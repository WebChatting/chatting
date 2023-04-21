package com.sxrekord.chatting.util;

import lombok.extern.slf4j.Slf4j;

import java.security.*;

/**
 * @author Rekord
 * @date 2023/4/21 15:24
 */
@Slf4j
public class SecurityUtils {
    private static final KeyPair keyPair;
    public static final PublicKey publicKey;
    public static final PrivateKey privateKey;

    static {
        try {
            keyPair = generateKeyPair("RSA", 2048);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
        // 打印密钥
        log.info("Public key: " + publicKey.toString());
        log.info("Private Key: " + privateKey.toString());
    }


    private static KeyPair generateKeyPair(String algorithm, int keyLength) throws NoSuchAlgorithmException {
        // 选择算法
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
        // 初始化密钥长度
        keyGen.initialize(keyLength);
        // 生成密钥对
        return keyGen.generateKeyPair();
    }
}
