package com.example.LibraryTracker.service;

import com.example.LibraryTracker.config.security.UserPrincipal;
import com.example.LibraryTracker.entity.User;
import com.example.LibraryTracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found: " + username));
        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRoles(),
                user.isEnabled(),
                user.isAccountNonLocked()
        );
    }
}
