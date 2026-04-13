package com.datalock.datalock.authentication.service.impl;


import com.datalock.datalock.authentication.entities.UserJpaModel;
import com.datalock.datalock.authentication.repository.UserRepository;
import com.datalock.datalock.authentication.service.UserService;
import com.datalock.datalock.utils.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {

        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontro el usuario"));
    }

    @Override
    public Optional<UserJpaModel> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UUID obtenerIdDesdeUsername(String username) {
        return userRepository.findByEmail(username)
                .map(UserJpaModel::getId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con username: " + username));
    }
}