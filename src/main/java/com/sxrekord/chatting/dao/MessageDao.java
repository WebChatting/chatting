package com.sxrekord.chatting.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxrekord.chatting.model.po.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author Rekord
 * @date 2022/9/13 17:02
 */
@Mapper
public interface MessageDao extends BaseMapper<Message> {

    /**
     * 列出消息
     * 如果类型是1，则fromId无效
     * @param type
     * @param updateTime
     * @param fromId
     * @param toId
     * @param count
     * @return
     */
    List<Message> listMessage(@Param("type") Integer type, @Param("updateTime") Date updateTime,
                              @Param("fromId") Long fromId, @Param("toId") Long toId, @Param("count") Integer count);
}
