package com.sxrekord.chatting.service;

import com.sxrekord.chatting.model.vo.ResponseJson;

/**
 * @author Rekord
 */
public interface UserInfoService {

    /**
     * 通过用户 ID 获取用户信息
     * @param userId
     * @return
     */
    ResponseJson getByUserId(String userId);
}
