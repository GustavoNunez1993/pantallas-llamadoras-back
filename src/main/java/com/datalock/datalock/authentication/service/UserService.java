package com.datalock.datalock.authentication.service;

import com.datalock.datalock.authentication.entities.UserJpaModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserDetailsService userDetailsService();


    // Para JWT / negocio
    Optional<UserJpaModel> findByEmail(String email);

    UUID obtenerIdDesdeUsername(String username);

}
