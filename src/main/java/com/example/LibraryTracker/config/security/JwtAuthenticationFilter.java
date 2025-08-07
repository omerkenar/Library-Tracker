package com.example.LibraryTracker.config.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    public static final String KEY_USER_ID = "userId";
    public static final String KEY_ROLE = "role";
    public static final String KEY_AUTHORIZATION = "Authorization";
    public static final String KEY_ENABLED = "enabled";
    public static final String KEY_ACCOUNT_NONLOCKED = "accountNonLocked";
    public static final String PREFIX_TOKEN_BEARER_ = "Bearer ";
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = parseToken(request);
            if(token != null && jwtTokenProvider.validateToken(token)) {
                Claims claims = jwtTokenProvider.getClaims(token);
                String username = claims.getSubject();
                Long userId = claims.get(KEY_USER_ID, Long.class);
                List<String> roles = claims.get(KEY_ROLE, List.class);
                Set<String> roleSet = new HashSet<>(roles);
                boolean enabled = claims.get(KEY_ENABLED, Boolean.class);
                boolean accountNonLocked = claims.get(KEY_ACCOUNT_NONLOCKED, Boolean.class);

                UserPrincipal principal = new UserPrincipal(userId, username, null, roleSet, enabled, accountNonLocked);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                principal, null, principal.getAuthorities()
                        );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception ex) {
            SecurityContextHolder.clearContext();

        }

        filterChain.doFilter(request, response);
    }


    private String parseToken(HttpServletRequest request) {
        String headerAuth = request.getHeader(KEY_AUTHORIZATION);
        if (headerAuth != null && headerAuth.startsWith(PREFIX_TOKEN_BEARER_)) {
            return headerAuth.substring(7);
        }
        return String.valueOf(new NullPointerException("JWT Authorization Not Install"));

    }
}
