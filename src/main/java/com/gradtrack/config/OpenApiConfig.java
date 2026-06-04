package com.gradtrack.config;


import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gradTrackOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GradTrack API")
                        .version("1.0.0")
                        .description("REST API for tracking graduate job applications, interviews, tasks, deadlines and activity history."));
    }
}
