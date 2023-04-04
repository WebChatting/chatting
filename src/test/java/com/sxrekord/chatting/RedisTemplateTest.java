package com.sxrekord.chatting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @author Rekord
 * @date 2023/3/16 10:16
 */
@SpringBootTest
public class RedisTemplateTest {
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void test() {
        // 1.通过RedisTemplate获取操作String类型的ValueOperations对象
        ValueOperations ops = redisTemplate.opsForValue();
        // 2.插入一条数据
        ops.set("blogName","Vz-Blog");

        // 3.获取数据
        String blogName = (String) ops.get("blogName");
        System.out.println("blogName = " + blogName);
    }
}
