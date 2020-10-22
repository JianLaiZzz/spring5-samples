package com.gupaoedu.demo.mvc.action;

import com.gupaoedu.demo.service.IDemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TwoAction {

    private IDemoService demoService;

    public void edit(HttpServletRequest req, HttpServletResponse resp,
                     String name) {
        String result = demoService.get(name);
        try {
            resp.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
