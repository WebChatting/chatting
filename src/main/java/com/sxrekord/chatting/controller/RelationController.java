package com.sxrekord.chatting.controller;

import com.sxrekord.chatting.model.po.Relation;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.RelationService;
import com.sxrekord.chatting.util.HeaderUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rekord
 * @date 2023/3/10 18:15
 */
@Api(tags = "Relation-Related")
@Controller
@RequestMapping("/relation")
public class RelationController {
    @Autowired
    RelationService relationService;

    @ApiOperation(value = "Add Relation")
    @PostMapping(value = "add", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseJson add(@RequestBody Relation relation, HttpServletRequest request) {
        return relationService.createRelation(relation, HeaderUtils.getUserId(request));
    }

    @ApiOperation(value = "Update Relation")
    @PostMapping(value = "update", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseJson update(@RequestBody Relation relation, HttpServletRequest request) {
        return relationService.updateRelation(relation, HeaderUtils.getUserId(request));
    }

    @ApiOperation(value = "List Relation")
    @GetMapping(value = "list")
    @ResponseBody
    public ResponseJson list(@RequestParam int type, @RequestParam int status,
                             @RequestParam int direction, HttpServletRequest request) {
        return relationService.listRelation(type, status, direction, HeaderUtils.getUserId(request));
    }

}
