package com.example.vet_mvc_app.authentication;

import com.example.vet_mvc_app.authentication.JWT.JwtService;
import com.example.vet_mvc_app.authentication.dto.AuthResponse;
import com.example.vet_mvc_app.authentication.dto.LoginRequest;
import com.example.vet_mvc_app.users.Repository.UserRepository;
import com.example.vet_mvc_app.users.dto.UserResponse;
import com.example.vet_mvc_app.users.entity.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Controller
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Valid LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String token = jwtService.generateToken(user.getId(), user.getName(), request.getEmail(), user.getRole());
        return ResponseEntity.ok(Map.of(
                "token", token,
                "message", "Login successful"
        ));
    }
}
