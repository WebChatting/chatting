package com.sxrekord.chatting.service.impl;

import com.sxrekord.chatting.dao.GroupDao;
import com.sxrekord.chatting.dao.RelationDao;
import com.sxrekord.chatting.dao.UserDao;
import com.sxrekord.chatting.model.po.Relation;
import com.sxrekord.chatting.util.WrapEntity;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.RelationService;
import com.sxrekord.chatting.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    UserDao userDao;

    @Override
    public ResponseJson createRelation(Relation relation, HttpSession session) {
        ResponseJson responseJson = new ResponseJson();

        Object requestId = session.getAttribute(Constant.USER_TOKEN);
        relation.setRequestId((long)requestId);
        relationDao.insertRelation(relation);
        return responseJson.success();
    }

    @Override
    public ResponseJson updateRelation(Relation relation, HttpSession session) {
        ResponseJson responseJson = new ResponseJson();

        long requestId = (long)session.getAttribute(Constant.USER_TOKEN);
        relation.setRequestId(requestId);
        if (relation.getStatus() == 3) {
            relationDao.deleteRelation(relation);
        } else {
            relationDao.updateRelation(relation);
        }

        return responseJson.success();
    }

    @Override
    public ResponseJson listRelation(int type, int status, int direction, HttpSession session) {
        ResponseJson responseJson = new ResponseJson();
        long id = (long)session.getAttribute(Constant.USER_TOKEN);
        List data = new ArrayList();

        if (direction == -1) {
            for (Relation relation : relationDao.listRelation(id, type, status, 0)) {
                WrapEntity.wrapUser(responseJson, userDao.getUserById(relation.getAcceptId()), type, status);
            }

            for (Relation relation : relationDao.listRelation(id, type, status, 1)) {
                WrapEntity.wrapUser(responseJson, userDao.getUserById(relation.getRequestId()), type, status);
            }

        } else {
            for (Relation relation : relationDao.listRelation(id, type, status, direction)) {
                if (status == 1) {
                    // 加入的群组
                    WrapEntity.wrapGroup(responseJson, groupDao.getGroupById(relation.getAcceptId()), type, status);
                } else {
                    // 验证
                    if (type == 0) {
                        if (direction == 0) {
                            WrapEntity.wrapUser(responseJson, userDao.getUserById(relation.getAcceptId()), type, status);
                        } else {
                            WrapEntity.wrapUser(responseJson, userDao.getUserById(relation.getRequestId()), type, status);
                        }
                    } else {
                        if (direction == 0) {
                            WrapEntity.wrapGroup(responseJson, groupDao.getGroupById(relation.getAcceptId()), type, status);
                        } else {
                            WrapEntity.wrapUser(responseJson, userDao.getUserById(relation.getRequestId()), type, status);
                        }
                    }
                }
            }
        }

        return responseJson.setCollectionToData("relations").success();
    }

}
