package com.sxrekord.chatting.context;

import com.sxrekord.chatting.dao.GroupInfoDao;
import com.sxrekord.chatting.dao.UserInfoDao;
import com.sxrekord.chatting.websocket.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author Rekord
 * @date 2022/9/10 19:15
 */
@Service
@Scope("singleton")
public class AppContext implements InitializingBean, DisposableBean {

    private final Logger logger = LoggerFactory.getLogger(AppContext.class);
    @Autowired
    private WebSocketServer webSocketServer;
    private Thread nettyThread;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private GroupInfoDao groupInfoDao;
    /**
     * 服务器关闭前调用
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        logger.info("正在释放Netty Websocket相关连接...");
        webSocketServer.close();
        logger.info("正在关闭Netty Websocket服务器线程...");
        nettyThread.interrupt();
        logger.info("系统成功关闭！");
    }

    /**
     * 服务器启动后调用
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        nettyThread = new Thread(webSocketServer);
        logger.info("开启独立线程，启动Netty WebSocket服务器...");
        nettyThread.start();
        logger.info("加载用户数据...");
        userInfoDao.loadUserInfo();
        logger.info("加载用户交流群数据...");
        groupInfoDao.loadGroupInfo();
    }
}
