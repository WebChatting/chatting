package com.sxrekord.chatting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        System.out.println(groupService.searchGroup("group", 501L));
        System.out.println(groupService.searchGroup("", 501L));

        System.out.println("test searchGroup success");
    }

    @Test
    public void listGroupTest() {
        System.out.println(groupService.listGroup(501L));
        System.out.println(groupService.listGroup(503L));

        System.out.println("test listGroup success");
    }

    @Test
    public void createGroup() {
        System.out.println(groupService.createGroup("testGroup", "test_group_avatar_path", 502L));
        System.out.println(groupService.createGroup("testGroup2", "", 504L));

        System.out.println("test createGroup success");
    }

}
