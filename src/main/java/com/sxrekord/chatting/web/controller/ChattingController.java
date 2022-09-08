package com.sxrekord.chatting.web.controller;


import javax.servlet.http.HttpSession;

import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.UserInfoService;
import com.sxrekord.chatting.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/chatroom")
public class ChattingController {

    @Autowired
    UserInfoService userInfoService;
    
    /**
     * 描述：登录成功后，调用此接口进行页面跳转
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String toChatroom() {
        return "chatroom";
    }
    
    /**
     * 描述：登录成功跳转页面后，调用此接口获取用户信息
     * @param session
     * @return
     */
    @RequestMapping(value = "/get_userinfo", method = RequestMethod.POST) 
    @ResponseBody
    public ResponseJson getUserInfo(HttpSession session) {
        Object userId = session.getAttribute(Constant.USER_TOKEN);
        return userInfoService.getByUserId((String)userId);
    }
}
