package com.gradtrack.controller;


import com.gradtrack.dto.DashboardSummaryResponse;
import com.gradtrack.dto.JobApplicationResponse;
import com.gradtrack.dto.TaskDashboardResponse;
import com.gradtrack.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }


    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryResponse> getSummary(){
        return ResponseEntity.ok(dashboardService.getSummary());
    }

    @GetMapping("/upcoming-deadlines")
    public ResponseEntity<List<JobApplicationResponse>> getUpcomingDeadlines(){
        return ResponseEntity.ok(dashboardService.getUpcomingDeadlines());
    }

    @GetMapping("/tasks")
    public ResponseEntity<TaskDashboardResponse> getTaskSummary() {
        return ResponseEntity.ok(dashboardService.getTaskSummary());
    }

}
