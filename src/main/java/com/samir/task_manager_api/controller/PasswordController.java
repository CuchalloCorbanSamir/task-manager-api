package com.samir.task_manager_api.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class PasswordController {

    private final PasswordEncoder encoder;

    public PasswordController(
            PasswordEncoder encoder) {

        this.encoder = encoder;
    }

    @GetMapping("/hash")
    public String hash(
            @RequestParam String value) {

        return encoder.encode(value);
    }
}