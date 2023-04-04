package com.sxrekord.chatting.controller;

import com.sxrekord.chatting.common.Constant;
import com.sxrekord.chatting.model.po.Relation;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author Rekord
 * @date 2023/3/10 18:15
 */
@Controller
@RequestMapping("/relation")
public class RelationController {
    @Autowired
    RelationService relationService;

    @PostMapping(value = "add", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseJson add(@RequestBody Relation relation, HttpSession session) {
        return relationService.createRelation(relation, (Long)session.getAttribute(Constant.USER_TOKEN));
    }

    @PostMapping(value = "update", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseJson update(@RequestBody Relation relation, HttpSession session) {
        return relationService.updateRelation(relation, (Long)session.getAttribute(Constant.USER_TOKEN));
    }

    @GetMapping(value = "list")
    @ResponseBody
    public ResponseJson list(@RequestParam int type, @RequestParam int status,
                             @RequestParam int direction, HttpSession session) {
        return relationService.listRelation(type, status, direction, (Long)session.getAttribute(Constant.USER_TOKEN));
    }

}
