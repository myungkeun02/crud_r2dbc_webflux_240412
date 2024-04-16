package org.myungkeun.crud_r2dbc_webflux_2404112.config.security;

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
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private static final String REGISTER_URL = "/auth/signup";
    private static final String LOGIN_UTL = "/auth.login";
    private static final String USER_URL = "/user";

    /**
     * SecurityWebFilterChain을 설정합니다.
     * @param httpSecurity ServerHttpSecurity 객체
     * @return SecurityWebFilterChain
     */

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                // CSRF 보안 기능을 비활성화합니다.
                .csrf().disable()
                // 인증되지 않은 사용자에 대한 처리를 정의합니다.
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(
                        () -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)) // 인증되지 않은 사용자에 대한 처리: 401
                )
                .accessDeniedHandler((swe, e) -> Mono.fromRunnable(
                        () -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN))  // 권한이 없는 사용자에 대한 처리: 403
                )
                .and()
                // 인증 관리자와 보안 컨텍스트 저장소를 설정합니다.
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                // URL에 대한 접근 권한을 설정합니다.
                .authorizeExchange()
                // POST, GET, PUT 메서드에 대해 모두 허용합니다.
                .pathMatchers(HttpMethod.POST).permitAll()
                .pathMatchers(HttpMethod.GET).permitAll()
                .pathMatchers(HttpMethod.PUT).permitAll()
                // 특정 URL에 대해 모두 허용합니다.
                .pathMatchers(
                        REGISTER_URL,
                        LOGIN_UTL,
                        USER_URL
                )
                .permitAll()
                // 나머지 요청에 대해서는 인증이 필요합니다.
                .anyExchange().authenticated()
                // SecurityWebFilterChain을 생성하고 반환합니다.
                .and()
                .build();
    }
}
