package com.sxrekord.chatting.common;

import lombok.Getter;

import java.util.stream.Stream;

/**
 * @author Rekord
 * @date 2023/3/17 14:08
 */
@Getter
public enum WSType {
    // 上线
    ONLINE(1),
    // 离线
    OFFLINE(2),

    // 文本单发
    TEXT_SINGLE_SENDING(3),
    // 文本群发
    TEXT_GROUP_SENDING(4),
    // 图片单发
    IMAGE_SINGLE_SENDING(5),
    // 图片群发
    IMAGE_GROUP_SENDING(6),
    // 文件单发
    FILE_SINGLE_SENDING(7),
    // 文件群发
    FILE_GROUP_SENDING(8);

    private final Integer id;

    WSType(Integer id) {
        this.id = id;
    }

    public static WSType getWSTypeById(int id) {
        return Stream.of(WSType.values())
                .filter(t -> t.getId() == id)
                .findFirst().orElse(null);
    }
}
