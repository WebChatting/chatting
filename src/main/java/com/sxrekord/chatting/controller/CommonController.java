package com.sxrekord.chatting.controller;

import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Rekord
 * @date 2023/4/21 12:24
 */
@Api(tags = "Common Module")
@Controller
@RequestMapping("/common")
public class CommonController {

    @ApiOperation(value = "Get Public Key")
    @RequestMapping(value = "public-key", method = GET)
    @ResponseBody
    public ResponseJson getPublicKey() {
        return new ResponseJson().setData("public-key", SecurityUtils.getPublicKeyBase64()).success();
    }
}
