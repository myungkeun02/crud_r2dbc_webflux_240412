package org.myungkeun.crud_r2dbc_webflux_2404112;

import java.security.*;
import java.util.Base64;

public class GenerateKey {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // RSA 알고리즘을 사용하여 KeyPairGenerator를 생성하고, generateKeyPair() 메서드를 호출하여 RSA 키 페어를 생성.
        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();

        // 생성된 키 페어에서 개인 키와 공개 키를 가져오기.
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        // 개인 키와 공개 키를 각각 바이트 배열로 가져오기.
        byte[] privateKeyBytes = privateKey.getEncoded();
        byte[] publicKeyBytes = publicKey.getEncoded();

        // Base64 클래스를 사용하여 바이트 배열을 Base64 문자열로 인코딩.
        String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKeyBytes);
        String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKeyBytes);

        // 인코딩된 개인 키와 공개 키를 출력.
        System.out.println("privateKeyBase64: " + privateKeyBase64);
        System.out.println("-------------------------------------");
        System.out.println("publicKeyBase64: " + publicKeyBase64);
    }
}
