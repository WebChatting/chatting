package com.sxrekord.chatting.service;

import javax.servlet.http.HttpServletRequest;

import com.sxrekord.chatting.model.vo.ResponseJson;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    /**
     * 上传文件
     * @param file
     * @param request
     * @return
     */
    ResponseJson upload(MultipartFile file, HttpServletRequest request);
}
