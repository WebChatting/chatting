package com.sxrekord.chatting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Rekord
 * @date 2022/9/14 0:50
 */
@SpringBootTest
public class MessageServiceTest {

    @Autowired
    MessageService messageService;

    @Test
    public void loadMessageTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,1);

        System.out.println(messageService.loadMessage(0, calendar.getTime(), 503L, 2, 501L));

        System.out.println(messageService.loadMessage(0, calendar.getTime(), 503L, Integer.MAX_VALUE, 501L));

        System.out.println(messageService.loadMessage(1, calendar.getTime(), 101L, 2, 501L));

        System.out.println(messageService.loadMessage(1, calendar.getTime(), 101L, Integer.MAX_VALUE, 503L));

        System.out.println("test loadMessage success");
    }
}
