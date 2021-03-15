package com.zhw.mvcframework.aop.support;

/**
 * @author zhangwei
 * @date 2021/3/14 16:59
 **/

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zhw.mvcframework.aop.ZhwAopConfig;
import com.zhw.mvcframework.aop.aspect.ZhwAfterThrowingAdvice;
import com.zhw.mvcframework.aop.aspect.ZhwMethodAfterAdvice;
import com.zhw.mvcframework.aop.aspect.ZhwMethodBeforeAdvice;

/**
 * 主要用来解析和封装Aop配置
 * 
 * @author rd217
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

		//切面表达式
		String pointCut = config.getPointCut().replaceAll("\\.", "\\\\.").replaceAll("\\\\.\\*", ".*")
				.replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)");
		//pointCut=public .* com.gupaoedu.vip.spring.demo.service..*Service..*(.*)
		//玩正则
		String pointCutForClassRegex = pointCut.substring(0, pointCut.lastIndexOf("\\(") - 4);
		pointCutClassPattern = Pattern.compile(
				"class " + pointCutForClassRegex.substring(pointCutForClassRegex.lastIndexOf(" ") + 1));

		try
		{
			methodCache = new HashMap<Method, List<Object>>();

			Pattern pattern = Pattern.compile(pointCut);

			Class aspectClass = Class.forName(this.config.getAspectClass());
			Map<String, Method> aspectMethods = new HashMap<String, Method>();
			//切面方法和切面名字
			for (Method m : aspectClass.getMethods())
			{
				aspectMethods.put(m.getName(), m);
			}

			for (Method m : this.targetClass.getMethods())
			{
				String methodString = m.toString();
				if (methodString.contains("throws"))
				{
					methodString = methodString.substring(0, methodString.lastIndexOf("throws")).trim();
				}

				Matcher matcher = pattern.matcher(methodString);
				if (matcher.matches())
				{
					//执行器链,能满足切面规则的类
					List<Object> advices = new LinkedList<Object>();
					//把每一个方法包装成 MethodIterceptor
					//before
					if (!(null == config.getAspectBefore() || "".equals(config.getAspectBefore())))
					{
						//创建一个Advivce
						advices.add(new ZhwMethodBeforeAdvice(aspectMethods.get(config.getAspectBefore()),
								aspectClass.newInstance()));
					}
					//after
					if (!(null == config.getAspectAfter() || "".equals(config.getAspectAfter())))
					{
						//创建一个Advivce
						advices.add(new ZhwMethodAfterAdvice(aspectMethods.get(config.getAspectAfter()),
								aspectClass.newInstance()));
					}
					//afterThrowing
					if (!(null == config.getAspectAfterThrow()
							|| "".equals(config.getAspectAfterThrow())))
					{
						//创建一个Advivce
						ZhwAfterThrowingAdvice throwingAdvice = new ZhwAfterThrowingAdvice(
								aspectMethods.get(config.getAspectAfterThrow()),
								aspectClass.newInstance());
						throwingAdvice.setThrowName(config.getAspectAfterThrowName());
						advices.add(throwingAdvice);
					}
					methodCache.put(m, advices);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
