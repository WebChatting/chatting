package com.sxrekord.chatting.service.impl;

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

/**
 * @author Rekord
 * @date 2022/9/12 12:37
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Value("${file.user.avatar.location}")
    private String user_avatar_location;
    @Value("${file.user.avatar.default}")
    private String user_avatar_default;

    @Override
    public ResponseJson registerUser(String username, String password) {
        ResponseJson responseJson = new ResponseJson();
        if (userDao.getUserByUsername(username) == null) {
            userDao.insertUser(new User(username, password, user_avatar_location + user_avatar_default));
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
                session.setAttribute(Constant.USER_TOKEN, user.getUserId());
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
}
