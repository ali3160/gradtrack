package com.gradtrack.controller;


import com.gradtrack.dto.InterviewPatchRequest;
import com.gradtrack.dto.InterviewRequest;
import com.gradtrack.dto.InterviewResponse;
import com.gradtrack.model.JobApplication;
import com.gradtrack.service.InterviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class InterviewController {

    private final InterviewService interviewService;


    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping("/api/applications/{applicationId}/interviews")
    public ResponseEntity <InterviewResponse> createInterview(@PathVariable Long applicationId, @Valid @RequestBody InterviewRequest request){
        InterviewResponse savedInterview = interviewService.createInterview(applicationId, request);
        return ResponseEntity.status(201).body(savedInterview);
    }
    @GetMapping("/api/applications/{applicationId}/interviews")
    public ResponseEntity<List<InterviewResponse>> getInterviewsForApplication(@PathVariable Long applicationId ){

        return ResponseEntity.ok(interviewService.getInterviewsForApplication(applicationId));
    }

    @GetMapping("/interviews/{applicationId}")
    public ResponseEntity<InterviewResponse> getInterviewById(@PathVariable Long applicationId){
        return ResponseEntity.ok(interviewService.getInterviewById(applicationId));
    }

    @DeleteMapping("/api/interviews/{interviewId}")
    public ResponseEntity<Void> deleteInterview (@PathVariable Long interviewId){
        interviewService.deleteInterview(interviewId);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/api/interviews/{interviewId}")
    public ResponseEntity<InterviewResponse> updateInterview(@PathVariable Long interviewId , @Valid @RequestBody InterviewRequest request){
        InterviewResponse updatedInterview = interviewService.updateInterview(interviewId, request);

        return ResponseEntity.ok(updatedInterview);
    }

    @PatchMapping("/api/interviews/{interviewId}")
    public ResponseEntity<InterviewResponse> patchInterview(@PathVariable Long interviewId,
                                                            @Valid @RequestBody InterviewPatchRequest request){

        InterviewResponse updatedInterview = interviewService.patchInterview(interviewId, request);

        return ResponseEntity.ok(updatedInterview);

    }






}
