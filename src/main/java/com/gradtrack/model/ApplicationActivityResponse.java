package com.gradtrack.model;

import java.time.LocalDateTime;

public class ApplicationActivityResponse {

    private Long id;
    private Long jobApplicationId;
    private ActivityType activityType;
    private String description;
    private LocalDateTime createdAt;

    public ApplicationActivityResponse() {
    }
    public ApplicationActivityResponse(Long id, Long jobApplicationId, ActivityType activityType,
                                       String description, LocalDateTime createdAt) {
        this.id = id;
        this.jobApplicationId = jobApplicationId;
        this.activityType = activityType;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobApplicationId() {
        return jobApplicationId;
    }

    public void setJobApplicationId(Long jobApplicationId) {
        this.jobApplicationId = jobApplicationId;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
