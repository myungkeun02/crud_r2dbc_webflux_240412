package org.myungkeun.crud_r2dbc_webflux_2404112.services.impl;

import lombok.AllArgsConstructor;
import org.myungkeun.crud_r2dbc_webflux_2404112.config.jwt.JwtTokenUtil;
import org.myungkeun.crud_r2dbc_webflux_2404112.dto.login.LoginRequest;
import org.myungkeun.crud_r2dbc_webflux_2404112.dto.register.RegisterRequest;
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
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Mono<User> register(RegisterRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .filter(Objects::nonNull)
                .flatMap(user -> Mono.<User>error(new RuntimeException("Email already registered")))
                .switchIfEmpty(saveNewUser(request));
    }

    @Override
    public Mono<String> login(LoginRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(jwtTokenUtil::generateAccessToken)
                .switchIfEmpty(Mono.error(new RuntimeException("Login failed - not found email or wrong password")));
    }

    private Mono<User> saveNewUser(RegisterRequest request) {
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
