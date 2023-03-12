package com.sxrekord.chatting.service;

import com.sxrekord.chatting.model.po.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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

}
