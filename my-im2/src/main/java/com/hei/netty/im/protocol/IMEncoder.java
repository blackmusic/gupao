package com.hei.netty.im.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * 自定义消息编码器
 */
public class IMEncoder extends MessageToByteEncoder<IMMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, IMMessage imMessage, ByteBuf byteBuf) throws Exception {
        byteBuf.writeBytes(new MessagePack().write(imMessage));
    }

    public String encoder(IMMessage message) {
        if (message == null) {
            return null;
        }

        String prex = "[".concat(message.getCmd()).concat("][").concat(message.getTime() + "]");
        if (IMP.LOGIN.getName().equals(message.getCmd()) || IMP.FLOWER.getName().equals(message.getCmd())) {
            prex = prex.concat("[").concat(message.getSender()).concat("][").concat(message.getTerminal()).concat("]");
        } else if (IMP.CHAT.getName().equals(message.getCmd())) {
            prex = prex.concat("[").concat(message.getSender()).concat("]");
        } else if (IMP.SYSTEM.getName().equals(message.getCmd())) {
            prex = prex.concat("[").concat(message.getOnline() + "]");
        }

        if (null != message.getContent() && !"".equals(message.getContent())) {
            prex = prex.concat(" - ").concat(message.getContent());
        }
        return prex;
    }
}
