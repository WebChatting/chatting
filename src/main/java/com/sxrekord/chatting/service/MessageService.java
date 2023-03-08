package com.sxrekord.chatting.service;

import com.sxrekord.chatting.model.vo.ResponseJson;

/**
 * @author Rekord
 * @date 2022/9/13 21:21
 */
public interface MessageService {
    /**
     * 获取好友历史消息
     * @param fromId
     * @param toId
     * @return
     */
    ResponseJson getFriendMessage(Long fromId, Long toId);

    /**
     * 获取群组历史消息
     * @param groupId
     * @return
     */
    ResponseJson getGroupMessage(Long groupId);
}
