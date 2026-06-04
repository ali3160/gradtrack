package com.gradtrack.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime interviewDateTime;

    @Enumerated(EnumType.STRING)
    private InterviewType interviewType;

    @Enumerated(EnumType.STRING)
    private InterviewOutcome outcome;

    private String interviewerName;

    @Column(length = 1000)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "job_application_id")
    private JobApplication jobApplication;

    public Interview() {
    }

    public Interview(Long id, LocalDateTime interviewDateTime, InterviewType interviewType, InterviewOutcome outcome, String interviewerName, String notes, JobApplication jobApplication) {
        this.id = id;
        this.interviewDateTime = interviewDateTime;
        this.interviewType = interviewType;
        this.outcome = outcome;
        this.interviewerName = interviewerName;
        this.notes = notes;
        this.jobApplication = jobApplication;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getInterviewDateTime() {
        return interviewDateTime;
    }

    public void setInterviewDateTime(LocalDateTime interviewDateTime) {
        this.interviewDateTime = interviewDateTime;
    }

    public InterviewType getInterviewType() {
        return interviewType;
    }

    public void setInterviewType(InterviewType interviewType) {
        this.interviewType = interviewType;
    }

    public String getInterviewerName() {
        return interviewerName;
    }

    public void setInterviewerName(String interviewerName) {
        this.interviewerName = interviewerName;
    }

    public InterviewOutcome getOutcome() {
        return outcome;
    }

    public void setOutcome(InterviewOutcome outcome) {
        this.outcome = outcome;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public JobApplication getJobApplication() {
        return jobApplication;
    }

    public void setJobApplication(JobApplication jobApplication) {
        this.jobApplication = jobApplication;
    }
}
