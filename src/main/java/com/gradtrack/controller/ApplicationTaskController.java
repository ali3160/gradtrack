package com.gradtrack.controller;

import com.gradtrack.dto.ApplicationTaskPatchRequest;
import com.gradtrack.dto.ApplicationTaskRequest;
import com.gradtrack.dto.ApplicationTaskResponse;
import com.gradtrack.service.ApplicationTaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ApplicationTaskController {

    private final ApplicationTaskService taskService;

    public ApplicationTaskController(ApplicationTaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/api/applications/{applicationId}/tasks")
    public ResponseEntity<ApplicationTaskResponse> createTask(
            @PathVariable Long applicationId,
            @Valid @RequestBody ApplicationTaskRequest request
    ) {
        ApplicationTaskResponse savedTask = taskService.createTask(applicationId, request);
        return ResponseEntity.status(201).body(savedTask);
    }

    @GetMapping("/api/applications/{applicationId}/tasks")
    public ResponseEntity<List<ApplicationTaskResponse>> getTasksForApplication(
            @PathVariable Long applicationId
    ) {
        return ResponseEntity.ok(taskService.getTasksForApplication(applicationId));
    }

    @PatchMapping("/api/tasks/{taskId}")
    public ResponseEntity<ApplicationTaskResponse> patchTask(
            @PathVariable Long taskId,
            @Valid @RequestBody ApplicationTaskPatchRequest request
    ) {
        return ResponseEntity.ok(taskService.patchTask(taskId, request));
    }

    @DeleteMapping("/api/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
