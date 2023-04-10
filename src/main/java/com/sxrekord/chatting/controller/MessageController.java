package com.sxrekord.chatting.controller;

import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.MessageService;
import com.sxrekord.chatting.util.HeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * @author Rekord
 * @date 2022/9/13 23:41
 */
@Controller
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageService messageService;

    @PostMapping(value = "load")
    @ResponseBody
    public ResponseJson load(@RequestParam("type") Integer type,
                             @RequestParam("updateTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date updateTime,
                             @RequestParam("toId") Long toId, @RequestParam("count") Integer count,
                             HttpServletRequest request) {
        return messageService.loadMessage(type, updateTime, toId, count, HeaderUtils.getUserId(request));
    }
}
