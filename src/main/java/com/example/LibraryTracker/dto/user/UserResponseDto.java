package com.example.LibraryTracker.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class UserResponseDto {

    private Long id;

    private String username;

    private String email;

    private Set<String> roles;

    private boolean enabled;

    private boolean accountNonLocked;

    private LocalDateTime createdAt;

}
