package com.sxrekord.chatting.service;

import com.sxrekord.chatting.model.vo.ResponseJson;

import javax.servlet.http.HttpSession;

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
    ResponseJson searchGroup(String name, HttpSession session);

    /**
     * 列出群组
     * @param session
     * @return
     */
    ResponseJson listGroup(HttpSession session);

    ResponseJson createGroup(String name, String avatarPath, HttpSession session);
}
