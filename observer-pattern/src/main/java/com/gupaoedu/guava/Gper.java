package com.gupaoedu.guava;

import com.google.common.eventbus.EventBus;

public class Gper {

    private EventBus eventBus;

    public Gper(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void publishArticle(String content) {
        System.out.println("发布文章，内容：" + content);
        this.eventBus.post(content);
    }

    public void addListener(String name) {

        Teacher teacher = TeacherFactory.getTeacher(name);
        if (teacher != null) {
            this.eventBus.register(teacher);
        }
    }
}
