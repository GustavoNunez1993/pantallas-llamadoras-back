package com.datalock.datalock.authentication.security.responses;



import com.datalock.datalock.authentication.dto.UserResponseDTO;
import lombok.Builder;

@Builder
public record JwtAuthenticationResponse(
        String accessToken,
        String refreshToken,
        UserResponseDTO user
) {}