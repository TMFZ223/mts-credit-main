package com.example.creditservice.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "first name is required")
    private String firstname;

    @NotBlank(message = "last name is required")
    private String lastname;

    @NotBlank(message = "email is required")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 8, max = 25, message = "Password must be between 8 and 25 characters")
    @Pattern(regexp = "^(?!.*\\p{IsCyrillic})(?!.*\\s).*$", message = "Invalid format of password")
    private String password;
}
