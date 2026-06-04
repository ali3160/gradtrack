package com.gradtrack.model;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;

    private String roleTitle;

    private String location;

    private String salary;

    private String jobUrl;

    @Enumerated (EnumType.STRING)
    private ApplicationStatus status;

    private LocalDate applicationDate;

    private LocalDate deadline;
    @Column(length = 1000)
    private String notes;

    public JobApplication(){};

    public JobApplication(String roleTitle, String companyName, Long id, String salary, String location, String jobUrl, ApplicationStatus status, LocalDate applicationDate, LocalDate deadline, String notes) {
        this.roleTitle = roleTitle;
        this.companyName = companyName;
        this.id = id;
        this.salary = salary;
        this.location = location;
        this.jobUrl = jobUrl;
        this.status = status;
        this.applicationDate = applicationDate;
        this.deadline = deadline;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRoleTitle() {
        return roleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        this.roleTitle = roleTitle;
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
}
