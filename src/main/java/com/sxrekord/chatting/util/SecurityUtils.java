package com.sxrekord.chatting.util;


import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author Rekord
 * @date 2023/4/22 14:32
 */
@Slf4j
public class SecurityUtils {

    /**
     * 私钥的base64字符串
     */
    private static final String privateKeyBase64;
    /**
     * 公钥的base64字符串
     */
    private static final String publicKeyBase64;

    public static String getPublicKeyBase64() {
        return publicKeyBase64;
    }

    private static final String algorithm = "RSA";
    private static final int keySize = 1024;
    private static final String charsetName = "UTF-8";
    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final Base64.Decoder decoder = Base64.getDecoder();

    /**
     * 获取公钥私钥
     */
    static {
        KeyPair keyPair = generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();

        privateKeyBase64 = new String(encoder.encode((privateKey.getEncoded())));
        publicKeyBase64 = new String(encoder.encode(publicKey.getEncoded()));

        log.debug("publicKey: " + publicKeyBase64);
        log.debug("privateKey: " + privateKeyBase64);
    }

    /**
     * RSA公钥加密
     * @param str
     * @return
     */
    public static String encrypt(String str) {
        try {
            byte[] decoded = decoder.decode(publicKeyBase64);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(algorithm).generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return encoder.encodeToString(cipher.doFinal(str.getBytes(charsetName)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * RSA私钥解密
     * @param str
     * @return
     */
    public static String decrypt(String str) {
        try {
            byte[] inputByte = decoder.decode(str.getBytes(charsetName));
            byte[] decoded = decoder.decode(privateKeyBase64);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(algorithm).generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            return new String(cipher.doFinal(inputByte));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static KeyPair generateKeyPair() {
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyGen.initialize(keySize, new SecureRandom());
        return keyGen.generateKeyPair();
    }
}
