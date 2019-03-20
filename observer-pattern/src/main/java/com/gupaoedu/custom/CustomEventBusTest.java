package com.gupaoedu.custom;

public class CustomEventBusTest {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new Notify());
        eventBus.post("测试自定义观察者");
    }
}
