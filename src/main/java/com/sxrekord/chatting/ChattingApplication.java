package com.sxrekord.chatting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Rekord
 * @date 2022/9/10 13:17
 */

@SpringBootApplication
@EnableTransactionManagement
public class ChattingApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChattingApplication.class, args);
    }
}
