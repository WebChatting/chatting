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

    @Test
    public void testGenerateAccessToken() {
        String token = JwtTokenUtil.generateAccessToken(new User(1001L,
                "JwtTokenUtilTestName", "JwtTokenUtilTestPassword", "avatarPath"));
    }

    @Test
    public void testIsTokenExpired() {
        String token = JwtTokenUtil.generateAccessToken(new User(1001L,
                "JwtTokenUtilTestName", "JwtTokenUtilTestPassword", "avatarPath"));
        assert JwtTokenUtil.isTokenExpired(token) == false;
    }

    @Test
    public void testIsSignatureValid() {
        String token = JwtTokenUtil.generateAccessToken(new User(1001L,
                "JwtTokenUtilTestName", "JwtTokenUtilTestPassword", "avatarPath"));
        assert JwtTokenUtil.isSignatureInvalid(token) == false;
    }
}
