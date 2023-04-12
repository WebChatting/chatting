package com.sxrekord.chatting.common;

/**
 * @author Rekord
 * @date 2023/4/12 22:41
 */
public class ExpirePolicy {
    public static final Integer EXPIRE_DAY_INTERVAL = 7;
    /**
     * 直接过期
      */
    public static final Integer DIRECT_EXPIRATION = 0;
    /**
     * 定时过期
     */
    public static final Integer TIMED_EXPIRATION = 1;

}
