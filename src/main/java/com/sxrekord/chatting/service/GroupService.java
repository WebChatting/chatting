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
    ResponseJson searchGroup(String name);
}
