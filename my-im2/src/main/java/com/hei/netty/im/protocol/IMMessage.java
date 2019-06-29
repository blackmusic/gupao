package com.hei.netty.im.protocol;

import lombok.Data;
import org.msgpack.annotation.Message;

/**
 * 自定义协议对象
 */
@Data
@Message
public class IMMessage {
    // IP地址和端口
    private String addr;
    // 命令 SYSTEM
    private String cmd;
    // 时间
    private long time;
    // 当前在线人数
    private int online;
    // 发送者
    private String sender;
    // 接受者
    private String receiver;
    // 内容
    private String content;
    // 终端
    private String terminal;

    public IMMessage(){}

    public IMMessage(String cmd, long time, int online, String content) {
        this.cmd = cmd;
        this.time = time;
        this.online = online;
        this.content = content;
    }

    public IMMessage(String cmd, String terminal, long time, String sender) {
        this.cmd = cmd;
        this.time = time;
        this.sender = sender;
        this.terminal = terminal;
    }

    public IMMessage(String cmd, long time, String sender, String content) {
        this.cmd = cmd;
        this.time = time;
        this.sender = sender;
        this.content = content;
    }

    @Override
    public String toString() {
        return "IMMessage{" +
                "addr='" + addr + '\'' +
                ", cmd='" + cmd + '\'' +
                ", time=" + time +
                ", online=" + online +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", content='" + content + '\'' +
                ", terminal='" + terminal + '\'' +
                '}';
    }
}
