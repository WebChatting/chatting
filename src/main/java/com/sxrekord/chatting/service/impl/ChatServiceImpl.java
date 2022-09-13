package com.sxrekord.chatting.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sxrekord.chatting.dao.*;
import com.sxrekord.chatting.model.po.FileContent;
import com.sxrekord.chatting.model.po.Group;
import com.sxrekord.chatting.model.po.Message;
import com.sxrekord.chatting.model.po.TextContent;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.ChatService;
import com.sxrekord.chatting.util.ChatType;
import com.sxrekord.chatting.util.Constant;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * @author Rekord
 */
@Slf4j
@Service
public class ChatServiceImpl implements ChatService{

            
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private RelationDao relationDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private TextContentDao textContentDao;
    @Autowired
    private FileContentDao fileContentDao;
    
    @Override
    public void register(JSONObject param, ChannelHandlerContext ctx) {
        String userId = param.get("userId").toString();
        log.info(userId);
        Constant.onlineUserMap.put(userId, ctx);
        String responseJson = new ResponseJson().success()
                .setData("type", ChatType.REGISTER)
                .toString();
        sendMessage(ctx, responseJson);
        log.info(MessageFormat.format("userId为 {0} 的用户登记到在线用户表，当前在线人数为：{1}"
                , userId, Constant.onlineUserMap.size()));
    }

    @Override
    public void singleSend(JSONObject param, ChannelHandlerContext ctx) {
        String fromUserId = param.get("fromUserId").toString();
        String toUserId = param.get("toUserId").toString();
        String content = param.get("content").toString();
        ChannelHandlerContext toUserCtx = Constant.onlineUserMap.get(toUserId);

        storeTextMessage(new Message(Long.parseLong(fromUserId), Long.parseLong(toUserId), 0, 0), content);

        // 对方不在线不支持发送消息
        if (toUserCtx != null) {
            String responseJson = new ResponseJson().success()
                    .setData("fromUserId", fromUserId)
                    .setData("content", content)
                    .setData("type", ChatType.SINGLE_SENDING)
                    .toString();
            sendMessage(toUserCtx, responseJson);
        }
    }

    @Override
    public void groupSend(JSONObject param, ChannelHandlerContext ctx) {
        Long fromUserId = Long.parseLong(param.get("fromUserId").toString());
        Long toGroupId = Long.parseLong(param.get("toGroupId").toString());
        String content = param.get("content").toString();

        Group group = groupDao.getGroupById(toGroupId);
        storeTextMessage(new Message(fromUserId, toGroupId, 1, 0), content);
        if (group != null) {
            String responseJson = new ResponseJson().success()
                    .setData("fromUserId", fromUserId)
                    .setData("content", content)
                    .setData("toGroupId", toGroupId)
                    .setData("type", ChatType.GROUP_SENDING)
                    .toString();
            setMembers(group, fromUserId, responseJson);
        }
    }
    
    @Override
    public void remove(ChannelHandlerContext ctx) {
        Iterator<Entry<String, ChannelHandlerContext>> iterator = 
                Constant.onlineUserMap.entrySet().iterator();
        while(iterator.hasNext()) {
            Entry<String, ChannelHandlerContext> entry = iterator.next();
            if (entry.getValue() == ctx) {
                log.info("正在移除握手实例...");
                Constant.webSocketHandshakerMap.remove(ctx.channel().id().asLongText());
                log.info(MessageFormat.format("已移除握手实例，当前握手实例总数为：{0}"
                        , Constant.webSocketHandshakerMap.size()));
                iterator.remove();
                log.info(MessageFormat.format("userId为 {0} 的用户已退出聊天，当前在线人数为：{1}"
                        , entry.getKey(), Constant.onlineUserMap.size()));
                break;
            }
        }
    }

    @Override
    public void FileMsgSingleSend(JSONObject param, ChannelHandlerContext ctx) {
        String fromUserId = param.get("fromUserId").toString();
        String toUserId = param.get("toUserId").toString();
        String originalFilename = param.get("originalFilename").toString();
        String fileSize = param.get("fileSize").toString();
        String fileUrl = param.get("fileUrl").toString();
        ChannelHandlerContext toUserCtx = Constant.onlineUserMap.get(toUserId);

        storeFileMessage(new Message(Long.parseLong(fromUserId), Long.parseLong(toUserId), 0, 1),
                originalFilename, fileSize, fileUrl);
        if (toUserCtx != null) {
            String responseJson = new ResponseJson().success()
                    .setData("fromUserId", fromUserId)
                    .setData("originalFilename", originalFilename)
                    .setData("fileSize", fileSize)
                    .setData("fileUrl", fileUrl)
                    .setData("type", ChatType.FILE_MSG_SINGLE_SENDING)
                    .toString();
            sendMessage(toUserCtx, responseJson);
        }
    }

    @Override
    public void FileMsgGroupSend(JSONObject param, ChannelHandlerContext ctx) {
        Long fromUserId = Long.parseLong(param.get("fromUserId").toString());
        Long toGroupId = Long.parseLong(param.get("toGroupId").toString());
        String originalFilename = param.get("originalFilename").toString();
        String fileSize = param.get("fileSize").toString();
        String fileUrl = param.get("fileUrl").toString();
        Group group = groupDao.getGroupById((long)toGroupId);
        storeFileMessage(new Message(fromUserId, toGroupId, 1, 1),
                originalFilename, fileSize, fileUrl);
        if (group != null) {
            String responseJson = new ResponseJson().success()
                    .setData("fromUserId", fromUserId)
                    .setData("toGroupId", toGroupId)
                    .setData("originalFilename", originalFilename)
                    .setData("fileSize", fileSize)
                    .setData("fileUrl", fileUrl)
                    .setData("type", ChatType.FILE_MSG_GROUP_SENDING)
                    .toString();
            setMembers(group, fromUserId, responseJson);

        }
    }
    
    @Override
    public void typeError(ChannelHandlerContext ctx) {
        String responseJson = new ResponseJson()
                .error("该类型不存在！")
                .toString();
        sendMessage(ctx, responseJson);
    }
    

    
    private void sendMessage(ChannelHandlerContext ctx, String message) {
        ctx.channel().writeAndFlush(new TextWebSocketFrame(message));
    }

    private void setMembers(Group group, Long fromUserId, String responseJson) {
        List<Long> members = relationDao.listUserIdByGroupId(group.getGroupId());
        group.setMembers(new ArrayList<>(members.size()));
        for (Long userId : members) {
            group.getMembers().add(userDao.getUserById(userId));
        }

        group.getMembers().stream()
                .forEach(member -> {
                    ChannelHandlerContext toCtx = Constant.onlineUserMap.get(member.getUserId());
                    if (toCtx != null && !fromUserId.equals(member.getUserId())) {
                        sendMessage(toCtx, responseJson);
                    }
                });
    }

    /**
     * 将文字聊天记录存储到数据库中
     * @param message
     * @param content
     */
    private void storeTextMessage(Message message, String content) {
        TextContent textContent = new TextContent(content);
        textContentDao.insertTextContent(textContent);
        message.setContentId(textContent.getId());
        messageDao.insertMessage(message);
    }

    /**
     * 将文件聊天记录存储到数据库中
     * @param message
     * @param name
     * @param size
     * @param path
     */
    private void storeFileMessage(Message message, String name, String size, String path) {
        FileContent fileContent = new FileContent(name, size, path);
        fileContentDao.insertFileContent(fileContent);
        message.setContentId(fileContent.getId());
        messageDao.insertMessage(message);
    }

}
