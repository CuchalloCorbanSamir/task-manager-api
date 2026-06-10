package com.samir.task_manager_api.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl
        implements AuditorAware<String> {

    @Override
    //public Optional<String> getCurrentAuditor() {
    public @NonNull Optional<String> getCurrentAuditor() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()) {

            return Optional.of("SYSTEM");
        }

        return Optional.of(
                authentication.getName());
    }
}