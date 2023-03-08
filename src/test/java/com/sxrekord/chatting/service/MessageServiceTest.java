package com.sxrekord.chatting.service;

import com.sxrekord.chatting.model.vo.ResponseJson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/**
 * @author Rekord
 * @date 2022/9/14 0:50
 */
@SpringBootTest
public class MessageServiceTest {

    @Autowired
    MessageService messageService;

    @Test
    public void getFriendMessageTest() {
        ResponseJson responseJson = messageService.getFriendMessage(501L, 502L);
        Assert.isTrue(responseJson != null, "get friendMessage fail");
        System.out.println(responseJson);
    }

    @Test
    public void getGroupMessageTest() {
        ResponseJson responseJson = messageService.getGroupMessage(101L);
        Assert.isTrue(responseJson != null, "get groupMessage fail");
        System.out.println(responseJson);
    }
}
