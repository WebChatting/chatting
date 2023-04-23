package com.sxrekord.chatting.controller;

import com.sxrekord.chatting.annotation.NoAuthorization;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Rekord
 */
@Api(tags = "File-Related")
@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "Upload File")
    @RequestMapping(value = "/upload", method = POST)
    @ResponseBody
    @NoAuthorization
    public ResponseJson upload(
            @RequestParam(value = "file", required = true) MultipartFile file) {
        return fileService.upload(file);
    }
}
