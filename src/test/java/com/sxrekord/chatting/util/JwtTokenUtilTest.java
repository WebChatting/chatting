package com.sxrekord.chatting.util;

import com.sxrekord.chatting.model.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Rekord
 * @date 2023/4/9 22:51
 */
@SpringBootTest
public class JwtTokenUtilTest {

    private User user = new User(1001L,
            "JwtTokenUtilTestName", "JwtTokenUtilTestPassword", "avatarPath");
    @Test
    public void testGenerateAccessToken() {
        String token = JwtTokenUtils.generateAccessToken(user);
    }

    @Test
    public void testIsTokenExpired() {
        String token = JwtTokenUtils.generateAccessToken(user);
        assert JwtTokenUtils.isTokenExpired(token) == false;
    }

    @Test
    public void testIsSignatureValid() {
        String token = JwtTokenUtils.generateAccessToken(user);
        assert JwtTokenUtils.isSignatureInvalid(token) == false;
    }

    @Test
    public void testRefreshAccessToken() throws InterruptedException {
        String token = JwtTokenUtils.generateAccessToken(user);
        Thread.sleep(3000);
        String newToken = JwtTokenUtils.refreshAccessToken(token);
    }

    @Test
    public void testParseRemainingExpirationTime() {
        String token = JwtTokenUtils.generateAccessToken(user);
        System.out.println(JwtTokenUtils.parseRemainingExpirationTime(token));
        System.out.println(30*60*1000);
    }
}
