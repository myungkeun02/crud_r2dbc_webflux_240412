package org.myungkeun.crud_r2dbc_webflux_2404112.services;

import org.myungkeun.crud_r2dbc_webflux_2404112.entities.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<String> getUserProfile(String token);

    Mono<User> updateUsername(String token, String newName);

}
