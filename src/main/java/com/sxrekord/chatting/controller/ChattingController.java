package com.sxrekord.chatting.controller;

import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.UserService;
import com.sxrekord.chatting.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author Rekord
 * @date 2022/9/10 13:56
 */

@Controller
@RequestMapping("/chatroom")
public class ChattingController {

    @Autowired
    UserService userService;

    /**
     * 登录成功跳转页面后，调用此接口获取用户信息
     * @param session
     * @return
     */
    @PostMapping("/get_userinfo")
    @ResponseBody
    public ResponseJson getUserInfo(HttpSession session) {
        Object userId = session.getAttribute(Constant.USER_TOKEN);
        return userService.getUser((Long)userId);
    }

}
