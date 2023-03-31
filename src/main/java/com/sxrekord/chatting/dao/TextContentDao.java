package com.sxrekord.chatting.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxrekord.chatting.model.po.TextContent;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Rekord
 * @date 2022/9/13 17:30
 */
@Mapper
public interface TextContentDao extends BaseMapper<TextContent> {

}
