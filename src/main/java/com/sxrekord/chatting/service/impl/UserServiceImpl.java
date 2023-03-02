package com.sxrekord.chatting.service.impl;

import com.sxrekord.chatting.dao.GroupDao;
import com.sxrekord.chatting.dao.RelationDao;
import com.sxrekord.chatting.dao.UserDao;
import com.sxrekord.chatting.model.po.User;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.UserService;
import com.sxrekord.chatting.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rekord
 * @date 2022/9/12 12:37
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RelationDao relationDao;
    @Autowired
    private GroupDao groupDao;

    @Value("${file.user.avatar.default}")
    private String user_avatar_default;

    @Override
    public ResponseJson registerUser(String username, String password) {
        ResponseJson responseJson = new ResponseJson();
        if (userDao.getUserByUsername(username) == null) {
            userDao.insertUser(new User(username, password, user_avatar_default));
            responseJson.success("注册成功");
        } else {
            responseJson.error("用户名已存在");
        }
        return responseJson;
    }

    @Override
    public ResponseJson loginUser(String username, String password, HttpSession session) {
        ResponseJson responseJson = new ResponseJson();
        User user = userDao.getUserByUsernameAndPassword(username, password);
        if (user == null) {
            responseJson.error("用户名或密码错误！");
        } else {
            responseJson.success();
            if (session != null) {
                session.setAttribute(Constant.USER_TOKEN, user.getId());
            }
        }
        return responseJson;
    }

    @Override
    public ResponseJson logoutUser(HttpSession session) {
        ResponseJson responseJson = new ResponseJson();
        if (session == null) {
            return responseJson.error("请传入session！");
        }
        Object userId = session.getAttribute(Constant.USER_TOKEN);
        if (userId == null) {
            return responseJson.error("请先登录！");
        }
        session.removeAttribute(Constant.USER_TOKEN);
        log.info(MessageFormat.format("userId为 {0} 的用户已注销登录！", userId));
        return responseJson.success();
    }

    @Override
    public ResponseJson getUser(Long id) {
        ResponseJson responseJson = new ResponseJson();
        User user = userDao.getUserById(id);
        if (user == null) {
            responseJson.error("用户未登录！");
        } else {
            List<Long> friendList = relationDao.listUserIdByUserId(user.getId());
            user.setFriendList(new ArrayList<>(friendList.size()));
            for (Long userId : friendList) {
                user.getFriendList().add(userDao.getUserById(userId));
            }
            List<Long> groupList = relationDao.listGroupIdByUserId(user.getId());
            user.setGroupList(new ArrayList<>(groupList.size()));
            for (Long groupId : groupList) {
                user.getGroupList().add(groupDao.getGroupById(groupId));
            }

            log.info(user.toString());

            responseJson.success().setData("userInfo", user);
        }
        return responseJson;
    }

    @Override
    public ResponseJson updateUser(String username, String password, String avatarPath, HttpSession session) {
        ResponseJson responseJson = new ResponseJson();
        if (session == null) {
            return responseJson.error("请传入session！");
        }
        Object userId = session.getAttribute(Constant.USER_TOKEN);
        if (userId == null) {
            return responseJson.error("请先登录！");
        }
        userDao.updateUser(new User((long)userId, username, password, avatarPath));
        responseJson.success("信息更新成功");
        return responseJson;
    }

}
