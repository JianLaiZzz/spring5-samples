package com.zhw.mvcframework.demo.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhw.mvcframework.annotation.ZhwAutowired;
import com.zhw.mvcframework.annotation.ZhwController;
import com.zhw.mvcframework.annotation.ZhwRequestMapping;
import com.zhw.mvcframework.annotation.ZhwRequestParam;
import com.zhw.mvcframework.demo.service.impl.IDemoService;

/**
 * @author zhangwei
 * @date 2021/1/3 16:50
 **/
@ZhwController
@ZhwRequestMapping("/demo")
public class DemoAction
{

	@ZhwAutowired
	private IDemoService demoService;

	public void query(HttpServletRequest request, HttpServletResponse response,
			@ZhwRequestParam("name") String name)
	{

		String result = demoService.getName(name);

		try
		{
			response.getWriter().write(result);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
