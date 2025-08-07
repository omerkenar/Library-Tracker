package com.example.LibraryTracker.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAuthorDto {

    @NotBlank
    @Size(min = 2, max = 45, message = "Author name must be between 2 and 45 characters")
    private String name;

    private Integer birthYear;
}
