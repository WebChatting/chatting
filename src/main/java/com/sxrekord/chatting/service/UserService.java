package com.sxrekord.chatting.service;

import com.sxrekord.chatting.model.vo.ResponseJson;

import javax.servlet.http.HttpServletResponse;

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
     * @return
     */
    ResponseJson loginUser(String username, String password, HttpServletResponse response);

    /**
     * 用户登出
     * @param token
     * @return
     */
    ResponseJson logoutUser(String token);

    /**
     * 更新用户信息
     * @param username
     * @param password
     * @param avatarPath
     * @return
     */
    ResponseJson updateUser(String username, String password, String avatarPath, Long userId);

    /**
     * 查询用户
     * @param username
     * @return
     */
    ResponseJson searchUser(String username, Long userId);
}
