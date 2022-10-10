package com.sxrekord.chatting.dao;

import com.sxrekord.chatting.model.po.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Rekord
 * @date 2022/9/13 17:02
 */
@Mapper
public interface MessageDao {

    /**
     * 通过fromID和toId查询消息
     * @param fromId
     * @param toId
     * @return
     */
    List<Message> getMessageByFromIdAndToId(@Param("fromId") Long fromId, @Param("toId") Long toId);

    /**
     * 通过toID查询消息
     * @param toId
     * @return
     */
    List<Message> getMessageByToId(@Param("toId") Long toId);
    /**
     * 保存消息
     * @param message
     * @return
     */
    int insertMessage(Message message);
}
