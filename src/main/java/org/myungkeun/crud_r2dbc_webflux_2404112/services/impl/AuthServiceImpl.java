package org.myungkeun.crud_r2dbc_webflux_2404112.services.impl;

import lombok.AllArgsConstructor;
import org.myungkeun.crud_r2dbc_webflux_2404112.dto.register.RegisterRequestDto;
import org.myungkeun.crud_r2dbc_webflux_2404112.entities.Role;
import org.myungkeun.crud_r2dbc_webflux_2404112.entities.User;
import org.myungkeun.crud_r2dbc_webflux_2404112.repositories.UserRepository;
import org.myungkeun.crud_r2dbc_webflux_2404112.services.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@AllArgsConstructor

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<User> register(RegisterRequestDto request) {
        return userRepository.findByEmail(request.getEmail())
                .filter(Objects::nonNull)
                .flatMap(user -> Mono.<User>error(new RuntimeException("Email already registered")))
                .switchIfEmpty(saveNewUser(request));
    }

    private Mono<User> saveNewUser(RegisterRequestDto request) {
        User newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Role.ROLE_USER.name())
                .enabled(Boolean.TRUE)
                .createdBy(request.getUsername())
                .createdAt(LocalDateTime.now())
                .build();
        return userRepository.save(newUser);
    }
}
