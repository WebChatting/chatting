package com.sxrekord.chatting.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxrekord.chatting.model.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Rekord
 * @date 2022/9/12 12:17
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

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

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    List<User> searchUserByUsername(@Param("username") String username);
}
