package com.zhw.mvcframework.aop;

import com.zhw.mvcframework.aop.interceptor.ZhwMethodInvocation;
import com.zhw.mvcframework.aop.invocation.ZhwInvocationHandler;
import com.zhw.mvcframework.aop.support.ZhwAdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class ZhwJdkDynamicAopProxy implements ZhwAopProxy, InvocationHandler
{

	private ZhwAdvisedSupport support;

	public ZhwJdkDynamicAopProxy(ZhwAdvisedSupport support)
	{
		this.support = support;
	}

	@Override
	public Object getProxy()
	{
		return this.getProxy(support.getClass().getClassLoader());
	}

	@Override
	public Object getProxy(ClassLoader classLoader)
	{
		return Proxy.newProxyInstance(classLoader, support.getTargetClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{

		List<Object> interceptors = support.getInterceptorsAndDynamicInterceptionAdvice(method,
				support.getTargetClass());

		ZhwMethodInvocation invocation = new ZhwMethodInvocation(proxy, method, support.getTarget(),
				support.getTargetClass(), args, interceptors);
		return invocation.process();
	}
}
