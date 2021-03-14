package com.zhw.mvcframework.aop.interceptor;

import com.zhw.mvcframework.aop.aspect.ZhwJoinPoint;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author zhangwei
 * @date 2021/3/14 20:31
 **/

/**
 * 执行拦截器链
 */
public class ZhwMethodInvocation implements ZhwJoinPoint
{

	private Object proxyObject;

	private Method method;

	private Object target;

	private Class<?> clazz;

	private Object[] args;

	private List<Object> interceptorsAndDynamicMethodMatchers;

	private Map<String, Object> attributes;

	private int currentInterceptorIndex = -1;

	public ZhwMethodInvocation(Object proxyObject, Method method, Object target, Class<?> clazz,
			Object[] args, List<Object> interceptorsAndDynamicMethodMatchers)
	{
		this.proxyObject = proxyObject;
		this.method = method;
		this.target = target;
		this.clazz = clazz;
		this.args = args;
		this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
	}

	public Object process() throws Throwable
	{
		if (this.currentInterceptorIndex == interceptorsAndDynamicMethodMatchers.size() - 1)
		{
			return this.method.invoke(target, args);
		}

		Object interceptorOrInterceptionAdvice = this.interceptorsAndDynamicMethodMatchers
				.get(++this.currentInterceptorIndex);

		if (interceptorOrInterceptionAdvice instanceof ZhwMethodInterceptor)
		{
			ZhwMethodInterceptor interceptor = (ZhwMethodInterceptor) interceptorOrInterceptionAdvice;
			interceptor.invoke(this);
		}
		else
		{

			//动态匹配失败时,略过当前Intercetpor,调用下一个Interceptor
			return process();
		}

		return null;
	}

	@Override
	public Method getMethod()
	{
		return this.method;
	}

	@Override
	public Object[] getMethodArg()
	{
		return this.args;
	}

	@Override
	public Object getThis()
	{
		return this.target;
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
