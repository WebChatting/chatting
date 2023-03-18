package com.sxrekord.chatting.service;

import com.sxrekord.chatting.model.vo.ResponseJson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.util.Assert;

import javax.servlet.http.HttpSession;
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

        HttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("userId", 501L);

        System.out.println(messageService.loadMessage(0, calendar.getTime(), 503L, 2, httpSession));

        System.out.println(messageService.loadMessage(0, calendar.getTime(), 503L, Integer.MAX_VALUE, httpSession));

        System.out.println(messageService.loadMessage(1, calendar.getTime(), 101L, 2, httpSession));

        httpSession.setAttribute("userId", 503L);
        System.out.println(messageService.loadMessage(1, calendar.getTime(), 101L, Integer.MAX_VALUE, httpSession));
    }
}
