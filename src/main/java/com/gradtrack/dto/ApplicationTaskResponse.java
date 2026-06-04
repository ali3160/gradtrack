package com.gradtrack.dto;

import com.gradtrack.model.TaskStatus;

import java.time.LocalDate;

public class ApplicationTaskResponse {

    private Long id;
    private Long jobApplicationId;
    private String title;
    private LocalDate dueDate;
    private TaskStatus status;
    private String notes;

    public ApplicationTaskResponse() {
    }

    public ApplicationTaskResponse(Long id, Long jobApplicationId, String title,
                                   LocalDate dueDate, TaskStatus status, String notes) {
        this.id = id;
        this.jobApplicationId = jobApplicationId;
        this.title = title;
        this.dueDate = dueDate;
        this.status = status;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public Long getJobApplicationId() {
        return jobApplicationId;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }
}