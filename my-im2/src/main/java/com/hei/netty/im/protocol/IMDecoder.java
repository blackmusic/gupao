package com.hei.netty.im.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义消息解码器
 */
public class IMDecoder extends ByteToMessageDecoder{

    private Pattern pattern = Pattern.compile("^\\[(.*)\\](\\s\\-\\s(.*))?");
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        try {
            final int length = byteBuf.readableBytes();
            final byte[] array = new byte[length];
            String content = new String(array,byteBuf.readerIndex(),length);
            // 空消息不解析
            if(!(null == content || "".equals(content.trim()))){
                if(!IMP.isIMP(content)){
                    channelHandlerContext.pipeline().remove(this);
                    return;
                }
            }
            byteBuf.getBytes(byteBuf.readerIndex(),array,0,length);
            list.add(new MessagePack().read(array,IMMessage.class));
            byteBuf.clear();
        } catch (Exception e) {
            channelHandlerContext.channel().pipeline().remove(this);
        }
    }

    public IMMessage decoder(String msg){
        if(null == msg || "".equals(msg.trim())){
            return null;
        }
        try {
            Matcher matcher = pattern.matcher(msg);
            String header = "";
            String content = "";
            if(matcher.matches()){
                header = matcher.group(1);
                content = matcher.group(3);
            }
            String[] headers = header.split("\\]\\[");
            long time = Long.parseLong(headers[1]);
            String nickName = headers[2];
            // 昵称最多10个字节
            nickName = nickName.length()<10?nickName:nickName.substring(0,9);
            if(msg.startsWith("["+IMP.LOGIN.getName()+"]")){
                return new IMMessage(headers[0],headers[3],time,nickName);
            }else if(msg.startsWith("["+IMP.CHAT.getName()+"]")){
                return new IMMessage(headers[0],time,nickName,content);
            }else if(msg.startsWith("["+IMP.FLOWER.getName()+"]")){
                return new IMMessage(headers[0],headers[3],time,nickName);
            }else {
                return null;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }

    }
}
