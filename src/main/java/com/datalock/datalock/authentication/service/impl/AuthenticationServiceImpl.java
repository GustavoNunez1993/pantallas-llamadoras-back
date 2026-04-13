package com.datalock.datalock.authentication.service.impl;

import com.datalock.datalock.authentication.dto.UserResponseDTO;
import com.datalock.datalock.authentication.entities.UserJpaModel;
import com.datalock.datalock.authentication.repository.UserRepository;
import com.datalock.datalock.authentication.security.request.SignUpRequest;
import com.datalock.datalock.authentication.security.request.SigninRequest;
import com.datalock.datalock.authentication.security.responses.JwtAuthenticationResponse;
import com.datalock.datalock.authentication.service.AuthenticationService;
import com.datalock.datalock.authentication.service.JwtService;
import com.datalock.datalock.authentication.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {

        log.warn("Empresa recibida: " + request.getEmpresaId());




        // 3️⃣ Crear usuario
        var user = UserJpaModel.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .activo(true)
                .build();

        userRepository.save(user);

        // 🔐 4️⃣ Access Token (2h)
        var accessToken = jwtService.generateToken(user);

        // 🔁 5️⃣ Refresh Token (7 días)
        var refreshToken = refreshTokenService
                .create(user)
                .getToken();

        // 6️⃣ DTO
        UserResponseDTO userDTO = UserResponseDTO.builder()
                .id(user.getId())
                .nombre(user.getNombre())
                .apellido(user.getApellido())
                .email(user.getEmail())
                .role(user.getRole().name())
                .fotoUrl(user.getFotoUrl())
                .activo(user.getActivo())
                .build();

        return JwtAuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(userDTO)
                .build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );


        var user = userRepository
                .findByEmailAndActiveTrue(
                        request.getEmail()
                )
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

        var accessToken = jwtService.generateToken(user);

        var refreshToken = refreshTokenService
                .create(user)
                .getToken();

        UserResponseDTO userDTO = UserResponseDTO.builder()
                .id(user.getId())
                .nombre(user.getNombre())
                .apellido(user.getApellido())
                .email(user.getEmail())
                .role(user.getRole().name())
                .fotoUrl(user.getFotoUrl())
                .activo(user.getActivo())
                .build();

        return JwtAuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(userDTO)
                .build();
    }
}