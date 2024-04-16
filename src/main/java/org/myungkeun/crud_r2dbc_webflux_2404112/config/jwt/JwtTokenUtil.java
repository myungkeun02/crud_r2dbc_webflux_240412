package org.myungkeun.crud_r2dbc_webflux_2404112.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.myungkeun.crud_r2dbc_webflux_2404112.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
    private final JwtKeyUtil jwtKeyUtil;
    public JwtTokenUtil(JwtKeyUtil jwtKeyUtil) {
        this.jwtKeyUtil = jwtKeyUtil;
    }

    @Value("${application.security.jwt.expriration}")
    private Long jwtExpiryTime;
    @Value("${application.security.jwt.refresh-time}")
    private Long jwtRefreshTime;

    // 사용자의 정보를 기반으로 액세스 토큰을 생성하는 메서드
    public String generateAccessToken(User user) {
        // 클레임 설정
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles());
        // buildToken 내부 메서드를 이용해 토큰 생성
        return buildToken(claims, user, jwtExpiryTime);
    }

    // 사용자의 정보를 기반으로 리프레시 토큰을 생성하는 메서드
    public String generateRefreshToken(User user) {
        // buildToken 내부 메서드를 이용해 토큰 생성
        return buildToken(new HashMap<>(), user, jwtRefreshTime);
    }

    // 토큰을 생성하는 내부 메서드
    private String buildToken(Map<String, Object> extraClaims, User user, Long expirationTime) {
        return Jwts
                .builder()
                .setClaims(extraClaims) // 클레임 설정
                .setSubject(user.getEmail()) // 서브젝트 설
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발급 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // 만료 시간 설정
                .signWith(jwtKeyUtil.getPrivateKey(), SignatureAlgorithm.RS256) // 개인 키와 알고리즘 방식 지정하여 서명
                .compact(); // 토큰 생성
    }
}
