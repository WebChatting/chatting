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
    public void testInsertAndDeleteRelation() {
        int feedback = relationDao.insertRelation(new Relation(501L, 505L, 0));
        Assert.isTrue(feedback == 1, "test insertRelation error");
        feedback = relationDao.deleteRelation(new Relation(501L, 505L, 0));
        Assert.isTrue(feedback == 1, "test deleteRelation error");

        feedback = relationDao.insertRelation(new Relation(505L, 101L, 1));
        Assert.isTrue(feedback == 1, "test insertRelation error");
        feedback = relationDao.deleteRelation(new Relation(505L, 101L, 1));
        Assert.isTrue(feedback == 1, "test deleteRelation error");

        System.out.println("test insertRelation and deleteRelation success");
    }

    @Test
    public void testUpdateRelation() {
        int feedback = relationDao.updateRelation(new Relation(501L, 503L, 0, 2));
        Assert.isTrue(feedback == 1, "test updateRelation error");
        feedback = relationDao.updateRelation(new Relation(504L, 101L, 1, 1));
        Assert.isTrue(feedback == 0, "test updateRelation error");
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

    @Test
    public void testListUserIdByGroupId() {
        List<Long> feedback = null;

        System.out.println(feedback = relationDao.listUserIdByGroupId(101L));
        Assert.isTrue(feedback.size() == 0, "test listUserIdByGroupId error");
        System.out.println(feedback = relationDao.listUserIdByGroupId(102L));
        Assert.isTrue(feedback.size() == 1, "test listUserIdByGroupId error");
        System.out.println("test listUserIdByGroupId success");
    }

    @Test
    public void testDeleteRelationByTypeAndAcceptId() {
        int feedback = relationDao.deleteRelationByTypeAndAcceptId(1, 101L);
        Assert.isTrue(feedback == 3, "test deleteRelationByTypeAndAcceptId error");
        feedback = relationDao.deleteRelationByTypeAndAcceptId(0, 501L);
        Assert.isTrue(feedback == 2, "test deleteRelationByTypeAndAcceptId error");

        System.out.println("test deleteRelationByTypeAndAcceptId success");
    }

    @Test
    public void testSearchRelation() {
        Relation feedback = null;

        System.out.println(feedback = relationDao.searchRelation(new Relation(501L, 505L, 0)));
        Assert.isTrue(feedback == null, "test searchRelation error");
        System.out.println(feedback = relationDao.searchRelation(new Relation(501L, 502L, 0)));
        Assert.isTrue(feedback != null, "test searchRelation error");

        // 注意，群主与群组在relation表中没有记录
        System.out.println(feedback = relationDao.searchRelation(new Relation(502L, 101L, 1)));
        Assert.isTrue(feedback == null, "test searchRelation error");
        System.out.println(feedback = relationDao.searchRelation(new Relation(501L, 101L, 1)));
        Assert.isTrue(feedback == null, "test searchRelation error");

        System.out.println("test searchRelation success");
    }
}
