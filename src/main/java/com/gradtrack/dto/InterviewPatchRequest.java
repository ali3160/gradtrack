package com.gradtrack.dto;

import com.gradtrack.model.InterviewOutcome;
import com.gradtrack.model.InterviewType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class InterviewPatchRequest {
    @FutureOrPresent(message = "Interview date and time must be now or in the future")
    private LocalDateTime interviewDateTime;

    private InterviewType interviewType;

    private InterviewOutcome outcome;

    private String interviewerName;

    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;

    public InterviewPatchRequest() {
    }

    public LocalDateTime getInterviewDateTime() {
        return interviewDateTime;
    }

    public InterviewType getInterviewType() {
        return interviewType;
    }

    public InterviewOutcome getOutcome() {
        return outcome;
    }

    public String getInterviewerName() {
        return interviewerName;
    }

    public String getNotes() {
        return notes;
    }

    public void setInterviewDateTime(LocalDateTime interviewDateTime) {
        this.interviewDateTime = interviewDateTime;
    }

    public void setInterviewType(InterviewType interviewType) {
        this.interviewType = interviewType;
    }

    public void setOutcome(InterviewOutcome outcome) {
        this.outcome = outcome;
    }

    public void setInterviewerName(String interviewerName) {
        this.interviewerName = interviewerName;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
