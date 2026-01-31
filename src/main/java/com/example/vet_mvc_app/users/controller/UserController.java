package com.example.vet_mvc_app.users.controller;

import com.example.vet_mvc_app.users.dto.CreateUserRequest;
import com.example.vet_mvc_app.users.dto.UpdateUserRequest;
import com.example.vet_mvc_app.users.dto.UserResponse;
import com.example.vet_mvc_app.users.entity.User;
import com.example.vet_mvc_app.users.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/user")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/debug-token")
    public ResponseEntity<String> debugToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No token found");
        }

        // Remove "Bearer " prefix
        String token = authHeader.substring(7);

        // For debugging, just return it as plain text
        return ResponseEntity.ok(token);
    }
    @PostMapping
    public ResponseEntity<String> createUser(

            @Valid
            @RequestBody
            CreateUserRequest request) {
        String response = userService.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        UserResponse response = userService.updateUser(id, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        String response = userService.deleteUser(id);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
