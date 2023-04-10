package com.sxrekord.chatting.controller;

import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.UserService;
import com.sxrekord.chatting.util.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rekord
 * @date 2022/9/10 14:11
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson login(HttpServletResponse response,
                              @RequestParam String username,
                              @RequestParam String password) {
        return userService.loginUser(username, password, response);
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson register(@RequestParam String username,
                                 @RequestParam String password) {
        return userService.registerUser(username, password);
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson logout(HttpServletRequest request) {
        return userService.logoutUser(HeaderUtils.getValidAuthorizationHeader(request));
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson update(@RequestParam String name, @RequestParam String password,
                               @RequestParam String avatarPath, HttpServletRequest request) {
        return userService.updateUser(name, password, avatarPath, HeaderUtils.getUserId(request));
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJson search(@RequestParam String name, HttpServletRequest request) {
        return userService.searchUser(name, HeaderUtils.getUserId(request));
    }
}
