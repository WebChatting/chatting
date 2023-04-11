package com.sxrekord.chatting.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("update_time")
    private Date updateTime;

    public ImageContent(String path) {
        this.path = path;
    }

    public ImageContent(Long id, Long fileId) {
        this.id = id;
        this.fileId = fileId;
    }
}
