package com.sxrekord.chatting.service;

import com.sxrekord.chatting.dao.FileContentDao;
import com.sxrekord.chatting.dao.MessageDao;
import com.sxrekord.chatting.dao.TextContentDao;
import com.sxrekord.chatting.model.po.FileContent;
import com.sxrekord.chatting.model.po.Message;
import com.sxrekord.chatting.model.po.TextContent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/**
 * @author Rekord
 * @date 2022/9/13 17:11
 */
@SpringBootTest
public class MessageServiceTest {

    @Autowired
    FileContentDao fileContentDao;
    @Autowired
    TextContentDao textContentDao;
    @Autowired
    MessageDao messageDao;

    @Test
    public void getMessageTest() {
        System.out.println(messageDao.getMessageByFromIdAndToId(501L, 502L));
    }

    @Test
    public void insertMessageTest() {
        int feedback = 0;
        TextContent textContent = new TextContent("text message");
        textContentDao.insertTextContent(textContent);
        feedback = messageDao.insertMessage(new Message(501L, 503L, 0, 0, textContent.getId()));
        Assert.isTrue(feedback == 1, "insert text message error!");

        FileContent fileContent = new FileContent("test.pdf", "322KB", "/UploadFile/b88cbb8786604ea6bcba0be61743de5e.pdf");
        fileContentDao.insertFileContent(fileContent);
        feedback = messageDao.insertMessage(new Message(501L, 503L, 0, 1, fileContent.getId()));
        Assert.isTrue(feedback == 1, "insert file message error");

        System.out.println("insert message success");
    }
}
