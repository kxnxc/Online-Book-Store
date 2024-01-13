package com.example.onlinebookstore.dto.user;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserLoginRequestDto(
        @NotBlank
        @Length(min = 10, max = 24)
        String email,
        @NotBlank
        @Length(min = 4, max = 24)
        String password) {
}
