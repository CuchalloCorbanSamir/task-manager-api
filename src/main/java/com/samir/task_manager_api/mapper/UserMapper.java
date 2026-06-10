package com.samir.task_manager_api.mapper;

import com.samir.task_manager_api.dto.UserCreateDTO;
import com.samir.task_manager_api.dto.UserDTO;
import com.samir.task_manager_api.model.User;

public class UserMapper {

    public static UserDTO toDTO(
            User user) {

        UserDTO dto =
                new UserDTO();

        dto.setId(user.getId());
        dto.setUsername(
                user.getUsername());
        dto.setRole(
                user.getRole());

        return dto;
    }

    public static User toEntity(UserCreateDTO dto) {

        User user = new User();

        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        return user;
    }
}