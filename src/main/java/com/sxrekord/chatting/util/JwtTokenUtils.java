package com.sxrekord.chatting.util;

import com.sxrekord.chatting.model.po.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Rekord
 * @date 2023/4/9 22:32
 */
@Slf4j
public class JwtTokenUtils {
    private static final int EXPIRATION_TIME = 30 * 60 * 1000;
    private static final String ISSUER = "Chatting";

    /**
     * generate by using generateKey(256)
     */
    private static final String SECRET_KEY = "+Dns1uYbRurxFFaK2ItygPLnwiwaEzTpT4IypgVv1Mc=";

    /**
     * Java Cryptography Architecture (JCA)
     */
    private static final Key key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());

    private static final Map<String, Object> headers = new HashMap<>();

    static {
        headers.put("alg", "HS256");
        headers.put("typ", "JWT");
    }

    public static String generateAccessToken(User user) {
        /**
         * JWT 是一种以紧凑、可验证的形式在两方之间传输信息的方法。
         * JWT 主体中编码的信息位称为claim。 JWT 的扩展形式是 JSON 格式，因此每个claim都是 JSON 对象中的一个键。
         * JWT 可以加密签名（使其成为 JWS）或加密（使其成为 JWE）。
         *
         * JWS = encodedHeader + '.' + encodedClaims + hmacSha256(concatenated, key)
         */
        Map<String, Object> claims = new HashMap<>(1);
        claims.put("userId", user.getId());
        return Jwts.builder()
                .setHeader(headers)
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuer(ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static boolean isTokenExpired(String token) {
        return parseAccessToken(token).getExpiration().before(new Date());
    }

    public static boolean isSignatureInvalid(String token) {
        try {
            String signature = Jwts.builder()
                .setHeader(headers)
                .setClaims(parseAccessToken(token))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // 验证数字签名
            return !signature.equals(token);
        } catch (JwtException e) {
            // 如果解析或验证失败，则token无效
            return false;
        }
    }

    /**
     * 更新过期时间
     * @param token
     * @return
     */
    public static String refreshAccessToken(String token) {
        return refreshAccessToken(token, new Date(System.currentTimeMillis() + EXPIRATION_TIME));
    }

    /**
     * 将token过期
     * @param token
     * @return
     */
    public static String expireAccessToken(String token) {
        return refreshAccessToken(token, new Date(System.currentTimeMillis() - EXPIRATION_TIME));
    }

    /**
     * 从token中解析出userId
     * @param token
     * @return
     */
    public static Long parseUserId(String token) {
        return (Long)parseAccessToken(token).get("userId");
    }

    private static String refreshAccessToken(String token, Date expirationTime) {
        return Jwts.builder()
                .setHeader(headers)
                .setClaims(parseAccessToken(token))
                .setIssuedAt(new Date())
                .setExpiration(expirationTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private static Claims parseAccessToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        log.info("claims: " + claims);
        return claims;
    }

    /**
     *
     * @param keySizeBits: 256 or 384 or 512
     * @return
     */
    private static String generateKey(int keySizeBits) {
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyGen.init(keySizeBits);
        SecretKey secretKey = keyGen.generateKey();
        byte[] encodedKey = secretKey.getEncoded();
        String base64Key = Base64.getEncoder().encodeToString(encodedKey);
        log.info("Generated key: " + base64Key);
        return base64Key;
    }
}
