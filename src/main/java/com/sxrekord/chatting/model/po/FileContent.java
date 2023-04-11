package com.sxrekord.chatting.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Rekord
 * @date 2022/9/12 16:22
 */
@Data
@TableName("file_content")
@NoArgsConstructor
public class FileContent {
    private Long id;
    private String name;
    private String size;
    private String path;
    @TableField("file_id")
    private Long fileId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("update_time")
    private Date updateTime;

    public FileContent(String name, String size, String path) {
        this.name = name;
        this.size = size;
        this.path = path;
    }

    public FileContent(Long id, Long fileId) {
        this.id = id;
        this.fileId = fileId;
    }
}
