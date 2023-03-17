package com.sxrekord.chatting.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sxrekord.chatting.common.MessageType;
import com.sxrekord.chatting.common.WSType;
import com.sxrekord.chatting.dao.*;
import com.sxrekord.chatting.model.po.*;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.ChatService;
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
    private ImageContentDao imageContentDao;
    @Autowired
    private FileContentDao fileContentDao;
    
    @Override
    public void register(JSONObject param, ChannelHandlerContext ctx) {
        String userId = param.get("userId").toString();
        log.info(userId);
        Constant.onlineUserMap.put(userId, ctx);
        String responseJson = new ResponseJson().success()
                .setData("type", WSType.ONLINE)
                .toString();
        sendMessage(ctx, responseJson);
        log.info(MessageFormat.format("userId为 {0} 的用户登记到在线用户表，当前在线人数为：{1}"
                , userId, Constant.onlineUserMap.size()));
    }

    @Override
    public void send(JSONObject jm, ChannelHandlerContext ctx, Integer type, Integer contentType) {
        System.out.println("enter send method, type: " + type);
        Long fromId = Long.parseLong(jm.get("fromId").toString());
        Long toId = Long.parseLong(jm.get("toId").toString());
        String content = jm.get("content").toString();

        // 消息持久化到数据库
        Message message = new Message(fromId, toId, type, contentType);
        if (contentType == MessageType.TEXT.getId()) {
            TextContent textContent = new TextContent(content);
            textContentDao.insertTextContent(textContent);
            message.setContentId(textContent.getId());
        } else if (contentType == MessageType.IMAGE.getId()) {
            ImageContent imageContent = new ImageContent(content);
            imageContentDao.insertImageContent(imageContent);
            message.setContentId(imageContent.getId());
        } else if (contentType == MessageType.FILE.getId()) {
            String size = jm.get("size").toString();
            String url = jm.get("url").toString();
            FileContent fileContent = new FileContent(content, size, url);
            fileContentDao.insertFileContent(fileContent);
            message.setContentId(fileContent.getId());
        }

        messageDao.insertMessage(message);

        if (type == 0) {
            // 拿到对方ID上下文
            ChannelHandlerContext toUserCtx = Constant.onlineUserMap.get(toId);
            // 对方在线
            if (toUserCtx != null) {
                // 发送消息
                sendMessage(toUserCtx, jm.toJSONString());
            }
        } else {
            Group group = groupDao.getGroupById(toId);
            if (group != null) {
                sendToGroup(group, fromId, jm.toJSONString());
            }
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
    public void typeError(ChannelHandlerContext ctx) {
        String responseJson = new ResponseJson()
                .error("该类型不存在！")
                .toString();
        sendMessage(ctx, responseJson);
    }


    private void sendToGroup(Group group, Long fromId, String responseJson) {
        List<Long> members = new ArrayList<>();
        members.addAll(relationDao.listUserIdByGroupId(group.getId()));
        members.add(groupDao.getGroupById(group.getId()).getOwnerId());
        for (Long userId : members) {
            ChannelHandlerContext toCtx = Constant.onlineUserMap.get(userId);
            if (toCtx != null && !fromId.equals(userId)) {
                sendMessage(toCtx, responseJson);
            }
        }
    }

    private void sendMessage(ChannelHandlerContext ctx, String message) {
        ctx.channel().writeAndFlush(new TextWebSocketFrame(message));
    }

}
