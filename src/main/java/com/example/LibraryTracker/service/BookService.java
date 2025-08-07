package com.example.LibraryTracker.service;

import com.example.LibraryTracker.dto.book.BookResponseDto;
import com.example.LibraryTracker.dto.book.CreateBookDto;

import java.util.List;

public interface BookService {
    BookResponseDto createBook(CreateBookDto dto);
    BookResponseDto getBook(Long id);
    List<BookResponseDto> getAllBooks();
    List<BookResponseDto> searchBooksByCategory(String category);
    List<BookResponseDto> searchBooksByTitle(String keyword);
    List<BookResponseDto> searchBooksByAuthorName(String authorName);
    BookResponseDto updateBook(Long id, CreateBookDto dto);
    void deleteBook(Long id);
    List<BookResponseDto> getAvailableBooks();
    List<BookResponseDto> getAvailableBooksByCategory(String category);

}
