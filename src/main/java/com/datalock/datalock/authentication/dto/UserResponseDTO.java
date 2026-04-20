package com.datalock.datalock.authentication.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserResponseDTO(
        UUID id,
        String nombre,
        String apellido,
        String email,
        String role,
        String fotoUrl,
        Boolean activo
) {}