package com.samir.task_manager_api.controller;

import com.samir.task_manager_api.model.Task;
import com.samir.task_manager_api.service.TaskService;
import com.samir.task_manager_api.dto.TaskDTO;
import com.samir.task_manager_api.exception.TaskNotFoundException;
import com.samir.task_manager_api.mapper.TaskMapper;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }
/*
    @GetMapping
    @Operation(summary = "Obtener todas las tareas")
    public ResponseEntity<List<TaskDTO>> getAll() {

        List<TaskDTO> tasks = service.findAll()
                .stream()
                .map(TaskMapper::toDTO)
                .toList();
        return ResponseEntity.ok(tasks);

    }
*/
    @Operation(
        summary = "Obtener tareas paginadas",
        description = """
            Permite paginación y ordenamiento.

            Ejemplos:
            ?page=0&size=5
            ?sort=title,asc
            ?sort=id,desc
            """
    )
    @GetMapping
    public ResponseEntity<Page<TaskDTO>> getAll(
            @PageableDefault(
                    size = 3,
                    sort = "id")
            Pageable pageable) {

        Page<TaskDTO> tasks = service.findAll(pageable)
                .map(TaskMapper::toDTO);

        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva tarea")
    public ResponseEntity<TaskDTO> create(@Valid @RequestBody TaskDTO dto) {

        Task task = TaskMapper.toEntity(dto);

        Task saved = service.save(task);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TaskMapper.toDTO(saved));
    }
/*
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getById(@PathVariable Long id) {

        Task task = service.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        return ResponseEntity.ok(TaskMapper.toDTO(task));
    }
*/

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getById(@PathVariable Long id) {

        Task task = service.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        //System.out.println("createdAt = " + task.getCreatedAt());
        //System.out.println("updatedAt = " + task.getUpdatedAt());

        return ResponseEntity.ok(TaskMapper.toDTO(task));
    }

    @GetMapping("/completed")
    public ResponseEntity<List<TaskDTO>> getCompleted() {

        List<TaskDTO> tasks = service.findByCompleted(true)
                .stream()
                .map(TaskMapper::toDTO)
                .toList();

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<TaskDTO>> getPending() {

        List<TaskDTO> tasks = service.findByCompleted(false)
                .stream()
                .map(TaskMapper::toDTO)
                .toList();

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskDTO>> search(
            @RequestParam String title) {

        List<TaskDTO> tasks = service.searchByTitle(title)
                .stream()
                .map(TaskMapper::toDTO)
                .toList();

        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        service.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> update(
            @PathVariable Long id,
            @RequestBody TaskDTO dto) {

        Task existing = service.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        existing.setTitle(dto.getTitle());
        existing.setCompleted(dto.isCompleted());

        Task updated = service.save(existing);

        return ResponseEntity.ok(
                TaskMapper.toDTO(updated)
        );
    }

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    private final PasswordEncoder passwordEncoder;

    public PasswordController(
            PasswordEncoder passwordEncoder) {

        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/encode/{text}")
    public String encode(
            @PathVariable String text) {

        return passwordEncoder.encode(text);
    }
}

}