package com.gradtrack.controller;


import com.gradtrack.model.ApplicationActivityResponse;
import com.gradtrack.service.ApplicationActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/applications/{applicationId}/activities")
public class ApplicationActivityController {
    private final ApplicationActivityService activityService;

    public ApplicationActivityController(ApplicationActivityService applicationActivityService) {
        this.activityService = applicationActivityService;
    }


    @GetMapping
    public ResponseEntity<List<ApplicationActivityResponse>> getActivitiesForApplication(@PathVariable Long applicationId){
        return ResponseEntity.ok(activityService.getActivitiesForApplication(applicationId));
    }
}
