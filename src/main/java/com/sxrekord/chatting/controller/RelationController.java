package com.sxrekord.chatting.controller;

import com.sxrekord.chatting.model.po.Relation;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.RelationService;
import com.sxrekord.chatting.util.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public ResponseJson add(@RequestBody Relation relation, HttpServletRequest request) {
        return relationService.createRelation(relation, HeaderUtils.getUserId(request));
    }

    @PostMapping(value = "update", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseJson update(@RequestBody Relation relation, HttpServletRequest request) {
        return relationService.updateRelation(relation, HeaderUtils.getUserId(request));
    }

    @GetMapping(value = "list")
    @ResponseBody
    public ResponseJson list(@RequestParam int type, @RequestParam int status,
                             @RequestParam int direction, HttpServletRequest request) {
        return relationService.listRelation(type, status, direction, HeaderUtils.getUserId(request));
    }

}
