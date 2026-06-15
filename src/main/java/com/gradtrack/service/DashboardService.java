package com.gradtrack.service;

import com.gradtrack.dto.DashboardSummaryResponse;
import com.gradtrack.dto.JobApplicationResponse;
import com.gradtrack.dto.TaskDashboardResponse;
import com.gradtrack.model.AppUser;
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
    private final CurrentUserService currentUser;

    public DashboardService(JobApplicationRepository jobApplicationRepository, ApplicationTaskRepository taskRepository, CurrentUserService currentUser) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.taskRepository = taskRepository;
        this.currentUser = currentUser;
    }

    public DashboardSummaryResponse getSummary() {
        AppUser appUser = currentUser.getCurrentUser();
        long totalApplications = jobApplicationRepository.countByAppUser(appUser);

        long saved = jobApplicationRepository.countByAppUserAndStatus(appUser,ApplicationStatus.SAVED);
        long applied = jobApplicationRepository.countByAppUserAndStatus(appUser,ApplicationStatus.APPLIED);
        long onlineTest = jobApplicationRepository.countByAppUserAndStatus(appUser,ApplicationStatus.ONLINE_TEST);
        long interview = jobApplicationRepository.countByAppUserAndStatus(appUser,ApplicationStatus.INTERVIEW);
        long offer = jobApplicationRepository.countByAppUserAndStatus(appUser,ApplicationStatus.OFFER);
        long rejected = jobApplicationRepository.countByAppUserAndStatus(appUser,ApplicationStatus.REJECTED);
        long withdrawn = jobApplicationRepository.countByAppUserAndStatus(appUser,ApplicationStatus.WITHDRAWN);

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
        AppUser appUser = currentUser.getCurrentUser();
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysFromNow = today.plusDays(7);

        return jobApplicationRepository.findByAppUserAndDeadlineBetween(appUser, today, sevenDaysFromNow).
                stream().map(this::mapToResponse).toList();

    }

    public TaskDashboardResponse getTaskSummary() {
        AppUser appUser = currentUser.getCurrentUser();
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysFromNow = today.plusDays(7);

        long totalTasks = taskRepository.countByJobApplicationAppUser(appUser);

        long todo = taskRepository.countByJobApplicationAppUserAndStatus(appUser,TaskStatus.TODO);
        long inProgress = taskRepository.countByJobApplicationAppUserAndStatus(appUser,TaskStatus.IN_PROGRESS);
        long completed = taskRepository.countByJobApplicationAppUserAndStatus(appUser,TaskStatus.COMPLETED);
        long cancelled = taskRepository.countByJobApplicationAppUserAndStatus(appUser,TaskStatus.CANCELLED);

        long dueToday = taskRepository.countByDueDateAndStatusNot(today, TaskStatus.COMPLETED);

        long overdue = taskRepository.countByJobApplicationAppUserAndDueDateAndStatusNot(
                appUser,
                today,
                TaskStatus.COMPLETED
        );

        long upcoming = taskRepository.countByJobApplicationAppUserAndDueDateBeforeAndStatusNot(
                appUser,
                today.plusDays(1),
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