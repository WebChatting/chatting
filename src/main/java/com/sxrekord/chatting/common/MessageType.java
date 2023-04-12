package com.sxrekord.chatting.common;

import lombok.Getter;

/**
 * @author Rekord
 * @date 2023/3/17 23:03
 */
@Getter
public enum MessageType {
    /**
     * 文本
     */
    TEXT(0),
    /**
     * 图片
     */
    IMAGE(1),
    /**
     * 文件
     */
    FILE(2);

    private final int id;

    MessageType(int id) {
        this.id = id;
    }
}
