package com.gradtrack.dto;

import com.gradtrack.model.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class ApplicationTaskRequest {

    @NotBlank(message = "Task title is required")
    private String title;

    @FutureOrPresent(message = "Due date must be today or in the future")
    private LocalDate dueDate;

    private TaskStatus status = TaskStatus.TODO;

    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;

    public ApplicationTaskRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
