package com.sxrekord.chatting.service;

import com.sxrekord.chatting.model.po.Relation;
import com.sxrekord.chatting.model.vo.ResponseJson;

/**
 * @author Rekord
 * @date 2023/3/10 18:36
 */
public interface RelationService {
    /**
     * 创建一条关系
     * @param relation
     * @param userId
     * @return
     */
    ResponseJson createRelation(Relation relation, Long userId);

    /**
     * 更新一条关系
     * @param relation
     * @param userId
     * @return
     */
    ResponseJson updateRelation(Relation relation, Long userId);

    /**
     * 根据type和status列出所有关系
     * @param type
     * @param status
     * @param direction
     * @param userId
     * @return
     */
    ResponseJson listRelation(int type, int status, int direction, Long userId);

}
