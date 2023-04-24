package com.sxrekord.chatting.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 描述: Netty WebSocket服务器
 *      使用独立的线程启动
 * @author Kanarien, Rekord
 * @version 1.0
 * @date 2018年5月18日 上午11:22:51
 */
@Slf4j
@Component
public class WebSocketServer implements Runnable {
	private final ScheduledExecutorService executorService;

	private final EventLoopGroup bossGroup = new NioEventLoopGroup();
	private final EventLoopGroup workerGroup = new NioEventLoopGroup();
	private final ServerBootstrap serverBootstrap = new ServerBootstrap();

	@Value("${netty.server.port}")
	private int port;
	@Autowired
	@Qualifier("webSocketChildChannelHandler")
	private ChannelHandler childChannelHandler;
	private ChannelFuture serverChannelFuture;

	public WebSocketServer() {
		executorService = Executors.newScheduledThreadPool(2);
	}

	@Override
	public void run() {
        build();
	}

	/**
	 * 描述：启动Netty Websocket服务器
	 */
	public void build() {
		try {
			long begin = System.currentTimeMillis();
			serverBootstrap.group(bossGroup, workerGroup) // 分别用于处理客户端的连接请求和与客户端的读写操作。
					.channel(NioServerSocketChannel.class) // 配置客户端的 channel 类型
					.option(ChannelOption.SO_BACKLOG, 1024) // 设置服务端接收连接的队列大小
					.childOption(ChannelOption.TCP_NODELAY, true) // 开启 TCP_NODELAY 算法，尽可能发送大块数据，减少小块数据的充斥
					.childOption(ChannelOption.SO_KEEPALIVE, true) // 开启TCP心跳机制
					.childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(592048)) // 使用固定长度接收缓存区分配器
					.childHandler(childChannelHandler); // 绑定I/O事件的处理类
			long end = System.currentTimeMillis();
			log.info("Netty Websocket服务器启动完成，耗时 " + (end - begin) + " ms，已绑定端口 " + port + " 阻塞式等候客户端连接");

			serverChannelFuture = serverBootstrap.bind(port).sync();

			// 定时扫描所有的Channel，关闭失效的Channel
			executorService.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					log.info("scanNotActiveChannel --------");
//					UserInfoManager.scanNotActiveChannel();
				}
			}, 3, 60, TimeUnit.SECONDS);

			// 定时向所有客户端发送Ping消息
			executorService.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
//					UserInfoManager.broadCastPing();
				}
			}, 3, 50, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error("Error occurred while starting Netty WebSocket Server", e);
			close();
		}
	}

	/**
	 * 关闭Netty Websocket服务器
	 */
	public void close() {
		if (executorService != null) {
			executorService.shutdown();
		}
		try {
			// 停止接受新连接
			bossGroup.shutdownGracefully().sync();
			// 等待现有连接关闭
			serverChannelFuture.channel().close().sync();
			// 停止读写操作
			workerGroup.shutdownGracefully().sync();
		} catch (InterruptedException ie) {
			log.error("Interrupted while closing Netty WebSocket Server", ie);
			Thread.currentThread().interrupt();
		}
	}

}
