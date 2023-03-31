package com.sxrekord.chatting.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Rekord
 * @date 2022/9/12 16:21
 */
@Data
@TableName("text_content")
public class TextContent {
    private Long id;
    private String content;

    public TextContent() {}

    public TextContent(String content) {
        this.content = content;
    }
}
