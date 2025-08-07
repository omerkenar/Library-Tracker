package com.example.LibraryTracker.dto.book;

import lombok.Data;

@Data
public class BookResponseDto {

    private Long id;

    private String title;

    private Integer publishYear;

    private String category;

    private boolean available;

    private String authorName;


}
