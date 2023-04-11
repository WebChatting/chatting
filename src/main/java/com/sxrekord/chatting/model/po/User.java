package com.sxrekord.chatting.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Rekord
 * @date 2022/9/12 12:14
 */
@Data
@TableName("user")
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password;
    private String avatarPath;
    @TableField("file_id")
    private Long fileId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("update_time")
    private Date updateTime;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String avatarPath) {
        this(username, password);
        this.avatarPath = avatarPath;
    }

    public User(Long id, String username, String password, String avatarPath) {
        this(username, password, avatarPath);
        this.id = id;
    }

    public User(Long id, Long fileId) {
        this.id = id;
        this.fileId = fileId;
    }
}
