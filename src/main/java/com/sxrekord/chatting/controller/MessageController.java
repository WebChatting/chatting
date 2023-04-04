package com.sxrekord.chatting.controller;

import com.sxrekord.chatting.common.Constant;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
                             HttpSession session) {
        return messageService.loadMessage(type, updateTime, toId, count, (Long)session.getAttribute(Constant.USER_TOKEN));
    }
}
