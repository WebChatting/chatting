package com.sxrekord.chatting.controller;

import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.GroupService;
import com.sxrekord.chatting.util.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rekord
 * @date 2023/3/9 20:36
 */
@Controller
@RequestMapping("/group")
public class GroupController {
    @Autowired
    GroupService groupService;

    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJson search(@RequestParam String name, HttpServletRequest request) {
        return this.groupService.searchGroup(name, HeaderUtils.getUserId(request));
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJson list(HttpServletRequest request) {
        return this.groupService.listGroup(HeaderUtils.getUserId(request));
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson add(@RequestParam String name,
                            @RequestParam String avatarPath,
                            HttpServletRequest request) {
        return this.groupService.createGroup(name, avatarPath, HeaderUtils.getUserId(request));
    }
}
