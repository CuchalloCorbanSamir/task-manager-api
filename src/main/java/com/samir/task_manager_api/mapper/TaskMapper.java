package com.samir.task_manager_api.mapper;

import com.samir.task_manager_api.dto.TaskDTO;
import com.samir.task_manager_api.model.Task;

public class TaskMapper {

    public static TaskDTO toDTO(Task task) {

        TaskDTO dto = new TaskDTO();

        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setCompleted(task.isCompleted());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        dto.setCreatedBy(task.getCreatedBy());
        dto.setUpdatedBy(task.getUpdatedBy());
        return dto;
    }

    public static Task toEntity(TaskDTO dto) {

        Task task = new Task();

        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setCompleted(dto.isCompleted());

        return task;
    }
}