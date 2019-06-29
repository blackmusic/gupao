package com.hei.netty.im.server;

import com.hei.netty.im.protocol.IMDecoder;
import com.hei.netty.im.protocol.IMEncoder;
import com.hei.netty.im.server.handler.HttpServerHandler;
import com.hei.netty.im.server.handler.TerminalServerHandler;
import com.hei.netty.im.server.handler.WebSocketServerHanlder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IMServer {
    private int port = 8080;

    public void start(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 解析自定义协议
                            pipeline.addLast(new IMEncoder());
                            pipeline.addLast(new IMDecoder());
                            pipeline.addLast(new TerminalServerHandler());

                            // 解析HTTP请求
                            pipeline.addLast(new HttpServerCodec());
                            // 主要是将同一个http请求或相应的多个消息对象，变成一个完整的消息对象（FullHttpRequest）
                            pipeline.addLast(new HttpObjectAggregator(64 * 1024));
                            // 主要用于处理大数据流，比如1个G大小的文件，如果你直接传输肯定会撑爆JVM内存，加上这个handler就不用考虑这个问题了
                            pipeline.addLast(new ChunkedWriteHandler());
                            pipeline.addLast(new HttpServerHandler());
                            // 解析websocket请求
                            pipeline.addLast(new WebSocketServerProtocolHandler("/im"));
                            pipeline.addLast(new WebSocketServerHanlder());
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            log.info("服务已启动，监听端口：" + port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    private void start() {
        start(port);
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            new IMServer().start(Integer.valueOf(args[0]));
        } else {
            new IMServer().start();
        }
    }
}
