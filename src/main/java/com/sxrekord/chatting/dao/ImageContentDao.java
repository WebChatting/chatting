package com.sxrekord.chatting.dao;

import com.sxrekord.chatting.model.po.ImageContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Rekord
 * @date 2023/3/16 19:07
 */
@Mapper
public interface ImageContentDao {
    /**
     * 根据id查询ImageContent
     * @param id
     * @return
     */
    ImageContent getImageContentById(@Param("id") Long id);

    /**
     * 插入一张图片
     * @param imageContent
     * @return
     */
    int insertImageContent(ImageContent imageContent);
}
