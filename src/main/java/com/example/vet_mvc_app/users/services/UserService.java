package com.example.vet_mvc_app.users.services;

import com.example.vet_mvc_app.users.dto.CreateUserRequest;
import com.example.vet_mvc_app.users.dto.UpdateUserRequest;
import com.example.vet_mvc_app.users.dto.UserResponse;
import com.example.vet_mvc_app.users.entity.User;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();

    String createUser(CreateUserRequest request);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    String deleteUser(Long id);

}
