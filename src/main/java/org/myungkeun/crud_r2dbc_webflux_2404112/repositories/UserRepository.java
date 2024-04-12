package org.myungkeun.crud_r2dbc_webflux_2404112.repositories;

import org.myungkeun.crud_r2dbc_webflux_2404112.entities.User;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    Mono<User> findByEmailAndPassword(String email, String password);
    Mono<User> findByEmail(String email);
}
