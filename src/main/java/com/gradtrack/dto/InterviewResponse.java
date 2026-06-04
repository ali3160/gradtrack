package com.gradtrack.dto;

import com.gradtrack.model.InterviewOutcome;
import com.gradtrack.model.InterviewType;

import java.time.LocalDateTime;

public class InterviewResponse {

    private Long id;
    private Long jobApplicationId;
    private LocalDateTime interviewDateTime;
    private InterviewType interviewType;
    private InterviewOutcome outcome;
    private String interviewerName;
    private String notes;

    public InterviewResponse() {
    }

    public InterviewResponse(Long id, Long jobApplicationId, LocalDateTime interviewDateTime,
                             InterviewType interviewType, InterviewOutcome outcome,
                             String interviewerName, String notes) {
        this.id = id;
        this.jobApplicationId = jobApplicationId;
        this.interviewDateTime = interviewDateTime;
        this.interviewType = interviewType;
        this.outcome = outcome;
        this.interviewerName = interviewerName;
        this.notes = notes;
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
}
