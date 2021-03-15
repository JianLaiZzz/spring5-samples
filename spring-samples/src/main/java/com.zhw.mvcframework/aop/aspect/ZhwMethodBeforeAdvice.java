package com.zhw.mvcframework.aop.aspect;

import com.zhw.mvcframework.aop.interceptor.ZhwMethodInterceptor;
import com.zhw.mvcframework.aop.interceptor.ZhwMethodInvocation;

import java.lang.reflect.Method;

/**
 * @author zhangwei
 * @date 2021/3/14 20:23
 **/
public class ZhwMethodBeforeAdvice extends ZhwAbstractAspectJAdvice
		implements ZhwAdvice, ZhwMethodInterceptor
{

	private ZhwJoinPoint point;

	public ZhwMethodBeforeAdvice(Method aspectMethod, Object aspectTarget)
	{
		super(aspectMethod, aspectTarget);
	}

	public void before(Method method, Object[] args, Object target) throws Exception
	{
		invokeAdviceMethod(this.point, null, null);
	}

	@Override
	public Object invoke(ZhwMethodInvocation invocationHandler) throws Throwable
	{
		this.point = invocationHandler;

		this.before(invocationHandler.getMethod(), invocationHandler.getMethodArg(),
				invocationHandler.getThis());
		return invocationHandler.process();
	}
}
