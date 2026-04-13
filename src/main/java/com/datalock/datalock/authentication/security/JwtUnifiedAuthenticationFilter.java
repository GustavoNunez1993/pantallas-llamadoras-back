package com.datalock.datalock.authentication.security;

import com.datalock.datalock.authentication.service.JwtCompanyService;
import com.datalock.datalock.authentication.service.JwtRiderService;
import com.datalock.datalock.authentication.service.JwtService;
import com.datalock.datalock.authentication.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUnifiedAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final JwtCompanyService jwtCompanyService;
    private final JwtRiderService jwtRiderService;

    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {

        // 1️⃣ Si ya hay auth, no tocar
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(request, response);
            return;
        }

        // 2️⃣ Leer Authorization
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        String email;

        try {
            email = extractUsername(token);
        } catch (Exception e) {
            chain.doFilter(request, response);
            return;
        }

        if (email == null) {
            chain.doFilter(request, response);
            return;
        }

        // 3️⃣ Resolver tipo de token
        authenticateIfValid(token, email, request);

        chain.doFilter(request, response);
    }

    private String extractUsername(String token) {
        try {
            return jwtService.extractUserName(token);
        } catch (Exception ignored) {}

        try {
            return jwtCompanyService.extractUserName(token);
        } catch (Exception ignored) {}

        try {
            return jwtRiderService.extractUserName(token);
        } catch (Exception ignored) {}

        return null;
    }

    private void authenticateIfValid(String token, String email, HttpServletRequest request) {

        try {
            var user = userService.findByEmail(email).orElse(null);
            if (user != null && jwtService.isTokenValid(token, user)) {
                setAuth(user, request);
                return;
            }
        } catch (Exception ignored) {}

    }

    private void setAuth(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);

        log.debug("JWT autenticado: {}", userDetails.getUsername());
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return request.getMethod().equalsIgnoreCase("OPTIONS")
                || path.startsWith("/api/auth")
                || path.startsWith("/swagger")
                || path.startsWith("/v3/api-docs");
    }
}
