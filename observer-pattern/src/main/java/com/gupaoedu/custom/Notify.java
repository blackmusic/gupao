package com.gupaoedu.custom;

public class Notify {

    @Subscribe
    public void notify(String content) {
        System.out.println("内容:" + content);
    }
}
