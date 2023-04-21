package com.sxrekord.chatting.controller;

import com.sxrekord.chatting.model.vo.ResponseJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Base64;

import static com.sxrekord.chatting.util.SecurityUtils.publicKey;
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
        // 将公钥转换为Base64编码字符串
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        return new ResponseJson().setData("public-key", publicKeyString).success();
    }
}
