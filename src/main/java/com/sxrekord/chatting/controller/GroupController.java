package com.sxrekord.chatting.controller;

import com.sxrekord.chatting.common.Constant;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

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
    public ResponseJson search(@RequestParam String name, HttpSession session) {
        return this.groupService.searchGroup(name, (Long)session.getAttribute(Constant.USER_TOKEN));
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJson list(HttpSession session) {
        return this.groupService.listGroup((Long)session.getAttribute(Constant.USER_TOKEN));
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson add(@RequestParam String name,
                            @RequestParam String avatarPath,
                            HttpSession session) {
        return this.groupService.createGroup(name, avatarPath, (Long)session.getAttribute(Constant.USER_TOKEN));
    }
}
