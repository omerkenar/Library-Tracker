package com.example.LibraryTracker.controller;

import com.example.LibraryTracker.dto.author.*;
import com.example.LibraryTracker.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @Operation(summary = "Admin -> Yeni yazar ekleme")
    @PostMapping("/admin/authors/create-author")
    public ResponseEntity<AuthorResponseDto> createAuthor(@Valid @RequestBody CreateAuthorDto dto) {
        return ResponseEntity.ok(authorService.createAuthor(dto));
    }

    @Operation(summary = "User -> Id ile yazar getirme")
    @GetMapping("/user/authors/{id}")
    public ResponseEntity<AuthorResponseDto> getAuthor(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthor(id));
    }

    @Operation(summary = "User -> Tum yazarlari getirme")
    @GetMapping("/user/authors/all-authors")
    public ResponseEntity<List<AuthorResponseDto>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @Operation(summary = "User -> Yazar id ile tum kitaplarini getirme")
    @GetMapping("/user/authors/{id}/with-books")
    public ResponseEntity<AuthorWithBooksDto> getAuthorWithBooks(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorWithBooks(id));
    }

    @Operation(summary = "Admin -> Yazari guncelleme")
    @PutMapping("/admin/authors/update-author/{id}")
    public ResponseEntity<AuthorResponseDto> updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody CreateAuthorDto dto
    ) {
        return ResponseEntity.ok(authorService.updateAuthor(id, dto));
    }

    @Operation(summary = "Admin -> Yazari silme")
    @DeleteMapping("/admin/authors/delete-author/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "User -> Yazari ismiyle getirme")
    @GetMapping("/user/authors/by-name")
    public ResponseEntity<AuthorResponseDto> getAuthorByName(@RequestParam String name) {
        return ResponseEntity.ok(authorService.getAuthorByName(name));
    }
}
