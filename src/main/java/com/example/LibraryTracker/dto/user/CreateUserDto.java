package com.example.LibraryTracker.dto.user;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateUserDto {

    @NotBlank
    private String username;

    @Email
    private String email;

    @NotBlank
    private String password;

}
