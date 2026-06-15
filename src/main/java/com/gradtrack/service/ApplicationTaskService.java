package com.gradtrack.service;

import com.gradtrack.dto.ApplicationTaskPatchRequest;
import com.gradtrack.dto.ApplicationTaskRequest;
import com.gradtrack.dto.ApplicationTaskResponse;
import com.gradtrack.exception.ApplicationTaskNotFoundException;
import com.gradtrack.model.ActivityType;
import com.gradtrack.model.ApplicationTask;
import com.gradtrack.model.JobApplication;
import com.gradtrack.model.TaskStatus;
import com.gradtrack.repository.ApplicationTaskRepository;
import com.gradtrack.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationTaskService {

    private final ApplicationTaskRepository taskRepository;
    private final ApplicationActivityService activityService;
    private final JobApplicationService jobApplicationService;

    public ApplicationTaskService(ApplicationTaskRepository taskRepository , ApplicationActivityService activityService, JobApplicationService jobApplicationService) {
        this.taskRepository = taskRepository;
        this.activityService = activityService;
        this.jobApplicationService = jobApplicationService;
    }

    public ApplicationTaskResponse createTask(Long applicationId, ApplicationTaskRequest request){

        JobApplication application = jobApplicationService.findApplicationOrThrow(applicationId);

        ApplicationTask task = new ApplicationTask();
        task.setJobApplication(application);
        task.setTitle(request.getTitle());
        task.setDueDate(request.getDueDate());
        task.setStatus(request.getStatus() ==null? TaskStatus.TODO:request.getStatus());
        task.setNotes(request.getNotes());

        ApplicationTask savedTask = taskRepository.save(task);

        activityService.recordActivity(
                application,
                ActivityType.NOTE_UPDATED,
                "Task created: " + savedTask.getTitle()
        );

        return mapToResponse(savedTask);
    }

    public List<ApplicationTaskResponse> getTasksForApplication(Long applicationId) {
        JobApplication application = jobApplicationService.findApplicationOrThrow(applicationId);
        return taskRepository.findByJobApplicationOrderByDueDateAsc(application)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ApplicationTaskResponse patchTask(Long taskId, ApplicationTaskPatchRequest request) {
        ApplicationTask task = findApplicationTaskForCurrentUser(taskId);

        TaskStatus oldStatus = task.getStatus();

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }

        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate());
        }

        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        if (request.getNotes() != null) {
            task.setNotes(request.getNotes());
        }

        ApplicationTask updatedTask = taskRepository.save(task);

        if (oldStatus != updatedTask.getStatus()) {
            activityService.recordActivity(
                    updatedTask.getJobApplication(),
                    ActivityType.NOTE_UPDATED,
                    "Task status changed from " + oldStatus + " to " + updatedTask.getStatus()
            );
        }

        return mapToResponse(updatedTask);
    }


    public void deleteTask(Long taskId) {
        ApplicationTask task = findApplicationTaskForCurrentUser(taskId);
        activityService.recordActivity(
                task.getJobApplication(),
                ActivityType.NOTE_UPDATED,
                "Task deleted: " + task.getTitle()
        );

        taskRepository.delete(task);
    }


    private ApplicationTask findApplicationTaskForCurrentUser (long taskId){
        ApplicationTask task  = taskRepository.findById(taskId)
                .orElseThrow(() -> new ApplicationTaskNotFoundException(taskId));

        long applicationId = task.getJobApplication().getId();
        jobApplicationService.findApplicationOrThrow(applicationId);
        return task;
    }

    private ApplicationTaskResponse mapToResponse(ApplicationTask task) {
        return new ApplicationTaskResponse(
                task.getId(),
                task.getJobApplication().getId(),
                task.getTitle(),
                task.getDueDate(),
                task.getStatus(),
                task.getNotes()
        );

    }


}
