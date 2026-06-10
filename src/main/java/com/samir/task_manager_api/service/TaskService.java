package com.samir.task_manager_api.service;

import com.samir.task_manager_api.model.Task;
import com.samir.task_manager_api.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> findAll() {
        return repository.findAll();
    }

    public Page<Task> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Task> findById(Long id) {
        return repository.findById(id);
    }

    public Task save(Task task) {
        return repository.save(task);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    public List<Task> findByCompleted(boolean completed) {
        return repository.findByCompleted(completed);
    }
    public List<Task> searchByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }
}