package com.gupaoedu.adapter.custom;


/**
 * 适配器的实现方式，
 *  1通过静态代理模式实现
 *  2.铜鼓继承的方式实现
 *
 *
 */
public class AdapterTest {
    public static void main(String[] args) {
        SendMsgAdapter sendMsgAdapter = new SendMsgAdapter();

        sendMsgAdapter.sendEmail("1362", "测试邮件");

        sendMsgAdapter.sendSMS("183", "短信");
    }
}
