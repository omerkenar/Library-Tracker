package com.example.LibraryTracker.service;

import com.example.LibraryTracker.dto.author.AuthorResponseDto;
import com.example.LibraryTracker.dto.author.AuthorWithBooksDto;
import com.example.LibraryTracker.dto.author.CreateAuthorDto;

import java.util.List;

public interface AuthorService {
    AuthorResponseDto createAuthor(CreateAuthorDto dto);
    AuthorResponseDto getAuthor(Long id);
    List<AuthorResponseDto> getAllAuthors();
    AuthorWithBooksDto getAuthorWithBooks(Long id);
    AuthorResponseDto updateAuthor(Long id, CreateAuthorDto dto);
    void deleteAuthor(Long id);
    AuthorResponseDto getAuthorByName(String name);
}
