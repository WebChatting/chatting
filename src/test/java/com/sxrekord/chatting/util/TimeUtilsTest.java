package com.sxrekord.chatting.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author Rekord
 * @date 2023/4/12 22:46
 */
@SpringBootTest
public class TimeUtilsTest {
    @Test
    public void testSubtractDaysFromDate() {
        System.out.println(new Date());
        System.out.println(TimeUtils.subtractDaysFromDate(new Date(), 7));
    }
}
