package com.hei.netty.im.processor;

import com.alibaba.fastjson.JSONObject;
import com.hei.netty.im.protocol.IMDecoder;
import com.hei.netty.im.protocol.IMEncoder;
import com.hei.netty.im.protocol.IMMessage;
import com.hei.netty.im.protocol.IMP;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 自定义协议内容的逻辑处理
 */
public class MessageProcessor {

    // 记录在线用户数
    private static ChannelGroup onlineUsers = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    // 自定义一些扩展属性
    private static final AttributeKey<String> NICK_NAME = AttributeKey.valueOf("nickName");
    private static final AttributeKey<String> IP_ADDR = AttributeKey.valueOf("ipAddr");
    private static final AttributeKey<JSONObject> ATTRS = AttributeKey.valueOf("attrs");
    private static final AttributeKey<String> FROM = AttributeKey.valueOf("FROM");
    // 自定义编码和解码
    private IMEncoder encoder = new IMEncoder();
    private IMDecoder decoder = new IMDecoder();

    /**
     * 获取用户昵称
     *
     * @param client
     * @return
     */
    public String getNickName(Channel client) {
        try {
            return client.attr(NICK_NAME).get();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取扩展属性
     *
     * @param client
     * @return
     */
    public JSONObject getAttrs(Channel client) {
        try {
            return client.attr(ATTRS).get();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 设置扩展属性
     *
     * @param client
     * @param key
     * @param value
     */
    public void setAttr(Channel client, String key, Object value) {
        try {
            JSONObject jsonObject = client.attr(ATTRS).get();
            jsonObject.put(key, value);
            client.attr(ATTRS).set(jsonObject);
        } catch (Exception e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(key, value);
            client.attr(ATTRS).set(jsonObject);
        }
    }

    /**
     * 用户登出
     *
     * @param client
     */
    public void logout(Channel client) {
        // nickName为空，表示没有遵从聊天协议，非法登录
        if (getNickName(client) == null) {
            return;
        }
        for (Channel channel : onlineUsers) {
            IMMessage message = new IMMessage(IMP.SYSTEM.getName(), sysTime(), onlineUsers.size(), getNickName(client) + "离开");
            String content = encoder.encoder(message);
            channel.writeAndFlush(new TextWebSocketFrame(content));
        }
        onlineUsers.remove(client);
    }

    /**
     * 发送消息
     *
     * @param client
     * @param message
     */
    public void sendMsg(Channel client, IMMessage message) {
        sendMsg(client, encoder.encoder(message));
    }

    public void sendMsg(Channel client, String msg) {
        IMMessage request = decoder.decoder(msg);
        if (request == null) {
            return;
        }
        String addr = getAddress(client);
        if (IMP.LOGIN.getName().equals(request.getCmd())) {
            client.attr(NICK_NAME).getAndSet(request.getSender());
            client.attr(IP_ADDR).getAndSet(addr);
            client.attr(FROM).getAndSet(request.getTerminal());
            onlineUsers.add(client);
            for (Channel channel : onlineUsers) {
                boolean isSelf = channel == client;
                if (!isSelf) {
                    request = new IMMessage(IMP.SYSTEM.getName(), sysTime(), onlineUsers.size(), getNickName(client) + "加入");
                } else {
                    request = new IMMessage(IMP.SYSTEM.getName(), sysTime(), onlineUsers.size(), "已于服务器建立连接");
                }
                if ("Console".equals(channel.attr(FROM).get())) {
                    channel.writeAndFlush(request);
                    continue;
                }
                String content = encoder.encoder(request);
                channel.writeAndFlush(new TextWebSocketFrame(content));
            }
        } else if (IMP.CHAT.getName().equals(request.getCmd())) {
            for (Channel channel : onlineUsers) {
                boolean isSelf = channel == client;
                if (isSelf) {
                    request.setSender("you");
                } else {
                    request.setSender(getNickName(client));
                }
                request.setTime(sysTime());
                if ("Console".equals(channel.attr(FROM).get()) && !isSelf) {
                    channel.writeAndFlush(request);
                    continue;
                }
                String content = encoder.encoder(request);
                channel.writeAndFlush(new TextWebSocketFrame(content));
            }
        } else if (IMP.FLOWER.getName().equals(request.getCmd())) {
            JSONObject attrs = getAttrs(client);
            long currentTime = sysTime();
            if (null != attrs) {
                long lastTime = attrs.getLongValue("lastFlowerTime");
                int secends = 10;
                long sub = currentTime - lastTime;
                if (sub < 1000 * secends) {
                    request.setSender("you");
                    request.setCmd(IMP.SYSTEM.getName());
                    request.setContent("您送鲜花太频繁，" + (secends - Math.round(sub / 1000)) + "秒后再试");
                    String content = encoder.encoder(request);
                    client.writeAndFlush(new TextWebSocketFrame(content));
                    return;
                }
            }
            // 正常送鲜花
            for (Channel channel : onlineUsers) {
                if (channel == client) {
                    request.setSender("you");
                    request.setContent("你给大家送了一波鲜花雨");
                    setAttr(client, "lastFlowerTime", currentTime);
                } else {
                    request.setSender(getNickName(client));
                    request.setContent(getNickName(client) + "送来一波鲜花雨");
                }
                request.setTime(sysTime());
                String content = encoder.encoder(request);
                channel.writeAndFlush(new TextWebSocketFrame(content));
            }
        }
    }

    public String getAddress(Channel client) {
        return client.remoteAddress().toString().replaceFirst("/", "");
    }

    private long sysTime() {
        return System.currentTimeMillis();
    }

}
