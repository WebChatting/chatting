package com.sxrekord.chatting.service;

import com.sxrekord.chatting.model.vo.ResponseJson;

/**
 * @author Rekord
 * @date 2022/9/13 21:21
 */
public interface MessageService {
    /**
     * 获取消息
     * @return
     */
    ResponseJson getMessage();
}
