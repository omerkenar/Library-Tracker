package com.example.LibraryTracker.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Long customerId;
    private Set<String> roles;
    private boolean enabled;
    private boolean accountNonLocked;
}
