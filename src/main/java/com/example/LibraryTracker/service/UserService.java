package com.example.LibraryTracker.service;

import com.example.LibraryTracker.dto.user.CreateUserDto;
import com.example.LibraryTracker.dto.user.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(CreateUserDto dto);
    UserResponseDto getUser(String username);
    UserResponseDto getEmail(String email);
    List<UserResponseDto> getAllUsers();
    UserResponseDto updateUser(Long id, CreateUserDto dto);
    void deleteUser(Long id);
    void addRoleToUser(Long userId, String role);
    void removeRoleFromUser(Long userId, String role);
    void enableUser(Long userId);
    void disableUser(Long userId);
    void lockUser(Long userId);
    void unlockUser(Long userId);

}
