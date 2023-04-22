package com.sxrekord.chatting.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Rekord
 * @date 2023/4/21 15:31
 */
@SpringBootTest
public class SecurityUtilsTest {
    @Test
    public void testSecurityUtils() {
        String cipher = SecurityUtils.encrypt("member001");
        System.out.println(cipher);
        System.out.println(SecurityUtils.decrypt(cipher));
    }
}
