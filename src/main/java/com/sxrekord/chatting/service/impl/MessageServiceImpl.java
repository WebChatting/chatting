package com.sxrekord.chatting.service.impl;

import com.sxrekord.chatting.dao.FileContentDao;
import com.sxrekord.chatting.dao.MessageDao;
import com.sxrekord.chatting.dao.TextContentDao;
import com.sxrekord.chatting.dao.UserDao;
import com.sxrekord.chatting.model.po.FileContent;
import com.sxrekord.chatting.model.po.Message;
import com.sxrekord.chatting.model.po.TextContent;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.MessageService;
import com.sxrekord.chatting.util.ChatType;
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
    FileContentDao fileContentDao;
    @Autowired
    UserDao userDao;

    @Override
    public ResponseJson getFriendMessage(Long fromId, Long toId) {
        ResponseJson responseJson = new ResponseJson();
        List<Message> messages = messageDao.getMessageByFromIdAndToId(fromId, toId);

        for (Message message : messages) {
            // 文本消息
            if (message.getContentType() == 0L) {
                TextContent textContent = textContentDao.getTextContentById(message.getContentId());
                responseJson.setData("fromUserId", message.getFromId())
                    .setData("content", textContent.getContent())
                    .setData("type", ChatType.SINGLE_SENDING)
                    .setData("fromAvatarUrl", userDao.getUserById(message.getFromId()).getAvatarPath())
                    .toString();
            } else {
                // 文件消息
                FileContent fileContent = fileContentDao.getFileContentById(message.getContentId());
                responseJson.setData("fromUserId", message.getFromId())
                    .setData("originalFilename", fileContent.getName())
                    .setData("fileSize", fileContent.getSize())
                    .setData("fileUrl", fileContent.getPath())
                    .setData("type", ChatType.FILE_MSG_SINGLE_SENDING)
                    .setData("fromAvatarUrl", userDao.getUserById(message.getFromId()).getAvatarPath())
                    .toString();
            }
            responseJson.addToMessage();
        }
        return responseJson.success();
    }

    @Override
    public ResponseJson getGroupMessage(Long groupId) {
        ResponseJson responseJson = new ResponseJson();
        List<Message> messages = messageDao.getMessageByToId(groupId);

        for (Message message : messages) {
            // 文本消息
            if (message.getContentType() == 0L) {
                TextContent textContent = textContentDao.getTextContentById(message.getContentId());
                responseJson.setData("fromUserId", message.getFromId())
                        .setData("content", textContent.getContent())
                        .setData("toGroupId", groupId)
                        .setData("type", ChatType.SINGLE_SENDING)
                        .setData("fromAvatarUrl", userDao.getUserById(message.getFromId()).getAvatarPath())
                        .toString();
            } else {
                // 文件消息
                FileContent fileContent = fileContentDao.getFileContentById(message.getContentId());
                responseJson.setData("fromUserId", message.getFromId())
                        .setData("originalFilename", fileContent.getName())
                        .setData("fileSize", fileContent.getSize())
                        .setData("fileUrl", fileContent.getPath())
                        .setData("toGroupId", groupId)
                        .setData("type", ChatType.FILE_MSG_SINGLE_SENDING)
                        .setData("fromAvatarUrl", userDao.getUserById(message.getFromId()).getAvatarPath())
                        .toString();
            }
            responseJson.addToMessage();
        }
        return responseJson.success();
    }

    @Override
    public ResponseJson loadMessage(Integer type, Date updateTime, Long toId, Integer count, HttpSession session) {
        ResponseJson responseJson = new ResponseJson();

        responseJson.setData("messages",
                this.messageDao.listMessage(type, updateTime, (long)session.getAttribute(Constant.USER_TOKEN), toId, count));
        return responseJson.success();
    }
}
