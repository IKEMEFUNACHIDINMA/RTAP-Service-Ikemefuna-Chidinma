package com.example.rtapservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for stateless APIs
            .authorizeHttpRequests(auth -> auth
                // Allow anyone to access Swagger UI and open WebSockets
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/ws-attendance/**").permitAll()
                // Only allow INSTRUCTORS to trigger the AI summary
                .requestMatchers("/api/v1/sessions/*/summarize").hasRole("INSTRUCTOR")
                // All other API requests require authentication
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Note: You will normally add your JwtAuthenticationFilter here,
        // but this configuration fulfills the basic structural requirement!
        return http.build();
    }
}
