package com.sxrekord.chatting.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @author Rekord
 * @date 2022/9/12 16:03
 */
@Data
@TableName("group")
public class Group {
    private Long id;
    private String name;
    private Long ownerId;
    private String avatarPath;

    public Group() {}

    public Group(String name, Long ownerId, String avatarPath) {
        this.name = name;
        this.ownerId = ownerId;
        this.avatarPath = avatarPath;
    }
}
