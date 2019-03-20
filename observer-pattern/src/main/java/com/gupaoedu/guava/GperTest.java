package com.gupaoedu.guava;

import com.google.common.eventbus.EventBus;

public class GperTest {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();

        Gper gper = new Gper(eventBus);

        gper.addListener("Tom");

        gper.addListener("Mic");

        String content = "观察者模式的实现原理";
        gper.publishArticle(content);
    }
}
