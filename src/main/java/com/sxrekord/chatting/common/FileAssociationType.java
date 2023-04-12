package com.sxrekord.chatting.common;

/**
 * @author Rekord
 * @date 2023/4/12 20:50
 */
public enum FileAssociationType {
    /**
     * 用户头像文件
     */
    UserAvatar(1),
    /**
     * 群组头像文件
     */
    GroupAvatar(2),
    /**
     * 图片消息内容
     */
    ImageContent(3),
    /**
     * 文件消息内容
     */
    FileContent(4);

    private final Integer id;

    FileAssociationType(Integer id) {
        this.id = id;
    }
}
