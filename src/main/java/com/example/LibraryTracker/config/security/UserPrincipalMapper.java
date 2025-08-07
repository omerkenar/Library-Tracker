package com.example.LibraryTracker.config.security;

import com.example.LibraryTracker.entity.User;

public class UserPrincipalMapper {

    public static UserPrincipal fromUser(User user){
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
