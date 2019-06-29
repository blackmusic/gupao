package com.gupaoedu.custom;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventBus {

    private List<ObserverClass> observerClasses = new ArrayList<>();


    public void register(Object obj) {
        if (obj != null) {
            Class<?> clazz = obj.getClass();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Subscribe.class)) {
                    ObserverClass observerClass = new ObserverClass(obj, method);
                    observerClasses.add(observerClass);
                }
            }
        }

    }

    public void post(Object object) {

        if (observerClasses.size() == 0) {
            return;
        }

        for (ObserverClass observerClass : observerClasses) {
            try {
                observerClass.getMethod().invoke(observerClass.getObject(), object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }

    }


    private final class ObserverClass {
        private Object object;

        private Method method;

        private ObserverClass(Object object, Method method) {
            this.object = object;
            this.method = method;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }
    }
}
