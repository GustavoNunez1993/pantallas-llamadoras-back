package com.datalock.datalock.authentication.service;

import com.datalock.datalock.authentication.entities.RefreshTokenJpaModel;
import com.datalock.datalock.authentication.entities.UserJpaModel;
import com.datalock.datalock.authentication.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    private final long refreshTokenDays = 7;

    @Transactional
    public RefreshTokenJpaModel create(UserJpaModel user) {

        repository.deleteByUser(user);

        RefreshTokenJpaModel token = RefreshTokenJpaModel.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(LocalDateTime.now().plusDays(refreshTokenDays))
                .build();

        return repository.save(token);
    }

    @Transactional
    public RefreshTokenJpaModel validateAndRotate(String tokenValue) {

        RefreshTokenJpaModel token = repository.findByToken(tokenValue)
                .orElseThrow(() -> new RuntimeException("Refresh token inválido"));

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            repository.delete(token);
            throw new RuntimeException("Refresh token expirado");
        }

        // 🔁 ROTACIÓN
        repository.delete(token);

        return create(token.getUser());
    }

    @Transactional
    public void invalidate(UserJpaModel user) {
        repository.deleteByUser(user);
    }
}