package com.sxrekord.chatting.controller;

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
        return relationService.createRelation(relation, session);
    }

    @PostMapping(value = "update", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseJson update(@RequestBody Relation relation, HttpSession session) {
        return relationService.updateRelation(relation, session);
    }
}
