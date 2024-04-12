package org.myungkeun.crud_r2dbc_webflux_2404112.services;

import org.myungkeun.crud_r2dbc_webflux_2404112.dto.register.RegisterRequestDto;
import org.myungkeun.crud_r2dbc_webflux_2404112.entities.User;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<User> register(RegisterRequestDto request);
}
