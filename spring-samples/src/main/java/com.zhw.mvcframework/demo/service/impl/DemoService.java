package com.zhw.mvcframework.demo.service.impl;

import com.zhw.mvcframework.annotation.ZhwService;

/**
 * @author zhangwei
 * @date 2021/1/3 16:50
 **/
@ZhwService
public class DemoService implements IDemoService
{
	@Override
	public String getName(String name)
	{
		return "Myname is" + name;
	}
}
