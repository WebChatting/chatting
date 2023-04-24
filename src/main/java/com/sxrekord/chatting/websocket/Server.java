package com.sxrekord.chatting.websocket;

/**
 * @author Rekord
 * @date 2023/4/24 16:41
 */
public interface Server {
    /**
     * 启动服务器
     */
    void start();

    /**
     * 关闭服务器
     */
    void close();
}
