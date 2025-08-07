package com.example.LibraryTracker.service;

import com.example.LibraryTracker.dto.user.CreateUserDto;
import com.example.LibraryTracker.dto.user.UserResponseDto;
import com.example.LibraryTracker.entity.User;
import com.example.LibraryTracker.exception.BusinessException;
import com.example.LibraryTracker.exception.NotFoundException;
import com.example.LibraryTracker.mapper.UserMapper;
import com.example.LibraryTracker.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDto createUser(CreateUserDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new BusinessException("Username already exists!", HttpStatus.CONFLICT);
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email already exists!", HttpStatus.CONFLICT);
        }
        User user = userMapper.toEntity(dto);
        user.setRoles(Set.of("USER")); // Default rol
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user = userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(username + " not found", HttpStatus.NOT_FOUND));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(email + " not found", HttpStatus.NOT_FOUND));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(Long id, CreateUserDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id, HttpStatus.NOT_FOUND));
        if (!user.getUsername().equals(dto.getUsername()) &&
                userRepository.existsByUsername(dto.getUsername())) {
            throw new BusinessException("Username already exists!", HttpStatus.CONFLICT);
        }
        if (!user.getEmail().equals(dto.getEmail()) &&
                userRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email already exists!", HttpStatus.CONFLICT);
        }
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        if (!dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addRoleToUser(Long userId, String role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found: " + userId, HttpStatus.NOT_FOUND));
        user.getRoles().add(role.toUpperCase());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeRoleFromUser(Long userId, String role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found: " + userId, HttpStatus.NOT_FOUND));
        user.getRoles().remove(role.toUpperCase());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void enableUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found: " + userId, HttpStatus.NOT_FOUND));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void disableUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found: " + userId, HttpStatus.NOT_FOUND));
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void lockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found: " + userId, HttpStatus.NOT_FOUND));
        user.setAccountNonLocked(false);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void unlockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found: " + userId, HttpStatus.NOT_FOUND));
        user.setAccountNonLocked(true);
        userRepository.save(user);
    }

}
