package com.gupaoedu.guava;

import com.google.common.eventbus.Subscribe;

public class Tom implements Teacher {


    /**
     * 接收私信
     *
     * @param content
     */
    @Override
    @Subscribe
    public void notify(String content) {
        System.out.println("Mic收到私信，内容是：" + content);
    }
}
