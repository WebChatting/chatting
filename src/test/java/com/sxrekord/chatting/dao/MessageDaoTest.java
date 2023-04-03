package com.sxrekord.chatting.dao;

import com.sxrekord.chatting.model.po.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Rekord
 * @date 2022/9/13 17:11
 */
@SpringBootTest
public class MessageDaoTest {

    @Autowired
    MessageDao messageDao;

    @Test
    public void listMessageTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,1);

        List<Message> feedback = null;
        // 列出2条私聊消息
        System.out.println(feedback = messageDao.listMessage(0, calendar.getTime(), 501L, 503L, 2));
        Assert.isTrue(feedback.size() == 2, "list message error");
        // 列出大量私聊消息
        System.out.println(feedback = messageDao.listMessage(0, calendar.getTime(), 501L, 503L, Integer.MAX_VALUE));
        Assert.isTrue(feedback.size() == 4, "list message error");

        // 列出2条群聊消息
        System.out.println(feedback = messageDao.listMessage(1, calendar.getTime(), 501L, 101L, 2));
        Assert.isTrue(feedback.size() == 2, "list message error");
        // 列出大量群聊消息
        System.out.println(feedback = messageDao.listMessage(1, calendar.getTime(), 503L, 101L, Integer.MAX_VALUE));
        Assert.isTrue(feedback.size() == 2, "list message error");

        System.out.println("list message success");
    }
}
