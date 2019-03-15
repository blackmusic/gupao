package com.design.pattern.delegate.demo;

public class Manager  {
    public void command(String command, GroupLeader groupleader){
        groupleader.doing(command);
    }
}
