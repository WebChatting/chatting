package com.sxrekord.chatting.common;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述: 全局常量
 *      1. USER_TOKEN 用户认证的键，用来匹配http session中的对应userId；
 *      2. webSocketServerHandshaker表，用channelId为键，存放握手实例。用来响应CloseWebSocketFrame的请求；
 *      3. onlineUser表，用userId为键，存放在线的客户端连接上下文；
 * @author Kanarien 
 * @version 1.0
 * @date 2018年5月18日 下午9:17:35
 */
public class Constant {

    public static final String USER_TOKEN = "CHATTING:USER:ID";
    
    public static Map<Channel, WebSocketServerHandshaker> webSocketHandshakerMap =
            new ConcurrentHashMap<>();
    
	public static Map<String, ChannelHandlerContext> onlineUserMap = 
	        new ConcurrentHashMap<>();

}
