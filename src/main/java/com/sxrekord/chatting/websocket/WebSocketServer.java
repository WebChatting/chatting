package com.sxrekord.chatting.websocket;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
public class WebSocketServer extends BaseServer implements Runnable {
	private final ScheduledExecutorService executorService;
	@Resource(name = "webSocketServerHandler")
	private ChannelHandler webSocketServerHandler;
	@Resource(name = "httpRequestHandler")
	private ChannelHandler httpRequestHandler;

	public WebSocketServer() {
		executorService = Executors.newScheduledThreadPool(2);
	}

	@Override
	public void run() {
        start();
	}

	/**
	 * 描述：启动Netty Websocket服务器
	 */
	@Override
	public void start() {
		try {
			super.init();
			long begin = System.currentTimeMillis();
			serverBootstrap.group(bossGroup, workerGroup) // 分别用于处理客户端的连接请求和与客户端的读写操作。
					.channel(NioServerSocketChannel.class) // 配置客户端的 channel 类型
					.option(ChannelOption.SO_BACKLOG, 1024) // 设置服务端接收连接的队列大小
					.childOption(ChannelOption.TCP_NODELAY, true) // 开启 TCP_NODELAY 算法，尽可能发送大块数据，减少小块数据的充斥
					.childOption(ChannelOption.SO_KEEPALIVE, true) // 开启TCP心跳机制
					.childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(592048)) // 使用固定长度接收缓存区分配器
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(defLoopGroup);
							// HTTP请求编码解码器
							ch.pipeline().addLast("http-codec", new HttpServerCodec())
									// 把HTTP头、HTTP体拼成完整的HTTP请求
									.addLast("aggregator", new HttpObjectAggregator(65536))
									// 方便大文件传输，不过实质上都是短的文本数据
									.addLast("http-chunked", new ChunkedWriteHandler())
									// 检测链路是否读空闲(userEventTriggered)
									.addLast("idle-handler", new IdleStateHandler(60, 0, 0))
									.addLast("http-handler", httpRequestHandler)
									.addLast("websocket-handler",webSocketServerHandler);
						}
					}); // 绑定I/O事件的处理类
			long end = System.currentTimeMillis();
			log.info("Netty Websocket服务器启动完成，耗时 " + (end - begin) + " ms，已绑定端口 " + port + " 阻塞式等候客户端连接");

			serverChannelFuture = serverBootstrap.bind(port).sync();

			scheduleTasks();
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
		super.close();
	}

	private void scheduleTasks() {
		executorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				log.info("scanNotActiveChannel --------");
//				UserInfoManager.scanNotActiveChannel();
			}
		}, 3, 60, TimeUnit.SECONDS);

		executorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
//          		UserInfoManager.broadCastPing();
			}
		}, 3, 50, TimeUnit.SECONDS);
	}

}
