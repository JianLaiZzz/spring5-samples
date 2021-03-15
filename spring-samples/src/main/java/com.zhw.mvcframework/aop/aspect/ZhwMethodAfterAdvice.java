package com.zhw.mvcframework.aop.aspect;

import com.zhw.mvcframework.aop.interceptor.ZhwMethodInterceptor;
import com.zhw.mvcframework.aop.interceptor.ZhwMethodInvocation;

import javax.swing.*;
import java.lang.reflect.Method;

/**
 * @author rd217
 */
public class ZhwMethodAfterAdvice extends ZhwAbstractAspectJAdvice
		implements ZhwAdvice, ZhwMethodInterceptor
{

	private ZhwJoinPoint point;

	public ZhwMethodAfterAdvice(Method aspectMethod, Object aspectTarget)
	{
		super(aspectMethod, aspectTarget);
	}

	private void after(Method method, Object[] args, Object target, Object retVal) throws Exception
	{

		super.invokeAdviceMethod(this.point, retVal, null);
	}

	@Override
	public Object invoke(ZhwMethodInvocation invocationHandler) throws Throwable
	{
		point = invocationHandler;
		Object returnValue = invocationHandler.process();
		after(invocationHandler.getMethod(), invocationHandler.getMethodArg(),
				invocationHandler.getThis(), returnValue);

		return returnValue;
	}
}
