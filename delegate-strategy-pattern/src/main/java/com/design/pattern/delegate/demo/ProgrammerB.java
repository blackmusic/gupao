package com.design.pattern.delegate.demo;

public class ProgrammerB implements Programmer {
    @Override
    public void doing(String command) {
        System.out.println("我是干前台的");
    }
}
