package com.sxrekord.chatting.service;

import com.sxrekord.chatting.dao.MessageDao;
import com.sxrekord.chatting.model.po.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Rekord
 * @date 2022/9/13 17:11
 */
@SpringBootTest
public class MessageServiceTest {

    @Autowired
    MessageDao messageDao;

    @Test
    public void getMessageTest() {
        System.out.println(messageDao.getMessageByFromIdAndToId(501L, 502L));
    }

    @Test
    public void insertMessageTest() {
        System.out.println(messageDao.insertMessage(new Message(501L, 503L, 0, 0, 1003L)));
    }
}
