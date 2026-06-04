package com.gradtrack.controller;


import com.gradtrack.dto.JobApplicationRequest;
import com.gradtrack.dto.JobApplicationResponse;
import com.gradtrack.model.ApplicationStatus;
import com.gradtrack.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;


    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @GetMapping
    public ResponseEntity <List<JobApplicationResponse>> getAllApplications(@RequestParam (required = false)ApplicationStatus status, @RequestParam(required = false) String keyword) {

        return ResponseEntity.ok(jobApplicationService.getAllApplications(status,keyword));
    }

    @GetMapping("/{id}")
    public ResponseEntity <JobApplicationResponse> getApplicationById (@PathVariable long id){
        return ResponseEntity.ok (jobApplicationService.getApplicationById(id));
    }

    @PostMapping
    public ResponseEntity <JobApplicationResponse> createApplication(@Valid @RequestBody JobApplicationRequest request){
        JobApplicationResponse savedApplication = jobApplicationService.createApplication(request);

        return ResponseEntity.status(201).body(savedApplication);
    }

    @PutMapping("/{id}")
    public ResponseEntity <JobApplicationResponse> updateApplication (@PathVariable Long id , @Valid @RequestBody JobApplicationRequest request){
        JobApplicationResponse updatedApplication  = jobApplicationService.updateApplication(id,request);
        return ResponseEntity.ok(updatedApplication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id){
        jobApplicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }


}
