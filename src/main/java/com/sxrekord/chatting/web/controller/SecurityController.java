package com.sxrekord.chatting.web.controller;

import javax.servlet.http.HttpSession;

import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityController {

    @Autowired
    SecurityService securityService;
    
    @RequestMapping(value = {"login", "/"}, method = RequestMethod.GET)
    public String toLogin() {
        return "login";
    }
    
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson login(HttpSession session,
                              @RequestParam String username,
                              @RequestParam String password) {
        return securityService.login(username, password, session);
    }
    
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJson logout(HttpSession session) {
        return securityService.logout(session);
    }
}
