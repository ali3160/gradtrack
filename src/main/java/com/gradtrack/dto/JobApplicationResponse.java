package com.gradtrack.dto;

import com.gradtrack.model.ApplicationStatus;

import java.time.LocalDate;

public class JobApplicationResponse {

    private Long id;
    private String companyName;
    private String roleTitle;
    private String location;
    private String salary;
    private String jobUrl;
    private ApplicationStatus status;
    private LocalDate applicationDate;
    private LocalDate deadline;
    private String notes;

    public JobApplicationResponse (){};

    public JobApplicationResponse(Long id, String companyName, String roleTitle, String location,
                                  String salary, String jobUrl, ApplicationStatus status,
                                  LocalDate applicationDate, LocalDate deadline, String notes) {
        this.id = id;
        this.companyName = companyName;
        this.roleTitle = roleTitle;
        this.location = location;
        this.salary = salary;
        this.jobUrl = jobUrl;
        this.status = status;
        this.applicationDate = applicationDate;
        this.deadline = deadline;
        this.notes = notes;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
