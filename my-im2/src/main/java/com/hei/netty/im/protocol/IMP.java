package com.hei.netty.im.protocol;

/**
 * 指令
 */
public enum IMP {
    /**
     * 系统指令
     */
    SYSTEM("SYSTEM"),
    /**
     * 登录
     */
    LOGIN("LOGIN"),
    /**
     * 登出
     */
    LOGOUT("LOGOUT"),
    /**
     * 聊天
     */
    CHAT("CHAT"),
    /**
     * 送花
     */
    FLOWER("FLOWER");
    private String name;

    IMP(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static boolean isIMP(String content){
        return content.matches("^\\[(SYSTEM|LOGIN|LOGOUT|CHAT|FLOWER)\\]");
    }
    @Override
    public String toString() {
        return this.toString();
    }
}
