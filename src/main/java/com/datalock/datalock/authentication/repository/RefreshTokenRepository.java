package com.datalock.datalock.authentication.repository;

import com.datalock.datalock.authentication.entities.RefreshTokenJpaModel;
import com.datalock.datalock.authentication.entities.UserJpaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenJpaModel, UUID> {

    Optional<RefreshTokenJpaModel> findByToken(String token);

    void deleteByUser(UserJpaModel user);
}