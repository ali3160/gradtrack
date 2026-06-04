package com.gradtrack.dto;

import com.gradtrack.model.InterviewOutcome;
import com.gradtrack.model.InterviewType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class InterviewRequest {

    @NotNull(message = "Interview date and time is required")
    @FutureOrPresent(message = "Interview date and time must be now or in the future")
    private LocalDateTime interviewDateTime;

    @NotNull(message = "Interview type is required")
    private InterviewType interviewType;

    private InterviewOutcome outcome = InterviewOutcome.PENDING;

    private String interviewerName;

    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;

    public InterviewRequest() {
    }

    public LocalDateTime getInterviewDateTime() {
        return interviewDateTime;
    }

    public void setInterviewDateTime(LocalDateTime interviewDateTime) {
        this.interviewDateTime = interviewDateTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public InterviewType getInterviewType() {
        return interviewType;
    }

    public void setInterviewType(InterviewType interviewType) {
        this.interviewType = interviewType;
    }
}
