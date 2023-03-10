package com.sxrekord.chatting.service.impl;

import com.sxrekord.chatting.dao.GroupDao;
import com.sxrekord.chatting.dao.RelationDao;
import com.sxrekord.chatting.model.po.Relation;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.RelationService;
import com.sxrekord.chatting.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * @author Rekord
 * @date 2023/3/10 18:28
 */
@Service
public class RelationServiceImpl implements RelationService {
    @Autowired
    RelationDao relationDao;

    @Autowired
    GroupDao groupDao;

    @Override
    public ResponseJson createRelation(Relation relation, HttpSession session) {
        ResponseJson responseJson = new ResponseJson();

        Object requestId = session.getAttribute(Constant.USER_TOKEN);
        relation.setRequestId((long)requestId);
        // 根据群ID查询群主ID
        if (relation.getType() == 1) {
            relation.setAcceptId(this.groupDao.getGroupById(relation.getAcceptId()).getOwnerId());
        }
        relationDao.insertRelation(relation);
        return responseJson.success();
    }

    @Override
    public ResponseJson updateRelation(Relation relation, HttpSession session) {
        ResponseJson responseJson = new ResponseJson();

        Object requestId = session.getAttribute(Constant.USER_TOKEN);
        relation.setRequestId((long)requestId);
        // 根据群ID查询群主ID
        if (relation.getType() == 1) {
            relation.setAcceptId(this.groupDao.getGroupById(relation.getAcceptId()).getOwnerId());
        }
        relationDao.updateRelation(relation);
        return responseJson.success();
    }

}
