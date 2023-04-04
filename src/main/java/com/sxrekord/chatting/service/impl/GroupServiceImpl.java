package com.sxrekord.chatting.service.impl;

import com.sxrekord.chatting.dao.GroupDao;
import com.sxrekord.chatting.dao.RelationDao;
import com.sxrekord.chatting.model.po.Group;
import com.sxrekord.chatting.model.po.Relation;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.GroupService;
import com.sxrekord.chatting.util.WrapEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rekord
 * @date 2023/3/9 20:38
 */
@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    GroupDao groupDao;
    @Autowired
    RelationDao relationDao;

    @Value("${file.default.group}")
    private String group_avatar_default;

    @Override
    @Transactional(readOnly = true)
    public ResponseJson searchGroup(String name, Long requestId) {
        ResponseJson responseJson = new ResponseJson();

        List<Group> groups = groupDao.searchGroupByName(name);
        for (Group group : groups) {
            Relation relation = relationDao.searchRelation(new Relation(requestId, group.getId(), 1));
            WrapEntity.wrapSearchResultForGroup(responseJson, group, relation == null ?
                    (group.getOwnerId().equals(requestId) ? 1 : -1) : relation.getStatus());
        }
        responseJson.setCollectionToData("results").success();
        return responseJson;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseJson listGroup(Long ownerId) {
        ResponseJson responseJson = new ResponseJson();

        List<Group> groups = groupDao.listGroupByOwnerId((long)ownerId);
        for (Group group : groups) {
            WrapEntity.wrapGroup(responseJson, group, 1, 1);
        }
        return responseJson.setCollectionToData("relations").success();
    }

    @Override
    public ResponseJson createGroup(String name, String avatarPath, Long ownerId) {
        ResponseJson responseJson = new ResponseJson();

        groupDao.insert(new Group(name, (long)ownerId, avatarPath.trim().length() > 0 ? avatarPath : group_avatar_default));
        responseJson.success();
        return responseJson;
    }
}
