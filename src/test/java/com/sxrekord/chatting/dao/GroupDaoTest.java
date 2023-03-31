package com.sxrekord.chatting.dao;

import com.sxrekord.chatting.model.po.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Rekord
 * @date 2023/3/11 21:39
 */
@SpringBootTest
public class GroupDaoTest {
    @Autowired
    GroupDao groupDao;

    @Test
    public void testSearchGroupByName() {
        List<Group> feedback = null;
        System.out.println(feedback = groupDao.searchGroupByName("group"));
        Assert.isTrue(feedback != null, "test searchGroupByName error");
        System.out.println("test searchGroupByName success");
    }

    @Test
    public void testListGroupByOwnerId() {
        List<Group> feedback = null;
        System.out.println(feedback = groupDao.listGroupByOwnerId(501L));
        Assert.isTrue(feedback != null, "test listGroupByOwnerId error");
        System.out.println("test listGroupByOwnerId success");
    }

}
