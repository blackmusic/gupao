package com.gupaoedu.guava;

public class TeacherFactory {

    public static Teacher getTeacher(String name) {
        if ("Tom".equals(name)) {
            return new Tom();
        } else if ("Mic".equals(name)) {
            return new Mic();
        }
        return null;

    }
}
