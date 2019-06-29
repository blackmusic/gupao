package com.hei.netty.im.cilent;

import com.hei.netty.im.cilent.handler.IMClientHandler;
import com.hei.netty.im.protocol.IMDecoder;
import com.hei.netty.im.protocol.IMEncoder;
import com.hei.netty.im.server.IMServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class IMClient {

    private IMClientHandler imClientHandler;
    private String host;
    private int port;

    public IMClient(String nickName){
        this.imClientHandler = new IMClientHandler(nickName);
    }

    public void connect(String host,int port){
        this.host = host;
        this.port = port;
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new IMEncoder());
                            socketChannel.pipeline().addLast(new IMDecoder());
                            socketChannel.pipeline().addLast(imClientHandler);
                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            loopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new IMClient("Tom").connect("127.0.0.1",8080);

    }
}
