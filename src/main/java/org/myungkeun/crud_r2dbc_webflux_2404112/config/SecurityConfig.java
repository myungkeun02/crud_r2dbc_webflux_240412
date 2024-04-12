package org.myungkeun.crud_r2dbc_webflux_2404112.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor

public class SecurityConfig {
    private static final String REGISTER_URL = "/auth/signup";

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(
                        () -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)) //failed authenticate 401
                )
                .accessDeniedHandler((swe, e) -> Mono.fromRunnable(
                        () -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)) //no authenticate 403
                )
                .and()
                .authorizeExchange()
                .pathMatchers(HttpMethod.POST).permitAll()
                .pathMatchers(
                        REGISTER_URL
                )
                .permitAll()
                .anyExchange().authenticated()
                .and()
                .build();
    }
}
