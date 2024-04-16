package org.myungkeun.crud_r2dbc_webflux_2404112.config.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {
    private static final String BEARER = "Bearer ";
    private final AuthenticationManager authenticationManager;
    public SecurityContextRepository(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * SecurityContext를 저장합니다. (현재 구현되지 않음)
     */
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return null;
        // todo: db에 token 저장
    }

    /**
     * SecurityContext를 로드합니다.
     */
    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        // HTTP 요청 헤더에서 Authorization 헤더를 가져와서 Bearer 토큰인지 확인합니다.
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith(BEARER))
                .flatMap(this::authenticationUserToken); // Bearer 토큰을 사용하여 사용자를 인증합니다.
    }

    /**
     * Bearer 토큰을 사용하여 사용자를 인증하고 SecurityContext를 생성합니다.
     */
    private Mono<SecurityContextImpl> authenticationUserToken(String authHeaderBearer) {
        // Bearer 토큰에서 토큰 부분만 추출합니다.
        String authToken = authHeaderBearer.substring(BEARER.length());
        // 추출된 토큰을 사용하여 Authentication 객체를 생성합니다.
        Authentication authentication = new UsernamePasswordAuthenticationToken(authToken, authToken);
        // 생성된 Authentication 객체를 사용하여 사용자를 인증합니다.
        return authenticationManager
                .authenticate(authentication)
                .map(SecurityContextImpl::new); // SecurityContextImpl 객체를 생성하여 반환합니다.
    }
}
