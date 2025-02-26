package org.example.petclinicmanagementsystem.Data.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;

import java.io.Serializable;

@Value
public class UserDTO implements Serializable {

    @NotNull(message = "Id cannot be null")
    @Positive(message = "Id must be a positive number")
    Long id;

    @NotNull(message = "Username cannot be null")
    @NotBlank
    String username;

    @NotNull(message = "password cannot be null")
    @NotBlank
    String password;

    @Email
    @NotNull(message = "Email cannot be null")
    @NotBlank
    String email;

    String role;
}
