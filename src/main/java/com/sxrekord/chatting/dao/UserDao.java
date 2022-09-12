package com.sxrekord.chatting.dao;

import com.sxrekord.chatting.model.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Rekord
 * @date 2022/9/12 12:17
 */
@Mapper
public interface UserDao {
    /**
     * 插入用户
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 根据用户ID查询用户
     * @param userId
     * @return
     */
    User getUserById(@Param("userId") long userId);

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    User getUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User getUserByUsername(@Param("username") String username);
}
