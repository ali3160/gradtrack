package com.gradtrack.controller;

import com.gradtrack.model.ApplicationStatus;
import com.gradtrack.model.ApplicationTask;
import com.gradtrack.model.JobApplication;
import com.gradtrack.model.TaskStatus;
import com.gradtrack.repository.ApplicationTaskRepository;
import com.gradtrack.repository.InterviewRepository;
import com.gradtrack.repository.JobApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DashboardControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private ApplicationTaskRepository applicationTaskRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @BeforeEach
    void setUp() {
        applicationTaskRepository.deleteAll();
        interviewRepository.deleteAll();
        jobApplicationRepository.deleteAll();
    }

    @Test
    void shouldReturnTaskDashboardSummary() throws Exception {
        JobApplication application = new JobApplication();
        application.setRoleTitle("Graduate Software Engineer");
        application.setCompanyName("CGI");
        application.setSalary("£32,000");
        application.setLocation("London");
        application.setJobUrl("https://example.com/job");
        application.setStatus(ApplicationStatus.APPLIED);
        application.setApplicationDate(LocalDate.now());
        application.setDeadline(LocalDate.now().plusDays(14));
        application.setNotes("Submitted application");

        JobApplication savedApplication = jobApplicationRepository.save(application);

        ApplicationTask todoTask = new ApplicationTask();
        todoTask.setTitle("Prepare CV");
        todoTask.setNotes("Update project section");
        todoTask.setStatus(TaskStatus.TODO);
        todoTask.setDueDate(LocalDate.now().plusDays(3));
        todoTask.setJobApplication(savedApplication);

        ApplicationTask inProgressTask = new ApplicationTask();
        inProgressTask.setTitle("Prepare interview answers");
        inProgressTask.setNotes("Practise STAR examples");
        inProgressTask.setStatus(TaskStatus.IN_PROGRESS);
        inProgressTask.setDueDate(LocalDate.now());
        inProgressTask.setJobApplication(savedApplication);

        ApplicationTask completedTask = new ApplicationTask();
        completedTask.setTitle("Submit application");
        completedTask.setNotes("Application submitted online");
        completedTask.setStatus(TaskStatus.COMPLETED);
        completedTask.setDueDate(LocalDate.now().minusDays(1));
        completedTask.setJobApplication(savedApplication);

        applicationTaskRepository.save(todoTask);
        applicationTaskRepository.save(inProgressTask);
        applicationTaskRepository.save(completedTask);

        mockMvc.perform(get("/api/dashboard/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalTasks").value(3))
                .andExpect(jsonPath("$.todo").value(1))
                .andExpect(jsonPath("$.inProgress").value(1))
                .andExpect(jsonPath("$.completed").value(1))
                .andExpect(jsonPath("$.dueToday").value(1))
                .andExpect(jsonPath("$.upcoming").value(1));
    }

    @Test
    void shouldUpdateTaskStatusAndReflectChangesInDashboard() throws Exception {
        JobApplication application = new JobApplication();
        application.setRoleTitle("Graduate Software Engineer");
        application.setCompanyName("CGI");
        application.setSalary("£32,000");
        application.setLocation("London");
        application.setJobUrl("https://example.com/job");
        application.setStatus(ApplicationStatus.APPLIED);
        application.setApplicationDate(LocalDate.now());
        application.setDeadline(LocalDate.now().plusDays(14));
        application.setNotes("Submitted application");

        JobApplication savedApplication = jobApplicationRepository.save(application);

        ApplicationTask task = new ApplicationTask();
        task.setTitle("Prepare interview answers");
        task.setNotes("Practise STAR examples");
        task.setStatus(TaskStatus.TODO);
        task.setDueDate(LocalDate.now().plusDays(2));
        task.setJobApplication(savedApplication);

        ApplicationTask savedTask = applicationTaskRepository.save(task);

        mockMvc.perform(get("/api/dashboard/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalTasks").value(1))
                .andExpect(jsonPath("$.todo").value(1))
                .andExpect(jsonPath("$.completed").value(0))
                .andExpect(jsonPath("$.upcoming").value(1));

        String patchBody = """
            {
              "status": "COMPLETED"
            }
            """;

        mockMvc.perform(patch("/api/tasks/{taskId}", savedTask.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));

        mockMvc.perform(get("/api/dashboard/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalTasks").value(1))
                .andExpect(jsonPath("$.todo").value(0))
                .andExpect(jsonPath("$.completed").value(1));
    }
}