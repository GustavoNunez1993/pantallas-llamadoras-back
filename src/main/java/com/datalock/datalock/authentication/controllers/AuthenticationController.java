package com.datalock.datalock.authentication.controllers;


import com.datalock.datalock.authentication.entities.RefreshTokenJpaModel;
import com.datalock.datalock.authentication.entities.UserJpaModel;
import com.datalock.datalock.authentication.security.request.SignUpRequest;
import com.datalock.datalock.authentication.security.request.SigninRequest;
import com.datalock.datalock.authentication.service.AuthenticationService;
import com.datalock.datalock.authentication.service.JwtService;
import com.datalock.datalock.authentication.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest request) {
        try {
            return ResponseEntity.ok(authenticationService.signup(request));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "timestamp", LocalDateTime  .now(),
                    "status", HttpStatus.BAD_REQUEST.value(),
                    "error", "Bad Request",
                    "message", ex.getMessage()
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "timestamp", LocalDateTime.now(),
                    "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "error", "Internal Server Error",
                    "message", ex.getMessage()
            ));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninRequest request) {
        try {
            return ResponseEntity.ok(authenticationService.signin(request));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "timestamp", LocalDateTime.now(),
                    "status", HttpStatus.BAD_REQUEST.value(),
                    "error", "Bad Request",
                    "message", ex.getMessage()
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "timestamp", LocalDateTime.now(),
                    "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "error", "Internal Server Error",
                    "message", ex.getMessage()
            ));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {

        String oldRefreshToken = request.get("refreshToken");

        RefreshTokenJpaModel newTokenEntity =
                refreshTokenService.validateAndRotate(oldRefreshToken);

        String newAccessToken =
                jwtService.generateToken(newTokenEntity.getUser());

        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken,
                "refreshToken", newTokenEntity.getToken()
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(Authentication authentication) {

        UserJpaModel user = (UserJpaModel) authentication.getPrincipal();

        refreshTokenService.invalidate(user);

        return ResponseEntity.ok("Logout exitoso");
    }
}

