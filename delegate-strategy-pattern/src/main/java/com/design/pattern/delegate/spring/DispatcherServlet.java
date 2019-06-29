package com.design.pattern.delegate.spring;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherServlet extends HttpServlet {



    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拦截请求
     * @param req
     * @param resp
     */
    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws
            Exception{
        String uri = req.getRequestURI();
        String mid = req.getParameter("mid");

        if("/getControllerAById".equals(uri)){
            new ControllerA().getControllerAById(mid);
        }else if("/getControllerBById".equals(uri)){
            new ControllerB().getControllerBById(mid);
        }else if("/getControllerCById".equals(uri)){
            new ControllerC().getControllerCById(mid);
        }else {
            resp.getWriter().write("404 Not Found!!");
        }


    }
}
