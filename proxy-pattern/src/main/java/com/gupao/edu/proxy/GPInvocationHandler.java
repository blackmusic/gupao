package com.gupao.edu.proxy;

import java.lang.reflect.Method;

/**
 * @author heizq
 * @date 2019-03-13 16:18
 * @since v1.0.0
 */
public interface GPInvocationHandler {

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
