package com.sxrekord.chatting.util;

/**
 * @author Rekord
 * @date 2023/4/10 17:41
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Slf4j
@Component
public class RedisUtils {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 写入redis缓存（不设置expire存活时间）
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, String value) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            log.error("写入redis缓存失败！错误信息为: " + e.getMessage());
        }
        return result;
    }

    /**
     * 写入redis缓存，设置expire存活时间(以秒为单位)
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public boolean set(final String key, String value, Long expire) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(key, value);
            stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("写入redis缓存（设置expire存活时间）失败！错误信息为：" + e.getMessage());
        }
        return result;
    }


    /**
     * 读取Redis缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            result = operations.get(key);
        } catch (Exception e) {
            log.info("读取redis缓存失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * Get the total number of available keys in currently selected database.
     * @return
     */
    public int getSize() {
        Long size = (Long)stringRedisTemplate.execute(RedisServerCommands::dbSize);
        return size.intValue();
    }

    /**
     * 获取过期时间
     * @param key
     * @return The command returns -2 if the key does not exist.
     *         The command returns -1 if the key exists but has no associated expire.
     */
    public long getExpire(String key) {
        return stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 往set中添加一个元素
     * @param key
     * @param value
     * @param timeout
     * @return
     */
    public Long sAdd(String key, String value, long timeout) {
        long result = stringRedisTemplate.opsForSet().add(key, value);
        stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        return result;
    }

    /**
     * 判断set中是否存在某一元素
     * @param key
     * @param value
     * @return
     */
    public boolean sIsMember(String key, String value) {
        return stringRedisTemplate.opsForSet().isMember(key, value);
    }
}
