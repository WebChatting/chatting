package com.sxrekord.chatting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpSession;

/**
 * @author Rekord
 * @date 2023/3/11 21:46
 */
@SpringBootTest
public class RelationServiceTest {
    @Autowired
    RelationService relationService;

    @Test
    public void testListRelation() {
        HttpSession session = new MockHttpSession();
        session.setAttribute("userId", 503L);
        // 列出所有好友
        System.out.println(relationService.listRelation(0, 1, -1, session));

        // 列出所有加入的群组
        System.out.println(relationService.listRelation(1, 1, 0, session));

        // 列出所有发起的验证
        System.out.println(relationService.listRelation(0, -1, 0, session));
        System.out.println(relationService.listRelation(1, -1, 0, session));

        // 列出所有收到的验证
        System.out.println(relationService.listRelation(0, -1, 1, session));
        session.setAttribute("userId", 101L);
        System.out.println(relationService.listRelation(1, -1, 1, session));

        System.out.println("test listRelation success");
    }

}
