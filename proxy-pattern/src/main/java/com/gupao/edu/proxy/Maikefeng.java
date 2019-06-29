package com.gupao.edu.proxy;

import java.lang.reflect.Method;

/**
 * @author heizq
 * @date 2019-03-13 16:29
 * @since v1.0.0
 */
public class Maikefeng implements GPInvocationHandler{

    private Object target;

    public Maikefeng(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        beforeSay();
        Object obj = method.invoke(target, args);
        afterSay();
        return obj;
    }

    private void afterSay() {
        System.out.println("发话结束....");
    }

    private void beforeSay() {
        System.out.println("语音美化....");
    }
}
