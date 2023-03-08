package com.sxrekord.chatting.service;

import com.sxrekord.chatting.model.vo.ResponseJson;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rekord
 */
public interface FileService {

    /**
     * 上传文件
     * @param file
     * @param request
     * @return
     */
    ResponseJson upload(MultipartFile file);
}
