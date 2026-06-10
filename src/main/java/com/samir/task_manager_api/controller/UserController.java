package com.samir.task_manager_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.samir.task_manager_api.dto.UserCreateDTO;
import com.samir.task_manager_api.dto.UserDTO;
import com.samir.task_manager_api.mapper.UserMapper;
import com.samir.task_manager_api.model.User;
import com.samir.task_manager_api.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {

        List<UserDTO> users = service.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .toList();

        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(
            @RequestBody UserCreateDTO dto) {

        User user = UserMapper.toEntity(dto);

        User saved = service.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserMapper.toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}