package com.example.onlinebookstore.dto.user;

import com.example.onlinebookstore.validation.FieldMatch;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@FieldMatch(first = "password", second = "repeatPassword",
        message = "Passwords must match")
public class UserRegistrationRequestDto {
    @NotEmpty
    @Length(max = 255)
    private String email;
    @NotEmpty
    @Length(min = 8, max = 255)
    private String password;
    @NotEmpty
    @Length(min = 8, max = 255)
    private String repeatPassword;
    @NotEmpty
    @Length(max = 255)
    private String firstName;
    @NotEmpty
    @Length(max = 255)
    private String lastName;
    @Length(max = 255)
    private String shippingAddress;
}
