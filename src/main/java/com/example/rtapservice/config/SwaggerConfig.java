package com.example.rtapservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI attendanceServiceOpenAPI() {
        // Define the security scheme for JWT
        SecurityScheme jwtScheme = new SecurityScheme()
                .name("Bearer Authentication")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .info(new Info()
                        .title("Real-Time Attendance API")
                        .description("API documentation for the FIP Real-Time Attendance and Presence Service.")
                        .version("v1.0")
                        .contact(new Contact().name("Your Name").email("your.email@example.com")))
                // Add the JWT security scheme to the components
                .components(new Components().addSecuritySchemes("BearerToken", jwtScheme))
                // Apply the security scheme globally (optional, but helpful for testing)
                .addSecurityItem(new SecurityRequirement().addList("BearerToken"));
    }
}
