package com.design.pattern.delegate.demo;

public class DelegateTest {

    public static void main(String[] args) {
       new  Manager().command("后台",new GroupLeader());
    }
}
