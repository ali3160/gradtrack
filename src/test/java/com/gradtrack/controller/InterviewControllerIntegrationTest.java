package com.gradtrack.controller;

import com.gradtrack.model.ApplicationStatus;
import com.gradtrack.model.JobApplication;
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
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class InterviewControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @BeforeEach
    void setUp() {
        interviewRepository.deleteAll();
        jobApplicationRepository.deleteAll();
    }

    @Test
    void shouldCreateInterviewAndUpdateApplicationStatusToInterview() throws Exception {
        JobApplication application = new JobApplication();
        application.setRoleTitle("Graduate Software Engineer");
        application.setCompanyName("CGI");
        application.setSalary("£32,000");
        application.setLocation("London");
        application.setJobUrl("https://example.com/job");
        application.setStatus(ApplicationStatus.APPLIED);
        application.setApplicationDate(LocalDate.of(2026, 6, 4));
        application.setDeadline(LocalDate.of(2026, 6, 20));
        application.setNotes("Submitted application");

        JobApplication savedApplication = jobApplicationRepository.save(application);

        String interviewDateTime = LocalDateTime.now().plusDays(7).withNano(0).toString();

        String requestBody = """
        {
          "interviewType": "TECHNICAL",
          "interviewDateTime": "%s",
          "interviewerName": "Microsoft Teams",
          "notes": "Prepare Java, Spring Boot and SQL",
          "outcome": "PENDING"
        }
        """.formatted(interviewDateTime);

        mockMvc.perform(post("/api/applications/{applicationId}/interviews", savedApplication.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.interviewType").value("TECHNICAL"))
                .andExpect(jsonPath("$.outcome").value("PENDING"))
                .andExpect(jsonPath("$.interviewerName").value("Microsoft Teams"));

        JobApplication updatedApplication = jobApplicationRepository
                .findById(savedApplication.getId())
                .orElseThrow();

        assertThat(updatedApplication.getStatus()).isEqualTo(ApplicationStatus.INTERVIEW);
    }
}