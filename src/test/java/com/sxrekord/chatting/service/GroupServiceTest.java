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
public class GroupServiceTest {
    @Autowired
    GroupService groupService;

    @Test
    public void searchGroupTest() {
        System.out.println(groupService.searchGroup("group"));

        System.out.println("test searchGroup success");
    }

    @Test
    public void listGroupTest() {
        HttpSession session = new MockHttpSession();
        session.setAttribute("userId", 501L);
        System.out.println(groupService.listGroup(session));
        session.setAttribute("userId", 503L);
        System.out.println(groupService.listGroup(session));

        System.out.println("test listGroup success");
    }

}
