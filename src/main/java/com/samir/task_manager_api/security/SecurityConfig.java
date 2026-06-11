package com.samir.task_manager_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//frontend
import java.util.List;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtFilter;

        public SecurityConfig(
                JwtAuthenticationFilter jwtFilter) {

                this.jwtFilter = jwtFilter;
        }

        @Bean
        SecurityFilterChain securityFilterChain(
                HttpSecurity http) throws Exception {

                http 
                        .cors(cors -> {})
                        .csrf(csrf -> csrf.disable())
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/api/auth/**",
                                        "/api/password/**")//temporal
                                .permitAll()
                                .requestMatchers(
                                        org.springframework.http.HttpMethod.POST,
                                        "/api/tasks")
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        org.springframework.http.HttpMethod.PUT,
                                        "/api/tasks/**")
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        org.springframework.http.HttpMethod.DELETE,
                                        "/api/tasks/**")
                                .hasRole("ADMIN")
                                .requestMatchers(
                                        "/api/tasks/**")
                                .hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/api/users/**")
                                .hasRole("ADMIN")
                                .anyRequest()
                                .authenticated())
                        .httpBasic(config -> config.disable())
                        .addFilterBefore(
                                jwtFilter,
                                UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of(
                        "http://localhost:4200",
                        "https://task-manager-ui-latest.onrender.com"));

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"));

        configuration.setAllowedHeaders(
                List.of("*"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration);

        return source;
        }

}