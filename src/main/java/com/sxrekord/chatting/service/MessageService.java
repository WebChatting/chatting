package com.sxrekord.chatting.service;

import com.sxrekord.chatting.model.vo.ResponseJson;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author Rekord
 * @date 2022/9/13 21:21
 */
public interface MessageService {
    /**
     * 加载历史消息
     * @param type
     * @param updateTime
     * @param toId
     * @param count
     * @param session
     * @return
     */
    ResponseJson loadMessage(Integer type, Date updateTime, Long toId, Integer count, HttpSession session);
}
