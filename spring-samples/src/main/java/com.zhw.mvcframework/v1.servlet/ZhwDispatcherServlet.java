package com.zhw.mvcframework.v1.servlet;

import com.zhw.mvcframework.annotation.ZhwController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangwei
 * @date 2021/1/3 16:27
 **/
public class ZhwDispatcherServlet extends HttpServlet {

    private Map<String, Object> mappings = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            this.doDispatch(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Exception " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String requestURI = req.getRequestURI();

        String contextPath = req.getContextPath();
        requestURI = requestURI.replace(contextPath, "").replaceAll("/+", "/");
        if (!mappings.containsKey(requestURI)) {
            resp.getWriter().write("404 Not Found!!");
            return;
        }

        Method method = (Method) mappings.get(requestURI);
        Map<String, String[]> params = req.getParameterMap();
        method.invoke(mappings.get(method.getDeclaringClass().getName()),
                new Object[]{req, resp, params.get("name")});

    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        InputStream inputStream;

        try {
            Properties configContext = new Properties();
            inputStream = this.getClass().getClassLoader()
                    .getResourceAsStream(config.getInitParameter("contextConfigLocation"));
            configContext.load(inputStream);
            String scanPackage = configContext.getProperty("scanPackage");
            doScanner(scanPackage);


            for (String className : mappings.keySet()) {

                if (!className.contains(".")) {
                    continue;
                }

                Class<?> clazz = Class.forName(className);

                if (clazz.isAnnotationPresent(ZhwController.class)) {

                    mappings.put(className, clazz.newInstance());

                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void doScanner(String scanPackage) {

        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classDir = new File(url.getFile());
        for (File file : classDir.listFiles()) {
            if (file.isDirectory()) {

                doScanner(scanPackage + "." + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                String clazzName = (scanPackage + "." + file.getName().replace(".class", ""));
                mappings.put(clazzName, null);
            }

        }
    }

}
