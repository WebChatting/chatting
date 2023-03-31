package com.sxrekord.chatting.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Rekord
 * @date 2023/2/19 0:12
 */

@Data
@TableName("image_content")
public class ImageContent {
    private Long id;
    private String path;

    public ImageContent() {}

    public ImageContent(String path) {
        this.path = path;
    }
}
