package org.myungkeun.crud_r2dbc_webflux_2404112.services;

import org.myungkeun.crud_r2dbc_webflux_2404112.dto.login.LoginRequest;
import org.myungkeun.crud_r2dbc_webflux_2404112.dto.register.RegisterRequest;
import org.myungkeun.crud_r2dbc_webflux_2404112.entities.User;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<User> register(RegisterRequest request);

    Mono<String> login(LoginRequest request);
}
