package com.example.LibraryTracker.service;

import com.example.LibraryTracker.dto.book.BookResponseDto;
import com.example.LibraryTracker.dto.book.CreateBookDto;
import com.example.LibraryTracker.entity.Author;
import com.example.LibraryTracker.entity.Book;
import com.example.LibraryTracker.exception.NotFoundException;
import com.example.LibraryTracker.mapper.BookMapper;
import com.example.LibraryTracker.repository.AuthorRepository;
import com.example.LibraryTracker.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional
    public BookResponseDto createBook(CreateBookDto dto) {
        Book book = bookMapper.toEntity(dto);
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Author not found: " + dto.getAuthorId(), HttpStatus.NOT_FOUND));
        book.setAuthor(author);
        book.setAvailable(true);
        book = bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponseDto getBook(Long id) {
        Book book = bookRepository.findByIdWithAuthor(id)
                .orElseThrow(() -> new NotFoundException("Book not found: " +id, HttpStatus.NOT_FOUND));
        return bookMapper.toDto(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> searchBooksByCategory(String category) {
        return bookRepository.findByCategory(category).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> searchBooksByTitle(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> searchBooksByAuthorName(String authorName) {
        return bookRepository.findByAuthorName(authorName).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public BookResponseDto updateBook(Long id, CreateBookDto dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found: " +id, HttpStatus.NOT_FOUND));
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Author not found: " + dto.getAuthorId(), HttpStatus.NOT_FOUND));
        book.setTitle(dto.getTitle());
        book.setPublishYear(dto.getPublishYear());
        book.setCategory(dto.getCategory());
        book.setAuthor(author);
        book = bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException("Book not found: " +id, HttpStatus.NOT_FOUND);
        }
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> getAvailableBooks() {
        return bookRepository.findByAvailableTrue().stream()
                .map(bookMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponseDto> getAvailableBooksByCategory(String category) {
        return bookRepository.findByCategoryAndAvailableTrue(category).stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
