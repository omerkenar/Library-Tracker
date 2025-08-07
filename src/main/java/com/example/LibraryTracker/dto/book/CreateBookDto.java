package com.example.LibraryTracker.dto.book;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateBookDto {

    @NotBlank
    @Size(min = 2, max = 45, message = "title size must be between 2 to 45")
    private String title;

    @Min(1400)
    @Max(2100)
    private Integer publishYear;

    private String category;

    @NotNull
    private Long authorId;


}
