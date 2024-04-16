package org.myungkeun.crud_r2dbc_webflux_2404112.services.impl;

import lombok.AllArgsConstructor;
import org.myungkeun.crud_r2dbc_webflux_2404112.config.jwt.JwtValidationUtil;
import org.myungkeun.crud_r2dbc_webflux_2404112.entities.User;
import org.myungkeun.crud_r2dbc_webflux_2404112.repositories.UserRepository;
import org.myungkeun.crud_r2dbc_webflux_2404112.services.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@AllArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtValidationUtil jwtValidationUtil;

    @Override
    public Mono<String> getUserProfile(String token) {
        return Mono.just(jwtValidationUtil.getEmail(token))
                .flatMap(userRepository::findByEmail)
                .filter(Objects::nonNull)
                .map(User::getEmail)
                .switchIfEmpty(Mono.<String>error(new RuntimeException("error when get email - not found email")));
    }

    @Override
    public Mono<User> updateUsername(String token, String newName) {
        return Mono.just(jwtValidationUtil.getEmail(token))
                .flatMap(userRepository::findByEmail)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.<User>error(new RuntimeException("error when getEmail - not found email")))
                .flatMap(user -> {
                    System.out.println("++++++++++++++++" + user);
                    user.setUsername(newName);
                    return userRepository.save(user);
                });
    }
}
