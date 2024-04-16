package org.myungkeun.crud_r2dbc_webflux_2404112.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtValidationUtil {
    private final JwtKeyUtil jwtKeyUtil;
    public JwtValidationUtil(JwtKeyUtil jwtKeyUtil) {
        this.jwtKeyUtil = jwtKeyUtil;
    }

    @Value("${application.security.jwt.secretkey}")
    private String secretKey;

    // 주어진 토큰에서 클레임을 가져오는 메서드
    public Claims getClaimsToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtKeyUtil.getPrivateKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 주어진 토큰에서 이메일을 가져오는 메서드
    public String getEmail(String token) {
        return getClaimsToken(token).getSubject();
    }

    // 주어진 토큰에서 만료 시간을 가져오는 메서드
    public Date getExpiryTime(String token) {
        return getClaimsToken(token).getExpiration();
    }

    // 주어진 토큰이 만료되었는지 확인하는 메서드
    public Boolean isExpired(String token) {
        return getExpiryTime(token).before(new Date());
    }

    // 주어진 토큰이 유효한지 확인하는 메서드
    public Boolean isTokenValid(String token, String requestEmail) {
        final String email = getEmail(token);
        return (email.equals(requestEmail) && !isExpired(token));
    }
}
