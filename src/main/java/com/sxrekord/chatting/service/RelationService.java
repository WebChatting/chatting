package com.sxrekord.chatting.service;

import com.sxrekord.chatting.model.po.Relation;
import com.sxrekord.chatting.model.vo.ResponseJson;

import javax.servlet.http.HttpSession;

/**
 * @author Rekord
 * @date 2023/3/10 18:36
 */
public interface RelationService {
    /**
     * 创建一条关系
     * @param relation
     * @param httpSession
     * @return
     */
    ResponseJson createRelation(Relation relation, HttpSession httpSession);

    /**
     * 更新一条关系
     * @param relation
     * @return
     */
    ResponseJson updateRelation(Relation relation, HttpSession httpSession);

    /**
     * 根据type和status列出所有关系
     * @param type
     * @param status
     * @return
     */
    ResponseJson listRelation(int type, int status, HttpSession session);
}
