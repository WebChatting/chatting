package com.sxrekord.chatting.service;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;


/**
 * @author Rekord
 * @date 2022/9/10 14:08
 */
public interface ChatService {

    /**
     * 处理用户上线
     * @param param
     * @param ctx
     */
    public void online(JSONObject param, ChannelHandlerContext ctx);

    /**
     * 处理用户下线
     * @param ctx
     */
    public void offline(ChannelHandlerContext ctx);

    /**
     *
     * @param jm
     * @param ctx
     * @param type
     * @param contentType
     */
    public void send(JSONObject jm, ChannelHandlerContext ctx, Integer type, Integer contentType);

    /**
     * 封装并发送“类型不存在”错误
     * @param ctx
     */
    public void typeError(ChannelHandlerContext ctx);
        
}
