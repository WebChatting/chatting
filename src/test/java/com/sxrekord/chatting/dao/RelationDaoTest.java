package com.sxrekord.chatting.dao;

import com.sxrekord.chatting.model.po.Relation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Rekord
 * @date 2023/3/11 21:45
 */
@SpringBootTest
public class RelationDaoTest {
    @Autowired
    RelationDao relationDao;

    @Test
    public void testUpdateRelation() {
        int feedback = relationDao.updateRelation(new Relation(501L, 503L, 0, 2));
        Assert.isTrue(feedback == 1, "test updateRelation error");
        feedback = relationDao.updateRelation(new Relation(504L, 101L, 1, 1));
        Assert.isTrue(feedback == 1, "test updateRelation error");
        System.out.println("test updateRelation success");
    }

    @Test
    public void testListRelation() {
        List<Relation> feedback = null;
        // 根据ID列出所有好友
        System.out.println(feedback = relationDao.listRelation(503L, 0, 1, 0));
        System.out.println(feedback = relationDao.listRelation(503L, 0, 1, 1));

        // 根据ID列出所有加入的群组
        System.out.println(feedback = relationDao.listRelation(503L, 1, 1, 0));

        // 根据ID列出所有发起的验证（包括好友和群组）
        System.out.println(feedback = relationDao.listRelation(503L, 0, -1, 0));
        System.out.println(feedback = relationDao.listRelation(503L, 1, -1, 0));

        // 根据ID列出所有收到的验证（包括好友和群组）
        System.out.println(feedback = relationDao.listRelation(503L, 0, -1, 1));
        System.out.println(feedback = relationDao.listRelation(101L, 1, -1, 1));

        System.out.println("test listRelation success");
    }
}
