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
    @Value("#{ T(java.lang.System).getProperty('os.name').contains('Windows') ? '${file.upload.location.windows}' : '${file.upload.location.linux}' }")
    private String FILE_STORE_PATH;

    @Test
    public void testRemoveFile() {
        boolean feedback = FileUtils.removeFile(FILE_STORE_PATH + "hello.txt");

        System.out.println("test removeFile success");
    }

    @Test
    public void testCheckFileEquality() {
        boolean feedback = FileUtils.checkFileEquality("C:\\Users\\Administrator\\Downloads\\设备故障明细2023-4-6.xlsx",
                "C:\\Users\\Administrator\\Downloads\\设备故障明细2023-4-6 - 副本.xlsx");

        System.out.println("test checkFileEquality success");
    }

    @Test
    public void testCalculateFileHash() throws Exception {
        System.out.println(FileUtils.calculateFileHash("C:\\Users\\rekord\\Downloads\\笔记代码.zip"));
    }
}
