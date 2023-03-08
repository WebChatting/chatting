package com.sxrekord.chatting.model.po;

import lombok.Data;

import java.util.List;

/**
 * @author Rekord
 * @date 2022/9/12 16:03
 */
@Data
public class Group {
    private Long groupId;
    private String groupName;
    private String AvatarPath;
    private Long owner;
    private List<User> members;

    public Group() {}

}
