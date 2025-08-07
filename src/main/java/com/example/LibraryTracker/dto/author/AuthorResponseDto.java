package com.example.LibraryTracker.dto.author;

import lombok.Data;

@Data
public class AuthorResponseDto {

    private Long id;

    private String name;

    private Integer birthYear;
}
