package com.example.LibraryTracker.dto.author;

import lombok.Data;

import java.util.List;

@Data
public class AuthorWithBooksDto {

    private Long id;

    private String name;

    private Integer birthYear;

    private List<BookInfo> books;

    @Data
    public static class BookInfo {
        private Long id;
        private String title;
        private boolean available;
    }
}
