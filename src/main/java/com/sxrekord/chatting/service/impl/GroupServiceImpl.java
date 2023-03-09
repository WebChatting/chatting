package com.sxrekord.chatting.service.impl;

import com.sxrekord.chatting.dao.GroupDao;
import com.sxrekord.chatting.model.po.Group;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.GroupService;
import com.sxrekord.chatting.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Rekord
 * @date 2023/3/9 20:38
 */
@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    GroupDao groupDao;

    @Override
    public ResponseJson searchGroup(String name) {
        ResponseJson responseJson = new ResponseJson();

        List<Group> groups = groupDao.searchGroupByName("%" + name + "%");
        responseJson.setData("groups", groups).success();
        return responseJson;
    }

    @Override
    public ResponseJson listGroup(HttpSession session) {
        ResponseJson responseJson = new ResponseJson();

        if (session == null) {
            return responseJson.error("请传入session！");
        }
        Object ownerId = session.getAttribute(Constant.USER_TOKEN);
        if (ownerId == null) {
            return responseJson.error("请先登录！");
        }
        session.getAttribute(Constant.USER_TOKEN);
        List<Group> groups = groupDao.listGroupByOwnerId((long)ownerId);
        responseJson.setData("groups", groups).success();
        return responseJson;
    }

    @Override
    public ResponseJson createGroup(String name, String avatarPath, HttpSession session) {
        ResponseJson responseJson = new ResponseJson();
        if (session == null) {
            return responseJson.error("请传入session！");
        }
        Object ownerId = session.getAttribute(Constant.USER_TOKEN);
        if (ownerId == null) {
            return responseJson.error("请先登录！");
        }
        groupDao.insertGroup(new Group(name, (long)ownerId, avatarPath));
        responseJson.success();
        return responseJson;
    }
}
