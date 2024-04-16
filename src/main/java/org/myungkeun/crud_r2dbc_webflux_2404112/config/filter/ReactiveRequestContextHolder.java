package org.myungkeun.crud_r2dbc_webflux_2404112.config.filter;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpHeaders;

import java.util.Objects;
import java.util.Optional;

public class ReactiveRequestContextHolder {
    private static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION = "authorization";

    // HTTP 요청에서 인증 토큰을 추출하는 메서드
    public static Mono<String> getTokenAuth() {
        return Mono.deferContextual(Mono::just)
                .filter(contextView -> Objects.nonNull(contextView.get(ServerWebExchange.class).getRequest()))
                .map(contextView -> {
                    // ServerWebExchange에서 HTTP 요청 헤더를 가져옴
                    HttpHeaders headers = contextView.get(ServerWebExchange.class).getRequest().getHeaders();
                    // Authorization 헤더에서 토큰을 추출
                    return Optional.ofNullable(headers.getFirst(AUTHORIZATION))
                            // Bearer 토큰인지 확인
                            .filter(authHeader -> authHeader.startsWith(BEARER))
                            // Bearer String 제거
                            .map(authHeaderBearer -> authHeaderBearer.substring(BEARER.length()))
                            // 토큰 반환
                            .get();
                });
    }
}
