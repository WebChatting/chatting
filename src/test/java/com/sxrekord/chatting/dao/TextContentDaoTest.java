package com.sxrekord.chatting.dao;

import com.sxrekord.chatting.model.po.TextContent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/**
 * @author Rekord
 * @date 2022/9/13 20:38
 */
@SpringBootTest
public class TextContentDaoTest {
    @Autowired
    TextContentDao textContentDao;

    @Test
    public void insertTextContentTest() {
        TextContent textContent = new TextContent("test statement");
        textContentDao.insertTextContent(textContent);
        Assert.isTrue(textContent.getId() != null, "insert textContent fail!");
        System.out.println(textContent);
    }

    @Test
    public void getTextContentTest() {
        System.out.println(textContentDao.getTextContentById(1001L));
        System.out.println(textContentDao.getTextContentById(1002L));
        System.out.println(textContentDao.getTextContentById(5002L));
    }
}
