package com.gupao.edu.proxy;

/**
 * @author heizq
 * @date 2019-03-13 17:04
 * @since v1.0.0
 */
public class Test {

    public static void main(String[] args) {
        Girl girl = new Girl();
        Action proxyGirl = (Action)GPProxy.newProxyInstance(new GPClassLoader(), girl.getClass().getInterfaces(), new Maikefeng(girl));
        proxyGirl.say();
    }
}
