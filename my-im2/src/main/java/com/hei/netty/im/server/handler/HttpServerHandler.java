package com.hei.netty.im.server.handler;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;
@Slf4j
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    // 获取class路径
    private URL url = HttpServerHandler.class.getResource("");
    private String webRoot = "webRoot";

    private File getResource(String fileName) throws Exception {
        String baseUrl = url.toURI().toString();
        int start = baseUrl.indexOf("classes/");
        baseUrl = (baseUrl.substring(0, start) + "/classes/").replaceAll("/+", "/");
        String path = baseUrl + webRoot + "/" + fileName;
        path = !path.contains("file:") ? path : path.substring(5);
        path = path.replaceAll("//", "/");
        return new File(path);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        log.info("接收的一个客户端的，websocket连接！！");
        String uri = request.uri();
        RandomAccessFile file = null;
        try {
            String page = uri.equals("/")?"chat.html":uri;
            file = new RandomAccessFile(getResource(page),"r");
        } catch (Exception e) {
            ctx.fireChannelRead(request.retain());
            return;
        }
        HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(),HttpResponseStatus.OK);
        String contextType = "text/html;";
        if(uri.endsWith(".css")){
            contextType = "text/css;";
        }else if(uri.endsWith("js")){
            contextType = "text/javascript;";
        }else if(uri.toLowerCase().matches(".*\\.(jpg|png|gif)$")){
            String ext = uri.substring(uri.lastIndexOf("."));
            contextType = "image/"+ext;
        }
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE,contextType+"charset=utf-8;");
        boolean keepAlive = HttpHeaders.isKeepAlive(request);
        if(keepAlive){
            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH,file.length());
            response.headers().set(HttpHeaders.Names.CONNECTION,HttpHeaders.Values.KEEP_ALIVE);
        }
        ctx.write(response);
        ctx.write(new DefaultFileRegion(file.getChannel(),0,file.length()));
        ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        if(!keepAlive){
            future.addListener(ChannelFutureListener.CLOSE);
        }
        file.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel client = ctx.channel();
        log.info("Client:" + client.remoteAddress()+"异常");
        cause.printStackTrace();
        ctx.close();
    }
}
