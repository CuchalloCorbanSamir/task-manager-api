package com.samir.task_manager_api.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Long id) {
        super("Task con id " + id + " no encontrada");
    }
}