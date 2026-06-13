package com.samir.task_manager_api.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StartupInfo {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @PostConstruct
    public void init() {

        System.out.println("=================================");
        System.out.println("DB URL = " + dbUrl);
        System.out.println("=================================");
    }
}