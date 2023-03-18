package com.sxrekord.chatting.controller;

import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
    public ResponseJson login(HttpSession session,
                              @RequestParam String username,
                              @RequestParam String password) {
        return userService.loginUser(username, password, session);
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson register(@RequestParam String username,
                                 @RequestParam String password) {
        return userService.registerUser(username, password);
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson logout(HttpSession session) {
        return userService.logoutUser(session);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson update(@RequestParam String name, @RequestParam String password, @RequestParam String avatarPath, HttpSession session) {
        return userService.updateUser(name, password, avatarPath, session);
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJson search(@RequestParam String name) {
        return userService.searchUser(name);
    }
}
