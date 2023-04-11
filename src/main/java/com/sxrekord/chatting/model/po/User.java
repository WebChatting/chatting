package com.sxrekord.chatting.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

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
