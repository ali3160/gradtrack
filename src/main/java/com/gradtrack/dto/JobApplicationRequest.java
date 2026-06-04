package com.gradtrack.dto;

import com.gradtrack.model.ApplicationStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class JobApplicationRequest {


    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Role title is required")
    private String roleTitle;

    @NotBlank(message = "Location is required")
    private String location;
    private String salary;
    private String jobUrl;

    @NotNull(message = "Application status is required")
    private ApplicationStatus status;

    @NotNull(message = "Application date is required")
    private LocalDate applicationDate;

    @FutureOrPresent(message = "Deadline must be today or in the future")
    private LocalDate deadline;

    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;

    public JobApplicationRequest(){};


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRoleTitle() {
        return roleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        this.roleTitle = roleTitle;
    }
}
