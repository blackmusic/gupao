package com.hei.netty.im.server.handler;

import com.hei.netty.im.processor.MessageProcessor;
import com.hei.netty.im.protocol.IMMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TerminalServerHandler extends SimpleChannelInboundHandler<IMMessage> {

    private MessageProcessor messageProcessor = new MessageProcessor();
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, IMMessage message) throws Exception {
        messageProcessor.sendMsg(channelHandlerContext.channel(),message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("Sokect client:与客户端断开连接："+cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }
}
