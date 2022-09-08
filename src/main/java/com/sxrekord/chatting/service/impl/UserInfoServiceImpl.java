package com.sxrekord.chatting.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxrekord.chatting.dao.UserInfoDao;
import com.sxrekord.chatting.model.po.UserInfo;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;
    
    @Override
    public ResponseJson getByUserId(String userId) {
        UserInfo userInfo = userInfoDao.getByUserId(userId);
        return new ResponseJson().success()
                .setData("userInfo", userInfo);
    }

}
