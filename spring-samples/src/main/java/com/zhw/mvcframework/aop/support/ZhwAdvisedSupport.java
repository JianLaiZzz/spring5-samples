package com.zhw.mvcframework.aop.support;

/**
 * @author zhangwei
 * @date 2021/3/14 16:59
 **/

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.zhw.mvcframework.aop.ZhwAopConfig;

/**
 * 主要用来解析和封装Aop配置
 */
public class ZhwAdvisedSupport
{

	private Class targetClass;

	private Object target;

	private Pattern pointCutClassPattern;

	private transient Map<Method, List<Object>> methodCache;

	private ZhwAopConfig config;

	public Class getTargetClass()
	{
		return targetClass;
	}

	public void setTargetClass(Class targetClass)
	{
		this.targetClass = targetClass;
	}

	public Object getTarget()
	{
		return target;
	}

	public void setTarget(Object target)
	{
		this.target = target;
	}

	public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass)
			throws Exception
	{

		List<Object> cache = methodCache.get(method);
		if (cache == null)
		{
			Method m = targetClass.getMethod(method.getName(), method.getParameterTypes());

			cache = methodCache.get(m);

			//底层逻辑，对代理方法进行一个兼容处理
			this.methodCache.put(m, cache);
		}

		return cache;
	}

	private void parse()
	{

	}

}
