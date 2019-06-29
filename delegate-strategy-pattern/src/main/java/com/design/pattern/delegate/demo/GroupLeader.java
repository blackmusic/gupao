package com.design.pattern.delegate.demo;

import java.util.HashMap;
import java.util.Map;

public class GroupLeader implements Programmer {

    private Map<String,Programmer> targets = new HashMap<String,Programmer>();


    public GroupLeader(){
        targets.put("后台",new ProgrammerA());
        targets.put("前台",new ProgrammerB());
    }

    @Override
    public void doing(String command) {
        targets.get(command).doing(command);
    }
}
