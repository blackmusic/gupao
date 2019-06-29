package com.hei.netty.im.cilent.handler;

import com.hei.netty.im.protocol.IMMessage;
import com.hei.netty.im.protocol.IMP;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class IMClientHandler extends SimpleChannelInboundHandler<IMMessage> {

    private ChannelHandlerContext ctx;
    private String nickName;
    public IMClientHandler(String nickName){
        this.nickName = nickName;
    }

    /**
     * 输入对话内容
     */
    private void session(){
        new Thread(){
            @Override
            public void run() {
                log.info(nickName+"你好，请在控制台输入内容：");
                IMMessage message = null;
                Scanner scanner = new Scanner(System.in);
                do{
                    if(scanner.hasNext()){
                        String input = scanner.nextLine();
                        if("exit".equals(input)){
                            message = new IMMessage(IMP.LOGOUT.getName(),"Console",System.currentTimeMillis(),nickName);
                        }else {
                            message = new IMMessage(IMP.CHAT.getName(),System.currentTimeMillis(),nickName,input);
                        }
                    }
                }while (sendMsg(message));
                scanner.close();
            }
        }.start();
    }

    /**
     * 发消息
     * @param message
     * @return
     */
    private boolean sendMsg(IMMessage message) {
        ctx.channel().writeAndFlush(message);
        log.info("请继续输入对话...");

        return message.getCmd().equals(IMP.LOGOUT) ? false : true;
    }

    /**]
     * 建立连接调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        IMMessage message = new IMMessage(IMP.LOGIN.getName(),"Console",System.currentTimeMillis(),this.nickName);
        sendMsg(message);
        log.info("成功连接服务器，已执行登录操作！");
        session();
    }

    /**
     * 收到消息调用
     * @param channelHandlerContext
     * @param message
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, IMMessage message) throws Exception {

        log.info(null == message.getSender()?"":message.getSender()+":"+removeHtmlTag(message.getContent()));
    }

    private String removeHtmlTag(String htmlStr) {
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script=p_script.matcher(htmlStr);
        htmlStr=m_script.replaceAll(""); //过滤script标签

        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style=p_style.matcher(htmlStr);
        htmlStr=m_style.replaceAll(""); //过滤style标签

        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(htmlStr);
        htmlStr=m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("与服务器断开连接："+cause.getMessage());
        ctx.close();
    }
}
