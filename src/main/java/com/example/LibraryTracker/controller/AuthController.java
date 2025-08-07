package com.example.LibraryTracker.controller;

import com.example.LibraryTracker.config.security.JwtTokenProvider;
import com.example.LibraryTracker.config.security.UserPrincipal;
import com.example.LibraryTracker.config.security.UserPrincipalMapper;
import com.example.LibraryTracker.dto.login.LoginRequest;
import com.example.LibraryTracker.dto.login.LoginResponse;
import com.example.LibraryTracker.entity.User;
import com.example.LibraryTracker.exception.BusinessException;
import com.example.LibraryTracker.exception.NotFoundException;
import com.example.LibraryTracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new NotFoundException("User not not found!", HttpStatus.NOT_FOUND));

        UserPrincipal principal = UserPrincipalMapper.fromUser(user);

        if (!passwordEncoder.matches(request.getPassword(), principal.getPassword())){
            throw new BusinessException("Password is wrong", HttpStatus.BAD_REQUEST);
        }
        if (!principal.isEnabled()) {
            throw new BusinessException("Account is disabled.", HttpStatus.FORBIDDEN);
        }
        if (!principal.isAccountNonLocked()) {
            throw new BusinessException("Account is locked.", HttpStatus.FORBIDDEN);
        }

        String token = jwtTokenProvider.generateToken(
                principal.getUserId(),
                principal.getUsername(),
                principal.getAuthorities().stream()
                        .map(grantedAuthority -> grantedAuthority.getAuthority())
                        .collect(Collectors.toSet()),
                principal.isEnabled(),
                principal.isAccountNonLocked()
                );
        return ResponseEntity.ok(new LoginResponse(token, user.getId(), user.getRoles(), user.isEnabled(), user.isAccountNonLocked()));
    }
}
