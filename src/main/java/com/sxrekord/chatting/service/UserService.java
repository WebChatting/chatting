package com.sxrekord.chatting.service;

import com.sxrekord.chatting.model.po.User;
import com.sxrekord.chatting.model.vo.ResponseJson;

import javax.servlet.http.HttpSession;

/**
 * @author Rekord
 * @date 2022/9/12 12:33
 */
public interface UserService {

    /**
     * 用户注册
     * @param username
     * @param password
     * @return
     */
    ResponseJson registerUser(String username, String password);

    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    ResponseJson loginUser(String username, String password, HttpSession session);

    /**
     * 用户登出
     * @param session
     * @return
     */
    ResponseJson logoutUser(HttpSession session);
}
