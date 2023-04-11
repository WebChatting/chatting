package com.sxrekord.chatting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Rekord
 * @date 2023/4/11 11:42
 */
@SpringBootTest
public class FileServiceTest {
    @Autowired
    FileService fileService;
    @Test
    public void testTidyUp() {
        fileService.tidyUp();
    }
}
