package com.sxrekord.chatting.service.impl;

import com.sxrekord.chatting.dao.RelationDao;
import com.sxrekord.chatting.dao.UserDao;
import com.sxrekord.chatting.model.po.Relation;
import com.sxrekord.chatting.model.po.User;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.UserService;
import com.sxrekord.chatting.util.HeaderUtils;
import com.sxrekord.chatting.util.JwtTokenUtils;
import com.sxrekord.chatting.util.WrapEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
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

    @Value("${file.default.user}")
    private String user_avatar_default;

    @Override
    public ResponseJson registerUser(String username, String password) {
        ResponseJson responseJson = new ResponseJson();
        if (userDao.getUserByUsername(username) == null) {
            userDao.insert(new User(username, password, user_avatar_default));
            responseJson.success("注册成功");
        } else {
            responseJson.error("用户名已存在");
        }
        return responseJson;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseJson loginUser(String username, String password, HttpServletResponse response) {
        ResponseJson responseJson = new ResponseJson();
        User user = userDao.getUserByUsernameAndPassword(username, password);
        if (user == null) {
            responseJson.error("用户名或密码错误！");
        } else {
            responseJson.setData("id", user.getId())
                    .setData("username", user.getUsername())
                    .setData("password", user.getPassword())
                    .setData("avatarPath", user.getAvatarPath()).success();
            HeaderUtils.setAuthorizationHeader(response, JwtTokenUtils.generateAccessToken(user));
        }
        return responseJson;
    }

    @Override
    public ResponseJson logoutUser(String token) {
        ResponseJson responseJson = new ResponseJson();
        log.info(MessageFormat.format("userId为 {0} 的用户已注销登录！", JwtTokenUtils.parseUserId(token)));
        // 将该token加入黑名单

        return responseJson.success();
    }

    @Override
    public ResponseJson updateUser(String username, String password, String avatarPath, Long userId) {
        ResponseJson responseJson = new ResponseJson();
        userDao.updateById(new User(userId, username, password, avatarPath));
        responseJson.success("信息更新成功");
        return responseJson;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseJson searchUser(String username, Long requestId) {
        ResponseJson responseJson = new ResponseJson();
        // 调用dao层
        List<User> users = userDao.searchUserByUsername("%" + username + "%");
        for (User user : users) {
            if (user.getId().equals(requestId)) {
                continue;
            }
            Relation relation = relationDao.searchRelation(new Relation(requestId, user.getId(), 0));
            WrapEntity.wrapSearchResultForUser(responseJson, user, relation == null ? -1 : relation.getStatus());
        }
        return responseJson.setCollectionToData("results").success();
    }

}
