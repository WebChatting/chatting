package com.sxrekord.chatting.model.po;

import lombok.Data;

/**
 * @author Rekord
 * @date 2022/9/12 16:03
 */
@Data
public class Group {
    private Long groupId;
    private String groupName;
    private String AvatarPath;

    public Group() {}

}
