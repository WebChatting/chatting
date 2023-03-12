package com.sxrekord.chatting.dao;

import com.sxrekord.chatting.model.po.Relation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/**
 * @author Rekord
 * @date 2023/3/11 21:45
 */
@SpringBootTest
public class RelationDaoTest {
    @Autowired
    RelationDao relationDao;

    @Test
    public void testInsertRelation() {
        int feedback = relationDao.insertRelation(new Relation(501L, 505L, 0));
        Assert.isTrue(feedback == 1, "test insertRelation error");
        feedback = relationDao.insertRelation(new Relation(505L, 101L, 1));
        Assert.isTrue(feedback == 1, "test insertRelation error");
        System.out.println("test insertRelation success");
    }

    @Test
    public void testUpdateRelation() {
        int feedback = relationDao.updateRelation(new Relation(501L, 503L, 0, 2));
        Assert.isTrue(feedback == 1, "test updateRelation error");
        feedback = relationDao.updateRelation(new Relation(504L, 101L, 1, 1));
        Assert.isTrue(feedback == 1, "test updateRelation error");
        System.out.println("test updateRelation success");
    }

}
