package com.example.LibraryTracker.controller;

import com.example.LibraryTracker.dto.book.BookResponseDto;
import com.example.LibraryTracker.dto.book.CreateBookDto;
import com.example.LibraryTracker.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Admin -> Yeni kitap ekle")
    @PostMapping("/admin/books/create-book")
    public ResponseEntity<BookResponseDto> createBook(@Valid @RequestBody CreateBookDto dto) {
        return ResponseEntity.ok(bookService.createBook(dto));
    }

    @Operation(summary = "User -> Id ile kitap getir")
    @GetMapping("/user/books/{id}")
    public ResponseEntity<BookResponseDto> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @Operation(summary = "User -> Tum kitaplari getir")
    @GetMapping("/user/books/all-books")
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @Operation(summary = "User -> Kategori ile kitap arama")
    @GetMapping("/user/books/search/category")
    public ResponseEntity<List<BookResponseDto>> searchBooksByCategory(@RequestParam String category) {
        return ResponseEntity.ok(bookService.searchBooksByCategory(category));
    }

    @Operation(summary = "User -> Isme gore kitap arama")
    @GetMapping("/user/books/search/title")
    public ResponseEntity<List<BookResponseDto>> searchBooksByTitle(@RequestParam String keyword) {
        return ResponseEntity.ok(bookService.searchBooksByTitle(keyword));
    }

    @Operation(summary = "User -> Yazar adina gore kitap arama")
    @GetMapping("/user/books/search/author")
    public ResponseEntity<List<BookResponseDto>> searchBooksByAuthorName(@RequestParam String authorName) {
        return ResponseEntity.ok(bookService.searchBooksByAuthorName(authorName));
    }

    @Operation(summary = "Admin -> Kitap guncelleme")
    @PutMapping("/admin/books/update-book/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable Long id, @Valid @RequestBody CreateBookDto dto) {
        return ResponseEntity.ok(bookService.updateBook(id, dto));
    }

    @Operation(summary = "Admin -> Kitap sil")
    @DeleteMapping("/admin/books/delete-book/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "User -> Musait tum kitaplari getir")
    @GetMapping("/user/books/available")
    public ResponseEntity<List<BookResponseDto>> getAvailableBooks(
            @RequestParam(required = false) String category) {
        if (category != null) {
            return ResponseEntity.ok(bookService.getAvailableBooksByCategory(category));
        }
        return ResponseEntity.ok(bookService.getAvailableBooks());
    }
}
