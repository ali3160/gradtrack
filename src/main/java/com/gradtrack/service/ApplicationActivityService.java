package com.gradtrack.service;


import com.gradtrack.exception.JobApplicationNotFoundException;
import com.gradtrack.model.ActivityType;
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

    public ApplicationActivityService(ApplicationActivityRepository activityRepository, JobApplicationRepository jobApplicationRepository) {
        this.activityRepository = activityRepository;
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public void recordActivity (JobApplication application, ActivityType activityType, String description){

        ApplicationActivity activity = new ApplicationActivity();

        activity.setJobApplication(application);
        activity.setActivityType(activityType);
        activity.setDescription(description);
        activity.setCreatedAt(LocalDateTime.now());

        activityRepository.save(activity);
    }

    public List<ApplicationActivityResponse> getActivitiesForApplication(Long applicationId){
        if(!jobApplicationRepository.existsById(applicationId)){
            throw new JobApplicationNotFoundException(applicationId);
        }
        return activityRepository.findByJobApplicationIdOrderByCreatedAtDesc(applicationId)
                .stream().map(this::mapToResponse).toList();

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
