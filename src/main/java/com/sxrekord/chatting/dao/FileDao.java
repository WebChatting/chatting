package com.sxrekord.chatting.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxrekord.chatting.model.po.File;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Rekord
 * @date 2023/4/11 10:19
 */
@Mapper
public interface FileDao extends BaseMapper<File> {
    /**
     * 根据ID更新文件过期策略
     * @param file
     * @return
     */
    int updateExpirePolicy(File file);
}
