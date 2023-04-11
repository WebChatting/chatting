package com.sxrekord.chatting.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rekord
 * @date 2023/2/19 0:12
 */

@Data
@TableName("image_content")
@NoArgsConstructor
public class ImageContent {
    private Long id;
    private String path;
    @TableField("file_id")
    private Long fileId;

    public ImageContent(String path) {
        this.path = path;
    }

    public ImageContent(Long id, Long fileId) {
        this.id = id;
        this.fileId = fileId;
    }
}
