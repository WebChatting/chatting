package com.sxrekord.chatting.dao;

import com.sxrekord.chatting.model.po.TextContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Rekord
 * @date 2022/9/13 17:30
 */
@Mapper
public interface TextContentDao {

    /**
     * 插入文本消息
     * @param textContent
     * @return
     */
    int insertTextContent(TextContent textContent);

    /**
     * 根据id查询文本消息
     * @param id
     * @return
     */
    TextContent getTextContentById(@Param("id") Long id);
}
