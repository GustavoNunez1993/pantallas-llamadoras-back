package com.datalock.datalock.authentication.repository;

import com.datalock.datalock.authentication.entities.UserJpaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserJpaModel, UUID> {

    Optional<UserJpaModel> findByEmail(String email);

    Optional<UserJpaModel> findByEmailAndActiveTrue(
            String email
    );

    boolean existsByEmail(String email);

}
