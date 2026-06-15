package com.gradtrack.service;

import com.gradtrack.exception.JobApplicationNotFoundException;
import com.gradtrack.model.ActivityType;
import com.gradtrack.model.AppUser;
import com.gradtrack.model.ApplicationActivity;
import com.gradtrack.model.ApplicationActivityResponse;
import com.gradtrack.model.JobApplication;
import com.gradtrack.repository.ApplicationActivityRepository;
import com.gradtrack.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationActivityService {

    private final ApplicationActivityRepository activityRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final CurrentUserService currentUserService;

    public ApplicationActivityService(
            ApplicationActivityRepository activityRepository,
            JobApplicationRepository jobApplicationRepository,
            CurrentUserService currentUserService
    ) {
        this.activityRepository = activityRepository;
        this.jobApplicationRepository = jobApplicationRepository;
        this.currentUserService = currentUserService;
    }

    public void recordActivity(JobApplication application, ActivityType activityType, String description) {
        ApplicationActivity activity = new ApplicationActivity();

        activity.setJobApplication(application);
        activity.setActivityType(activityType);
        activity.setDescription(description);
        activity.setCreatedAt(LocalDateTime.now());

        activityRepository.save(activity);
    }

    public List<ApplicationActivityResponse> getActivitiesForApplication(Long applicationId) {
        JobApplication application = findApplicationForCurrentUser(applicationId);

        return activityRepository.findByJobApplicationOrderByCreatedAtDesc(application)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private JobApplication findApplicationForCurrentUser(Long applicationId) {
        AppUser currentUser = currentUserService.getCurrentUser();

        JobApplication application = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new JobApplicationNotFoundException(applicationId));

        if (!application.getAppUser().getId().equals(currentUser.getId())) {
            throw new JobApplicationNotFoundException(applicationId);
        }

        return application;
    }

    private ApplicationActivityResponse mapToResponse(ApplicationActivity activity) {
        return new ApplicationActivityResponse(
                activity.getId(),
                activity.getJobApplication().getId(),
                activity.getActivityType(),
                activity.getDescription(),
                activity.getCreatedAt()
        );
    }
}