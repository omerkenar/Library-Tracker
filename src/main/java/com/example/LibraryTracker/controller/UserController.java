package com.example.LibraryTracker.controller;

import com.example.LibraryTracker.dto.user.CreateUserDto;
import com.example.LibraryTracker.dto.user.UserResponseDto;
import com.example.LibraryTracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @Operation(summary = "Admin -> Yeni Kullanici olustur")
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody CreateUserDto dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @Operation(summary = "Admin -> Kullanici adi ile getir")
    @GetMapping("/by-username/{username}")
    public ResponseEntity<UserResponseDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUser(username));
    }

    @Operation(summary = "Admin -> e-mail ile getir")
    @GetMapping("/by-email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getEmail(email));
    }

    @Operation(summary = "Admin -> Tum kullanicilari getir")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Admin -> Kullanici guncelle")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @Valid @RequestBody CreateUserDto dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @Operation(summary = "Admin -> Kullanici sil")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Admin -> Rol ekle")
    @PutMapping("/{userId}/roles/add")
    public ResponseEntity<Void> addRoleToUser(@PathVariable Long userId, @RequestParam String role) {
        userService.addRoleToUser(userId, role);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Admin -> Rol sil")
    @PutMapping("/{userId}/roles/remove")
    public ResponseEntity<Void> removeRoleFromUser(@PathVariable Long userId, @RequestParam String role) {
        userService.removeRoleFromUser(userId, role);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Admin -> Hesabi aktif et")
    @PutMapping("/{userId}/enable")
    public ResponseEntity<Void> enableUser(@PathVariable Long userId) {
        userService.enableUser(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Admin -> Hesabi pasif et")
    @PutMapping("/{userId}/disable")
    public ResponseEntity<Void> disableUser(@PathVariable Long userId) {
        userService.disableUser(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Admin -> Hesabi kilitle")
    @PutMapping("/{userId}/lock")
    public ResponseEntity<Void> lockUser(@PathVariable Long userId) {
        userService.lockUser(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Admin -> Hesap kilit ac")
    @PutMapping("/{userId}/unlock")
    public ResponseEntity<Void> unlockUser(@PathVariable Long userId) {
        userService.unlockUser(userId);
        return ResponseEntity.ok().build();
    }
}
