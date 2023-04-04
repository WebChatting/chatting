package com.sxrekord.chatting.service;

import com.sxrekord.chatting.model.vo.ResponseJson;

/**
 * @author Rekord
 * @date 2023/3/9 20:37
 */
public interface GroupService {
    /**
     * 查询群组
     * @param name
     * @return
     */
    ResponseJson searchGroup(String name, Long userId);

    /**
     * 列出群组
     * @param userId
     * @return
     */
    ResponseJson listGroup(Long userId);

    /**
     * 创建群组
     * @param name
     * @param avatarPath
     * @param userId
     * @return
     */
    ResponseJson createGroup(String name, String avatarPath, Long userId);
}
