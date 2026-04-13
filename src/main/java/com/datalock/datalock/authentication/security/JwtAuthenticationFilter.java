package com.datalock.datalock.authentication.security;

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
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        // 1️⃣ Si ya hay autenticación, no tocar
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(request, response);
            return;
        }

        // 2️⃣ Header Authorization
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String jwt = header.substring(7);

        try {
            String email = jwtService.extractUserName(jwt);

            if (email != null) {

                // 🔑 CARGAMOS LA ENTIDAD REAL
                var cliente = userService.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

                // Validar token contra la entidad
                if (jwtService.isTokenValid(jwt, cliente)) {

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    cliente, // 👈 CLAVE
                                    null,
                                    cliente.getAuthorities()
                            );

                    auth.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }

        } catch (Exception e) {
            // Token no corresponde a cliente → que lo intente otro filtro (rider / empresa)
            log.debug("JWT no corresponde a cliente");
        }

        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return request.getMethod().equalsIgnoreCase("OPTIONS")
                || path.startsWith("/api/auth-company")
                || path.startsWith("/api/auth-rider");
    }

}
