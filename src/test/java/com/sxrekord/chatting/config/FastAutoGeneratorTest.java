package com.sxrekord.chatting.config;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

/**
 * @author Rekord
 * @date 2023/3/31 15:35
 */
@SpringBootTest
public class FastAutoGeneratorTest {
    @Test
    public void testAutoGenerator() {
        FastAutoGenerator
                .create("jdbc:mysql://localhost:3306/chatting?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true",
                        "webchatting", "webchatting")
                .globalConfig(builder -> {
                    builder.author("Rekord") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("mybatis-plus-generator"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.sxrekord") // 设置父包名
                            .moduleName("chatting") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                                    "mybatis-plus-generator")); // 设置mapperXml生成路径
                })
                .templateConfig(builder -> {
                    builder.controller("") // 不生成controller
                            .service("").serviceImpl(""); // 不生成service
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok()
                            .mapperBuilder()
                            .build();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
