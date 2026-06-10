package com.samir.task_manager_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.samir.task_manager_api.model.User;
import com.samir.task_manager_api.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository repository,
            PasswordEncoder passwordEncoder) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {

        return repository.findAll();
    }

    public Optional<User> findById(Long id) {

        return repository.findById(id);
    }

    public User save(User user) {

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()));

        return repository.save(user);
    }

    public void delete(Long id) {

        repository.deleteById(id);
    }
}