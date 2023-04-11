package com.sxrekord.chatting.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rekord
 * @date 2022/9/12 16:03
 */
@Data
@TableName("`group`")
@NoArgsConstructor
public class Group {
    private Long id;
    private String name;
    private Long ownerId;
    private String avatarPath;
    @TableField("file_id")
    private Long fileId;

    public Group(String name, Long ownerId, String avatarPath) {
        this.name = name;
        this.ownerId = ownerId;
        this.avatarPath = avatarPath;
    }

    public Group(Long id, Long fileId) {
        this.id = id;
        this.fileId = fileId;
    }
}
