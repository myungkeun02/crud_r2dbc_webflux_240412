package org.myungkeun.crud_r2dbc_webflux_2404112.config.security;

import org.myungkeun.crud_r2dbc_webflux_2404112.config.jwt.JwtValidationUtil;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component

public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtValidationUtil jwtValidationUtil;
    public AuthenticationManager(JwtValidationUtil jwtValidationUtil) {
        this.jwtValidationUtil = jwtValidationUtil;
    }

    /**
     * 인증을 수행합니다.
     * @param authentication 인증 객체
     * @return 인증 결과를 포함한 Mono
     */
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        // 전달된 Authentication 객체에서 토큰을 추출.
        String token = authentication.getCredentials().toString();
        // 토큰을 사용하여 이메일을 추출.
        String email = jwtValidationUtil.getEmail(token);
        // 토큰이 유효한지 확인하고 인증 토큰을 생성하여 반환.
        return Mono.justOrEmpty(jwtValidationUtil.isTokenValid(token, email))
                .flatMap(isValid -> AuthenticationEmailAndToken(email, token));
    }

    /**
     * 이메일과 토큰을 사용하여 인증을 수행합니다.
     * @param email 이메일
     * @param token 토큰
     * @return 인증 결과를 포함한 Mono
     */
    private Mono<Authentication> AuthenticationEmailAndToken(String email, String token) {
        // 토큰을 사용하여 클레임을 추출합니다.
        return Mono.just(jwtValidationUtil.getClaimsToken(token))
                .map(claims -> {
                    // 클레임에서 역할(role) 정보를 추출.
                    List<String> roleList = claims.get("roles", List.class);
                    // 추출된 역할 정보를 SimpleGrantedAuthority로 변환하여 인증 토큰을 생성.
                    return new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            roleList.stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList())
                    );
                });
    }
}
