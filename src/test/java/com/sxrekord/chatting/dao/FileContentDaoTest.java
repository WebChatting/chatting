package com.sxrekord.chatting.dao;

import com.sxrekord.chatting.model.po.FileContent;
import com.sxrekord.chatting.model.po.TextContent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.io.File;

/**
 * @author Rekord
 * @date 2022/9/13 20:24
 */
@SpringBootTest
public class FileContentDaoTest {

    @Autowired
    FileContentDao fileContentDao;

    @Test
    public void insertFileContentTest() {
        FileContent fileContent = new FileContent("法语成绩单.pdf", "165.6KB", "/UploadFile/b88cbb8786604ea6bcba0be61743de5e.pdf");
        fileContentDao.insertFileContent(fileContent);
        Assert.isTrue(fileContent.getId() != null, "insert fileContent fail!");
        System.out.println(fileContent);
    }

    @Test
    public void getFileContentTest() {
        System.out.println(fileContentDao.getFileContentById(1001L));
        System.out.println(fileContentDao.getFileContentById(1002L));
        System.out.println(fileContentDao.getFileContentById(5002L));
    }
}
