package com.sxrekord.chatting.service;

import com.sxrekord.chatting.model.po.Relation;
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
        // 列出所有好友
        System.out.println(relationService.listRelation(0, 1, -1, 503L));

        // 列出所有加入的群组
        System.out.println(relationService.listRelation(1, 1, 0, 503L));

        // 列出所有发起的验证
        System.out.println(relationService.listRelation(0, -1, 0, 503L));
        System.out.println(relationService.listRelation(1, -1, 0, 503L));

        // 列出所有收到的验证
        System.out.println(relationService.listRelation(0, -1, 1, 503L));
        System.out.println(relationService.listRelation(1, -1, 1, 101L));

        System.out.println("test listRelation success");
    }

    @Test
    public void testCreateRelation() {
        HttpSession session = new MockHttpSession();
        System.out.println(relationService.createRelation(new Relation(505L, 0), 501L));
        System.out.println(relationService.createRelation(new Relation(101L, 1), 505L));

        System.out.println("test createRelation success");
    }

    @Test
    public void testUpdateRelation() {
        System.out.println(relationService.updateRelation(new Relation(504L, 0, 1), 502L));
        System.out.println(relationService.updateRelation(new Relation(101L, 1, 1), 502L));

        // 删除关系（特殊更新）
        System.out.println(relationService.updateRelation(new Relation(501L, 0, 3), 502L));

        System.out.println("test updateRelation success");
    }
}
