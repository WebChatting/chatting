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

        redisUtils.set("chatting-expire-key", "chatting-expire-value", 10L);
        assert redisUtils.get("chatting-expire-key").equals("chatting-expire-value");

        redisUtils.set("test-getExpire", "test-getExpire", 10L);
        System.out.println(redisUtils.getExpire("test-getExpire"));
    }

    @Test
    public void testSize() {
        System.out.println(redisUtils.getSize());
    }

}
