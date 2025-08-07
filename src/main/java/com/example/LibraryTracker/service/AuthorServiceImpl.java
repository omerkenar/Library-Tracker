package com.example.LibraryTracker.service;

import com.example.LibraryTracker.dto.author.*;
import com.example.LibraryTracker.entity.Author;
import com.example.LibraryTracker.exception.BusinessException;
import com.example.LibraryTracker.exception.NotFoundException;
import com.example.LibraryTracker.mapper.AuthorMapper;
import com.example.LibraryTracker.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    @Transactional
    public AuthorResponseDto createAuthor(CreateAuthorDto dto) {

        boolean authorExists = authorRepository.findByName(dto.getName()).isPresent();
        if (authorExists) {
            throw new BusinessException("Author with the same name already exists!", HttpStatus.CONFLICT);
        }

        Author author = authorMapper.toEntity(dto);
        author = authorRepository.save(author);
        return authorMapper.toDto(author);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorResponseDto getAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found: " + id, HttpStatus.NOT_FOUND));
        return authorMapper.toDto(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorResponseDto> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorWithBooksDto getAuthorWithBooks(Long id) {
        Author author = authorRepository.findByIdWithBooks(id)
                .orElseThrow(() -> new NotFoundException("Author not found: " + id, HttpStatus.NOT_FOUND));
        return authorMapper.toDtoWithBooks(author);
    }

    @Override
    @Transactional
    public AuthorResponseDto updateAuthor(Long id, CreateAuthorDto dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found: " + id, HttpStatus.NOT_FOUND));
        author.setName(dto.getName());
        author.setBirthYear(dto.getBirthYear());
        author = authorRepository.save(author);
        return authorMapper.toDto(author);
    }

    @Override
    @Transactional
    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new NotFoundException("Author not found: " + id, HttpStatus.NOT_FOUND);
        }
        authorRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorResponseDto getAuthorByName(String name) {
        Author author = authorRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Author not found: " + name, HttpStatus.NOT_FOUND));
        return authorMapper.toDto(author);
    }
}
