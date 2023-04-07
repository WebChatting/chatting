package com.sxrekord.chatting.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Rekord
 * @date 2023/4/7 9:59
 */
@SpringBootTest
public class FileUtilsTest {
    @Value("${file.upload.location}")
    private String FILE_STORE_PATH;

    @Test
    public void testRemoveFile() {
        boolean feedback = FileUtils.removeFile(FILE_STORE_PATH + "hello.txt");

        System.out.println("test removeFile success");
    }

}
