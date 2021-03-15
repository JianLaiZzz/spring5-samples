package com.zhw.mvcframework.aop.aspect;

import com.zhw.mvcframework.aop.interceptor.ZhwMethodInterceptor;
import com.zhw.mvcframework.aop.interceptor.ZhwMethodInvocation;

import java.lang.reflect.Method;

public class ZhwAfterThrowingAdvice extends ZhwAbstractAspectJAdvice
		implements ZhwAdvice, ZhwMethodInterceptor
{
	private String throwingName;

	public ZhwAfterThrowingAdvice(Method aspectMethod, Object aspectTarget)
	{
		super(aspectMethod, aspectTarget);
	}

	@Override
	public Object invoke(ZhwMethodInvocation invocationHandler) throws Throwable {
		try
		{
			invocationHandler.process();
		}
		catch (Throwable throwable)
		{
			invokeAdviceMethod(invocationHandler, null, throwable.getCause());
			throw throwable;
		}
		return null;
	}

	public void setThrowName(String throwName)
	{
		this.throwingName = throwName;
	}
}
