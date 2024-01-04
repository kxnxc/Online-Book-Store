package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.user.UserRegistrationRequestDto;
import com.example.onlinebookstore.dto.user.UserResponseDto;
import com.example.onlinebookstore.exception.RegistrationException;
import com.example.onlinebookstore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication api", description = "Authentication api")
@RequiredArgsConstructor
@RestController
public class AuthenticationController {
    private final UserService userService;

    @Operation(summary = "Register user", description = "Register user")
    @PostMapping("/api/auth/registration")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }
}
