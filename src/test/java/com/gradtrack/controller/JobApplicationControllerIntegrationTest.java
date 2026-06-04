package com.gradtrack.controller;

import com.gradtrack.repository.JobApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JobApplicationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @BeforeEach
    void setUp() {
        jobApplicationRepository.deleteAll();
    }

    @Test
    void shouldCreateJobApplication() throws Exception {
        String requestBody = """
                {
                  "roleTitle": "Graduate Software Engineer",
                  "companyName": "CGI",
                  "salary": "£32,000",
                  "location": "London",
                  "jobUrl": "https://example.com/job",
                  "status": "APPLIED",
                  "applicationDate": "2026-06-04",
                  "deadline": "2026-06-20",
                  "notes": "Submitted application through careers website"
                }
                """;

        mockMvc.perform(post("/api/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.roleTitle").value("Graduate Software Engineer"))
                .andExpect(jsonPath("$.companyName").value("CGI"))
                .andExpect(jsonPath("$.status").value("APPLIED"));
    }

    @Test
    void shouldReturnBadRequestWhenRequiredFieldsAreMissing() throws Exception {
        String requestBody = """
            {
              "salary": "£32,000",
              "location": "London"
            }
            """;

        mockMvc.perform(post("/api/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}