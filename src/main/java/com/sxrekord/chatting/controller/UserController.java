package com.sxrekord.chatting.controller;

import com.sxrekord.chatting.model.po.User;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.UserService;
import com.sxrekord.chatting.util.HeaderUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rekord
 * @date 2022/9/10 14:11
 */
@Api(tags = "User-Related")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "Login User")
    @RequestMapping(value = "login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseJson login(HttpServletResponse response, @RequestBody User user) {
        return userService.loginUser(user, response);
    }

    @ApiOperation(value = "Register User")
    @RequestMapping(value = "register", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseJson register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @ApiOperation(value = "Logout User")
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson logout(HttpServletRequest request) {
        return userService.logoutUser(HeaderUtils.getValidAuthorizationHeader(request));
    }

    @ApiOperation(value = "Update User")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson update(@RequestParam String name, @RequestParam String password,
                               @RequestParam String avatarPath, HttpServletRequest request) {
        return userService.updateUser(new User(name, password, avatarPath), HeaderUtils.getUserId(request));
    }

    @ApiOperation(value = "Search User")
    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJson search(@RequestParam String name, HttpServletRequest request) {
        return userService.searchUser(name, HeaderUtils.getUserId(request));
    }
}
