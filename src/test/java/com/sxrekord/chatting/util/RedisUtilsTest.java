package com.sxrekord.chatting.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Rekord
 * @date 2023/3/16 10:16
 */
@SpringBootTest
public class RedisUtilsTest {
    @Autowired
    RedisUtils redisUtils;

    @Test
    public void testSet() {
        redisUtils.set("chatting-key", "chatting-value");
        assert redisUtils.get("chatting-key").equals("chatting-value");
    }

}
