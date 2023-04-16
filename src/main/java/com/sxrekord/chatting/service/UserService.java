package com.sxrekord.chatting.service;

import com.sxrekord.chatting.model.po.User;
import com.sxrekord.chatting.model.vo.ResponseJson;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Rekord
 * @date 2022/9/12 12:33
 */
public interface UserService {

    /**
     * 用户注册
     * @param user
     * @return
     */
    ResponseJson registerUser(User user);

    /**
     * 用户登录
     * @param user
     * @param response
     * @return
     */
    ResponseJson loginUser(User user, HttpServletResponse response);

    /**
     * 用户登出
     * @param token
     * @return
     */
    ResponseJson logoutUser(String token);

    /**
     * 更新用户信息
     * @param user
     * @param userId
     * @return
     */
    ResponseJson updateUser(User user, Long userId);

    /**
     * 查询用户
     * @param username
     * @param userId
     * @return
     */
    ResponseJson searchUser(String username, Long userId);
}
