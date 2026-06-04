package com.gradtrack.service;

import com.gradtrack.dto.DashboardSummaryResponse;
import com.gradtrack.dto.JobApplicationResponse;
import com.gradtrack.dto.TaskDashboardResponse;
import com.gradtrack.model.ApplicationStatus;
import com.gradtrack.model.JobApplication;
import com.gradtrack.model.TaskStatus;
import com.gradtrack.repository.ApplicationTaskRepository;
import com.gradtrack.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {

    private final JobApplicationRepository jobApplicationRepository;
    private final ApplicationTaskRepository taskRepository;

    public DashboardService(JobApplicationRepository jobApplicationRepository, ApplicationTaskRepository taskRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.taskRepository = taskRepository;
    }

    public DashboardSummaryResponse getSummary() {
        long totalApplications = jobApplicationRepository.count();

        long saved = jobApplicationRepository.countByStatus(ApplicationStatus.SAVED);
        long applied = jobApplicationRepository.countByStatus(ApplicationStatus.APPLIED);
        long onlineTest = jobApplicationRepository.countByStatus(ApplicationStatus.ONLINE_TEST);
        long interview = jobApplicationRepository.countByStatus(ApplicationStatus.INTERVIEW);
        long offer = jobApplicationRepository.countByStatus(ApplicationStatus.OFFER);
        long rejected = jobApplicationRepository.countByStatus(ApplicationStatus.REJECTED);
        long withdrawn = jobApplicationRepository.countByStatus(ApplicationStatus.WITHDRAWN);

        return new DashboardSummaryResponse(totalApplications,
                saved,
                applied,
                onlineTest,
                interview,
                offer,
                rejected,
                withdrawn);
    }

    public List<JobApplicationResponse> getUpcomingDeadlines() {

        LocalDate today = LocalDate.now();
        LocalDate sevenDaysFromNow = today.plusDays(7);

        return jobApplicationRepository.findByDeadlineBetween(today, sevenDaysFromNow).
                stream().map(this::mapToResponse).toList();

    }

    public TaskDashboardResponse getTaskSummary() {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysFromNow = today.plusDays(7);

        long totalTasks = taskRepository.count();

        long todo = taskRepository.countByStatus(TaskStatus.TODO);
        long inProgress = taskRepository.countByStatus(TaskStatus.IN_PROGRESS);
        long completed = taskRepository.countByStatus(TaskStatus.COMPLETED);
        long cancelled = taskRepository.countByStatus(TaskStatus.CANCELLED);

        long dueToday = taskRepository.countByDueDate(today);

        long overdue = taskRepository.countByDueDateBeforeAndStatusNot(
                today,
                TaskStatus.COMPLETED
        );

        long upcoming = taskRepository.countByDueDateBetweenAndStatusNot(
                today,
                sevenDaysFromNow,
                TaskStatus.COMPLETED
        );

        return new TaskDashboardResponse(
                totalTasks,
                todo,
                inProgress,
                completed,
                cancelled,
                dueToday,
                overdue,
                upcoming
        );
    }

    private JobApplicationResponse mapToResponse(JobApplication application) {
        return new JobApplicationResponse(
                application.getId(),
                application.getCompanyName(),
                application.getRoleTitle(),
                application.getLocation(),
                application.getSalary(),
                application.getJobUrl(),
                application.getStatus(),
                application.getApplicationDate(),
                application.getDeadline(),
                application.getNotes()
        );


    }
}