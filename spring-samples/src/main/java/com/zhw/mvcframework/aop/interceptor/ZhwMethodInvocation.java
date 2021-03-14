package com.zhw.mvcframework.aop.interceptor;

import com.zhw.mvcframework.aop.aspect.ZhwJoinPoint;

import java.lang.reflect.Method;

/**
 * @author zhangwei
 * @date 2021/3/14 20:31
 **/
public class ZhwMethodInvocation implements ZhwJoinPoint
{
	@Override
	public Method getMethod()
	{
		return null;
	}

	@Override
	public Object[] getMethodArg()
	{
		return new Object[0];
	}

	@Override
	public Object getThis()
	{
		return null;
	}

	@Override
	public void setAttribute(String key, Object value)
	{

	}

	@Override
	public Object getAttribute(String key)
	{
		return null;
	}
}
