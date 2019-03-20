package com.gupaoedu.guava;

import com.google.common.eventbus.Subscribe;

public interface Teacher {


    /**
     * 接收私信
     *
     * @param content
     */
    public void notify(String content);
}
