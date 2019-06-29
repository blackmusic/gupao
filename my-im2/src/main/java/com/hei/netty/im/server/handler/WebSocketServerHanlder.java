package com.hei.netty.im.server.handler;

import com.hei.netty.im.processor.MessageProcessor;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketServerHanlder extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private MessageProcessor messageProcessor = new MessageProcessor();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        messageProcessor.sendMsg(channelHandlerContext.channel(), textWebSocketFrame.text());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel client = ctx.channel();
        String addr = messageProcessor.getAddress(client);
        log.info("WebSocket client:" + addr + ",连接异常！");
        cause.printStackTrace();
        ctx.close();
    }
}
