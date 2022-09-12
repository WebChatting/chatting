package com.sxrekord.chatting.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Rekord
 * @date 2022/9/12 17:09
 */
@Mapper
public interface RelationDao {
    /**
     * 根据用户ID查询所有好友
     * @param id
     * @return
     */
    List<Long> listUserIdByUserId(@Param("id") Long id);

    /**
     * 根据用户ID查询所有群组
     * @param id
     * @return
     */
    List<Long> listGroupIdByUserId(@Param("id") Long id);
}
