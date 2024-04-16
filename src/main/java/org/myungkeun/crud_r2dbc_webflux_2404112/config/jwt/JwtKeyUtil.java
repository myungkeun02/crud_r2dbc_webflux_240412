package org.myungkeun.crud_r2dbc_webflux_2404112.config.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Component
public class JwtKeyUtil {
    @Value("${application.security.jwt.secretkey}")
    private String secretKey;

    // application.properties 또는 application.yml 파일에서 설정한 secretKey 값을 가져와서
    // 해당 값으로부터 개인 키를 가져오는 메서드
    public Key getPrivateKey() {
        try {
            // Base64로 인코딩된 secretKey를 디코딩하여 바이트 배열로 변환
            byte[] privateKeyBytes = Base64.getDecoder().decode(secretKey);
            // PKCS8EncodedKeySpec를 사용하여 PKCS#8 형식의 개인 키 명세를 생성.
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);
            // RSA 알고리즘을 사용하는 KeyFactory를 생성.
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            // 명세를 사용하여 개인 키를 생성하고 반환.
            return keyFactory.generatePrivate(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
            // 예외가 발생하면 런타임 예외로 래핑하여 전달
            throw new RuntimeException(exception);
        }
    }
}
