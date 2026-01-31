package com.example.vet_mvc_app.users.dto;

import com.example.vet_mvc_app.users.entity.User;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    private String name;
    private String email;
    private User.Role role;
}
