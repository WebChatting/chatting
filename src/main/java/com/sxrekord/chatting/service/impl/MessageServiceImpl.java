package com.sxrekord.chatting.service.impl;

import com.sxrekord.chatting.dao.*;
import com.sxrekord.chatting.model.po.Message;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.MessageService;
import com.sxrekord.chatting.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
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

    @Override
    public ResponseJson getFriendMessage(Long fromId, Long toId) {
        ResponseJson responseJson = new ResponseJson();
        List<Message> messages = messageDao.getMessageByFromIdAndToId(fromId, toId);

        loadConcreteMessage(responseJson, messages);
        return responseJson.success();
    }

    @Override
    public ResponseJson getGroupMessage(Long groupId) {
        ResponseJson responseJson = new ResponseJson();
        List<Message> messages = messageDao.getMessageByToId(groupId);

        loadConcreteMessage(responseJson, messages);
        return responseJson.success();
    }

    private void loadConcreteMessage(ResponseJson responseJson, List<Message> messages) {
        for (Message message : messages) {
            responseJson.setData("id", message.getId())
                    .setData("fromId", message.getFromId())
                    .setData("name", userDao.getUserById(message.getFromId()).getUsername())
                    .setData("avatarPath", userDao.getUserById(message.getFromId()).getAvatarPath())
                    .setData("contentType", message.getContentType())
                    .setData("content", message.getContentType() == 0 ?
                            textContentDao.getTextContentById(message.getContentId()).getContent()
                            : (message.getContentType() == 1 ? imageContentDao.getImageContentById(message.getContentId()).getPath()
                            : fileContentDao.getFileContentById(message.getContentId()).getName()))
                    .setData("url", message.getType() == 2 ?
                            fileContentDao.getFileContentById(message.getContentId()).getPath() : null)
                    .toString();
            responseJson.addToCollection("messages");
        }
    }

    @Override
    public ResponseJson loadMessage(Integer type, Date updateTime, Long toId, Integer count, HttpSession session) {
        ResponseJson responseJson = new ResponseJson();

        List<Message> messages = this.messageDao.listMessage(type, updateTime,
                                                            (long)session.getAttribute(Constant.USER_TOKEN),
                                                            toId, count);

        loadConcreteMessage(responseJson, messages);
        return responseJson.setCollectionToData("messages").success();
    }
}
