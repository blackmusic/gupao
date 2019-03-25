package com.hei.spring.mvc;

import com.hei.spring.mvc.annotation.HAutowired;
import com.hei.spring.mvc.annotation.HController;
import com.hei.spring.mvc.annotation.HRequestMapping;
import com.hei.spring.mvc.annotation.HRequestParam;
import com.hei.spring.mvc.annotation.HService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author heizq
 * @date 2019-03-25 15:25
 * @since v1.0.0
 */
public class HDispatcherServlet extends HttpServlet {

    private Properties context = new Properties();

    private List<String> classNames = new ArrayList<>();

    private Map<String, Object> ioc = new HashMap<>();

    private Map<String, Method> handlerMapping = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatcher(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            //1.加载配置文件
            doLoadConfig(config.getInitParameter("contextConfigLocation"));
            //2.扫描类
            doScanner(context.getProperty("autoScanPackage"));
            //3.注册
            doIoc();
            //4.自动注入
            doAutowired();
            //5.建立URL和method的映射关系
            doHandlerMapping();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String url = req.getRequestURI();
            if("/favicon.ico".equals(url)){
                return;
            }
            Method method = handlerMapping.get(url);

            Map<String, Integer> paramMapping = new HashMap<>();
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                for (Annotation a : parameterAnnotations[i]) {
                    if (a instanceof HRequestParam) {
                        String paramName = ((HRequestParam) a).value();
                        if (!"".equals(paramName)) {
                            paramMapping.put(paramName, i);
                        }
                    }
                }
            }

            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> type = parameterTypes[i];
                if (type == HttpServletRequest.class || type == HttpServletResponse.class) {
                    paramMapping.put(type.getName(), i);
                }
            }

            Map<String, String[]> parameterMap = req.getParameterMap();
            Object[] paramValues = new Object[parameterTypes.length];
            for (Map.Entry<String, String[]> param : parameterMap.entrySet()) {
                String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "").replaceAll("\\s", "");
                if (!paramMapping.containsKey(param.getKey())) {
                    continue;
                }
                int index = paramMapping.get(param.getKey());
                paramValues[index] = caseStringValue(value, parameterTypes[index]);
            }
            if (paramMapping.containsKey(HttpServletRequest.class.getName())) {
                int reqIndex = paramMapping.get(HttpServletRequest.class.getName());
                paramValues[reqIndex] = req;
            }
            if (paramMapping.containsKey(HttpServletResponse.class.getName())) {
                int respIndex = paramMapping.get(HttpServletResponse.class.getName());
                paramValues[respIndex] = resp;
            }

            method.invoke(ioc.get(toLowerFirstCase(method.getDeclaringClass().getSimpleName())), paramValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doHandlerMapping() {
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(HController.class)) {
                continue;
            }
            String baseUrl = "";
            if (clazz.getAnnotation(HRequestMapping.class) != null) {
                baseUrl = clazz.getAnnotation(HRequestMapping.class).value();
            }
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(HRequestMapping.class)) {
                    continue;
                }
                HRequestMapping requestMapping = method.getAnnotation(HRequestMapping.class);
                String url = "/" + baseUrl + "/" + requestMapping.value();
                handlerMapping.put(url.replaceAll("/+", "/"), method);
            }
        }
    }

    private void doAutowired() throws Exception {
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Field[] declaredFields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                if (!field.isAnnotationPresent(HAutowired.class)) {
                    continue;
                }
                HAutowired autowired = field.getAnnotation(HAutowired.class);
                String beanName = autowired.value();
                if ("".equals(beanName)) {
                    beanName = field.getType().getName();
                }
                field.setAccessible(true);

                field.set(entry.getValue(), ioc.get(beanName));
            }
        }
    }

    private void doIoc() throws Exception {
        if (classNames.isEmpty()) {
            return;
        }
        for (String className : classNames) {
            Class<?> clazz = Class.forName(className);
            if (clazz.isAnnotationPresent(HController.class)) {
                String beanName = toLowerFirstCase(clazz.getSimpleName());
                Object instance = clazz.newInstance();
                ioc.put(beanName, instance);
            } else if (clazz.isAnnotationPresent(HService.class)) {
                HService service = clazz.getAnnotation(HService.class);
                String beanName = service.value();
                if ("".equals(beanName)) {
                    beanName = toLowerFirstCase(clazz.getSimpleName());
                }

                Object instance = clazz.newInstance();
                ioc.put(beanName, instance);
                Class<?>[] interfaces = clazz.getInterfaces();
                for (Class i : interfaces) {
                    ioc.put(i.getName(), instance);
                }
            } else {
                continue;
            }
        }
    }

    private void doScanner(String autoScanPackage) {
        // 获取绝对路径
        URL url = this.getClass().getClassLoader().getResource(autoScanPackage.replaceAll("\\.", "/"));
        File file = new File(url.getFile());
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                doScanner(autoScanPackage + "." + f.getName());
            }
            if (!f.getName().endsWith(".class")) {
                continue;
            }

            classNames.add(autoScanPackage + "." + f.getName().replaceAll(".class", ""));

        }
    }

    private void doLoadConfig(String contextConfigLocation) throws IOException {
        InputStream resource = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        context.load(resource);
    }

    private String toLowerFirstCase(String simpleClassName) {
        char[] chars = simpleClassName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    private Object caseStringValue(String value, Class<?> clazz) {
        if (clazz == String.class) {
            return value;
        } else if (clazz == Integer.class) {
            return Integer.valueOf(value);
        } else if (clazz == int.class) {
            return Integer.valueOf(value).intValue();
        } else {
            return null;
        }
    }
}
