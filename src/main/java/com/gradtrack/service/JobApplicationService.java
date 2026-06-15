package com.gradtrack.service;

import com.gradtrack.dto.JobApplicationRequest;
import com.gradtrack.dto.JobApplicationResponse;
import com.gradtrack.exception.JobApplicationNotFoundException;
import com.gradtrack.model.ActivityType;
import com.gradtrack.model.AppUser;
import com.gradtrack.model.ApplicationStatus;
import com.gradtrack.model.JobApplication;
import com.gradtrack.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final ApplicationActivityService activityService;
    private final CurrentUserService currentUserService;

    public JobApplicationService(
            JobApplicationRepository jobApplicationRepository,
            ApplicationActivityService activityService,
            CurrentUserService currentUserService
    ) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.activityService = activityService;
        this.currentUserService = currentUserService;
    }

    public List<JobApplicationResponse> getAllApplications(ApplicationStatus status, String keyword) {
        AppUser currentUser = currentUserService.getCurrentUser();

        List<JobApplication> applications;

        if (status != null) {
            applications = jobApplicationRepository.findByAppUserAndStatus(currentUser, status);
        } else if (keyword != null && !keyword.isBlank()) {
            applications = jobApplicationRepository.searchByKeywordForUser(currentUser, keyword);
        } else {
            applications = jobApplicationRepository.findByAppUser(currentUser);
        }

        return applications.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public JobApplicationResponse getApplicationById(long id) {
        JobApplication jobApplication = findApplicationOrThrow(id);
        return mapToResponse(jobApplication);
    }

    public JobApplicationResponse createApplication(JobApplicationRequest request) {
        AppUser currentUser = currentUserService.getCurrentUser();

        JobApplication application = new JobApplication();
        updateApplicationFeilds(application, request);
        application.setAppUser(currentUser);

        JobApplication savedApplication = jobApplicationRepository.save(application);

        activityService.recordActivity(
                savedApplication,
                ActivityType.APPLICATION_CREATED,
                "Application created for " + savedApplication.getRoleTitle() + " at " + savedApplication.getCompanyName()
        );

        return mapToResponse(savedApplication);
    }

    public JobApplicationResponse updateApplication(Long id, JobApplicationRequest request) {
        JobApplication existingApplication = findApplicationOrThrow(id);

        ApplicationStatus oldStatus = existingApplication.getStatus();

        updateApplicationFeilds(existingApplication, request);
        JobApplication updatedApplication = jobApplicationRepository.save(existingApplication);

        if (oldStatus != updatedApplication.getStatus()) {
            activityService.recordActivity(
                    updatedApplication,
                    ActivityType.STATUS_CHANGED,
                    "Status changed from " + oldStatus + " to " + updatedApplication.getStatus()
            );
        }

        activityService.recordActivity(
                updatedApplication,
                ActivityType.APPLICATION_UPDATED,
                "Application details updated"
        );

        return mapToResponse(updatedApplication);
    }

    public void deleteApplication(long id) {
        JobApplication existingApplication = findApplicationOrThrow(id);
        jobApplicationRepository.delete(existingApplication);
    }

    public JobApplication findApplicationOrThrow(Long id) {
        AppUser currentUser = currentUserService.getCurrentUser();

        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new JobApplicationNotFoundException(id));

        if (!application.getAppUser().getId().equals(currentUser.getId())) {
            throw new JobApplicationNotFoundException(id);
        }

        return application;
    }

    private void updateApplicationFeilds(JobApplication application, JobApplicationRequest request) {
        application.setCompanyName(request.getCompanyName());
        application.setRoleTitle(request.getRoleTitle());
        application.setLocation(request.getLocation());
        application.setSalary(request.getSalary());
        application.setJobUrl(request.getJobUrl());
        application.setStatus(request.getStatus());
        application.setApplicationDate(request.getApplicationDate());
        application.setDeadline(request.getDeadline());
        application.setNotes(request.getNotes());
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
