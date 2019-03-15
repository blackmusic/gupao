package com.design.pattern.delegate.demo;

public class ProgrammerA implements Programmer{
    @Override
    public void doing(String command) {
        System.out.println("我是干后台的");
    }
}
