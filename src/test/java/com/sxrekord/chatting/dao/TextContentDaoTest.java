package com.sxrekord.chatting.dao;

import com.sxrekord.chatting.model.po.TextContent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        System.out.println(textContentDao.insertTextContent(new TextContent("test statement1")));
        System.out.println(textContentDao.insertTextContent(new TextContent("test statement2")));
    }

    @Test
    public void getTextContentTest() {
        System.out.println(textContentDao.getTextContentById(1001L));
        System.out.println(textContentDao.getTextContentById(1002L));
        System.out.println(textContentDao.getTextContentById(5002L));
    }
}
