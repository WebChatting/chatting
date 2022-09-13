package com.sxrekord.chatting.dao;

import com.sxrekord.chatting.model.po.FileContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Rekord
 * @date 2022/9/13 17:30
 */
@Mapper
public interface FileContentDao {

    /**
     * 插入文件消息
     * @param fileContent
     * @return
     */
    int insertFileContent(FileContent fileContent);

    /**
     * 根据id查询文件消息
     * @param id
     * @return
     */
    FileContent getFileContentById(@Param("id") Long id);
}
