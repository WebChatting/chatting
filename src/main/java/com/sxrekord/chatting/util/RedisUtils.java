package com.sxrekord.chatting.util;

/**
 * @author Rekord
 * @date 2023/4/10 17:41
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 写入redis缓存，设置expire存活时间(以小时为单位)
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public boolean setByHours(final String key, String value, Long expire) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(key, value);
            stringRedisTemplate.expire(key, expire, TimeUnit.HOURS);
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
     * 判断redis缓存中是否有对应的key
     *
     * @param key
     * @return
     */
    public boolean exist(final String key) {
        boolean result = false;
        try {
            result = stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * redis根据key删除对应的value
     *
     * @param key
     * @return
     */
    public boolean remove(final String key) {
        boolean result = false;
        try {
            if (exist(key)) {
                stringRedisTemplate.delete(key);
            }
            result = true;
        } catch (Exception e) {
            log.error("redis根据key删除对应的value失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * Redis根据keys批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 存储对象（设置过期时间）
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public boolean setObject(String key, Object value, long expire) {
        try {
            redisTemplate.opsForValue().set(key, value, expire, TimeUnit.MILLISECONDS);
            return true;
        } catch (Exception e) {
            log.error("----------------->redis 写入object失败" + e.getMessage());
            return false;
        }
    }

    /**
     * 存储对象（不设置过期时间）
     * @param key
     * @param value
     * @return
     */
    public boolean setObject(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("----------------->redis 写入object失败" + e.getMessage());
            return false;
        }
    }

    /**
     * 获取对象
     * @param key
     * @return
     */
    public Object getObject(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("redis 获取object异常" + e.getMessage());
            return null;
        }
    }

}
