package com.sxrekord.chatting.websocket;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.sxrekord.chatting.common.Constant;
import com.sxrekord.chatting.common.WSErrorCode;
import com.sxrekord.chatting.common.WSType;
import com.sxrekord.chatting.model.vo.ResponseJson;
import com.sxrekord.chatting.service.ChatService;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;


/**
 * @author Rekord
 * @date 2022/9/10 19:15
 */
@Slf4j
@Component
@Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Autowired
    private ChatService chatService;

    /**
     * 描述：读取完连接的消息后，对消息进行处理。
     *      这里主要是处理WebSocket请求
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        handlerWebSocketFrame(ctx, msg);
    }
    
    /**
     * 描述：处理WebSocketFrame
     * @param ctx
     * @param frame
     * @throws Exception
     */
    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // 关闭请求
        if (frame instanceof CloseWebSocketFrame) {
            WebSocketServerHandshaker handshaker = 
                    Constant.webSocketHandshakerMap.get(ctx.channel().id().asLongText());
            if (handshaker == null) {
                // 发送提示信息后关闭握手实例
                CloseWebSocketFrame closeFrame = new CloseWebSocketFrame(WebSocketCloseStatus.NORMAL_CLOSURE.code(), "不存在的客户端连接！");
                ctx.writeAndFlush(closeFrame).addListener(ChannelFutureListener.CLOSE);
            } else {
                handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            }
            return;
        }
        // ping | pong 请求
        if (frame instanceof PingWebSocketFrame | frame instanceof PongWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 目前只支持文本格式，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            sendErrorMessage(ctx, WSErrorCode.INVALID_DATA_TYPE.getCode(), WSErrorCode.INVALID_DATA_TYPE.getMessage());
            return;
        }

        // 客户端发送过来的消息
        String request = ((TextWebSocketFrame)frame).text();
        log.info("服务端收到新信息:\n" + request);
        JSONObject param = null;
        try {
            param = JSONObject.parseObject(request);
        } catch (JSONException jsonException) {
            sendErrorMessage(ctx, WSErrorCode.INVALID_JSON_FORMAT.getCode(), WSErrorCode.INVALID_JSON_FORMAT.getMessage());
            return;
        }
        if (param == null) {
            sendErrorMessage(ctx, WSErrorCode.MESSAGE_CONTENT_EMPTY.getCode(), WSErrorCode.MESSAGE_CONTENT_EMPTY.getMessage());
            return;
        }

        WSType type = null;
        try {
            type = WSType.getWSTypeById((Integer)param.get("ws_type"));
        } catch (NullPointerException npe) {
            sendErrorMessage(ctx, WSErrorCode.MISSING_REQUIRED_FIELD.getCode(), WSErrorCode.MISSING_REQUIRED_FIELD.getMessage());
            return;
        }
        switch (type) {
            case ONLINE:
                chatService.online(param, ctx);
                break;
            case OFFLINE:
                chatService.offline(ctx);
                break;
            case TEXT_SINGLE_SENDING:
                chatService.send(param, ctx, 0, 0);
                break;
            case TEXT_GROUP_SENDING:
                chatService.send(param, ctx, 1, 0);
                break;
            case IMAGE_SINGLE_SENDING:
                chatService.send(param, ctx, 0, 1);
                break;
            case IMAGE_GROUP_SENDING:
                chatService.send(param, ctx, 1, 1);
                break;
            case FILE_SINGLE_SENDING:
                chatService.send(param, ctx, 0, 2);
                break;
            case FILE_GROUP_SENDING:
                chatService.send(param, ctx, 1, 2);
                break;
            default:
                chatService.typeError(ctx);
                break;
        }
    }
    
    /**
     * 描述：客户端断开连接
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("Client " + ctx.channel().remoteAddress() + " disconnected");
        Constant.webSocketHandshakerMap.remove(ctx.channel().id().asLongText());
        log.info(MessageFormat.format("已移除握手实例，当前握手实例总数为：{0}"
                , Constant.webSocketHandshakerMap.size()));
        chatService.offline(ctx);
        ctx.channel().close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 客户端与服务端建立连接后执行的操作
        log.info("Client " + ctx.channel().remoteAddress() + " connected");
        // 可以在此处记录连接信息、发送欢迎消息等
    }
   
    /**
     * 捕获异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("caught exception: " + cause);
        cause.printStackTrace();
    }
    
    private void sendErrorMessage(ChannelHandlerContext ctx, Integer errorStatus, String errorMsg) {
        String responseJson = new ResponseJson(errorStatus)
                .setMsg(errorMsg)
                .toString();
        ctx.channel().writeAndFlush(new TextWebSocketFrame(responseJson));
    }

}
