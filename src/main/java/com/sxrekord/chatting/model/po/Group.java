package com.sxrekord.chatting.model.po;

import lombok.Data;

import java.util.List;

/**
 * @author Rekord
 * @date 2022/9/12 16:03
 */
@Data
public class Group {
    private Long id;
    private String name;
    private Long ownerId;
    private String avatarPath;
    private List<User> members;

    public Group() {}

    public Group(String name, Long ownerId, String avatarPath) {
        this.name = name;
        this.ownerId = ownerId;
        this.avatarPath = avatarPath;
    }
}
