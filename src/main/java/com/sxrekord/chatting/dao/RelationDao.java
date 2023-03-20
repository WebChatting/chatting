package com.sxrekord.chatting.dao;

import com.sxrekord.chatting.model.po.Relation;
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
     * 根据群组ID查询所有用户
     * @param id
     * @return
     */
    List<Long> listUserIdByGroupId(@Param("id") Long id);

    /**
     * 插入一条关系
     * @param relation
     * @return
     */
    int insertRelation(Relation relation);

    /**
     * 更新关系状态
     * @param relation
     * @return
     */
    int updateRelation(Relation relation);

    /**
     * 删除关系
     */
    int deleteRelation(Relation relation);

    /**
     * 列出关系
     * @param id
     * @param type
     * @param status
     * @param direction
     * @return
     */
    List<Relation> listRelation(@Param("id") Long id, @Param("type") Integer type,
                                @Param("status") Integer status, @Param("direction") Integer direction);

    /**
     * 查询关系
     * @param relation
     * @return
     */
    Relation searchRelation(Relation relation);
}
