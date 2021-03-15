package com.zhw.mvcframework.aop.interceptor;

import com.zhw.mvcframework.aop.aspect.ZhwJoinPoint;

import java.lang.reflect.Method;
import java.util.HashMap;
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

	//代理对象
	private Object proxyObject;

	//目标对象方法
	private Method method;

	//目标对象
	private Object target;

	//目标类
	private Class<?> clazz;

	//方法参数
	private Object[] args;

	//拦截器链条
	private final List<Object> interceptorsAndDynamicMethodMatchers;

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
		//如果Interceptor执行完了，则执行joinPoint
		if (this.currentInterceptorIndex == interceptorsAndDynamicMethodMatchers.size() - 1)
		{
			return this.method.invoke(target, args);
		}

		Object interceptorOrInterceptionAdvice = this.interceptorsAndDynamicMethodMatchers
				.get(++this.currentInterceptorIndex);

		//如果要动态匹配joinPoint
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
		if (value != null)
		{
			if (this.attributes == null)
			{
				this.attributes = new HashMap<String, Object>();
			}
			this.attributes.put(key, value);
		}
		else
		{
			if (this.attributes != null)
			{
				this.attributes.remove(key);
			}
		}
	}

	@Override
	public Object getAttribute(String key)
	{
		return (this.attributes != null ? this.attributes.get(key) : null);

	}
}
