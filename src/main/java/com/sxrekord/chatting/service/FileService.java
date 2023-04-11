package com.sxrekord.chatting.service;

import com.sxrekord.chatting.model.vo.ResponseJson;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Rekord
 */
public interface FileService {

    /**
     * 上传文件
     * @param file
     * @return
     */
    ResponseJson upload(MultipartFile file);

    /**
     * 整理文件
     */
    void tidyUp();

    /**
     * 清理过期文件
     */
    void clean();
}
