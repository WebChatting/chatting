package com.sxrekord.chatting.service.impl;

import com.sxrekord.chatting.dao.*;
import com.sxrekord.chatting.model.po.Message;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Rekord
 * @date 2022/9/13 23:39
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageDao messageDao;
    @Autowired
    TextContentDao textContentDao;
    @Autowired
    ImageContentDao imageContentDao;
    @Autowired
    FileContentDao fileContentDao;
    @Autowired
    UserDao userDao;

    private void loadConcreteMessage(ResponseJson responseJson, List<Message> messages) {
        for (Message message : messages) {
            responseJson.setData("id", message.getId())
                    .setData("fromId", message.getFromId())
                    .setData("name", userDao.selectById(message.getFromId()).getUsername())
                    .setData("avatarPath", userDao.selectById(message.getFromId()).getAvatarPath())
                    .setData("updateTime", message.getUpdateTime())
                    .setData("contentType", message.getContentType())
                    .setData("content", message.getContentType() == 0 ?
                            textContentDao.selectById(message.getContentId()).getContent()
                            : (message.getContentType() == 1 ? imageContentDao.selectById(message.getContentId()).getPath()
                            : fileContentDao.selectById(message.getContentId()).getName()))
                    .setData("url", message.getType() == 2 ?
                            fileContentDao.selectById(message.getContentId()).getPath() : null)
                    .toString();
            responseJson.addToCollection("messages");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseJson loadMessage(Integer type, Date updateTime, Long toId, Integer count, Long userId) {
        ResponseJson responseJson = new ResponseJson();

        List<Message> messages = this.messageDao.listMessage(type, updateTime,
                                                            userId, toId, count);

        loadConcreteMessage(responseJson, messages);
        return responseJson.setCollectionToData("messages").success();
    }
}
