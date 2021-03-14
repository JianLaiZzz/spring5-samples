package com.zhw.mvcframework.aop.aspect;

import java.lang.reflect.Method;

/**
 * @author zhangwei
 * @date 2021/3/14 20:10
 **/
public abstract class ZhwAbstractAspectJAdvice implements ZhwAdvice
{

	private Method aspectMethod;

	private Object aspectTarget;

	public ZhwAbstractAspectJAdvice(Method aspectMethod, Object aspectTarget)
	{
		this.aspectMethod = aspectMethod;
		this.aspectTarget = aspectTarget;
	}

	protected Object invokeAdviceMethod(ZhwJoinPoint point, Object returnValue, Throwable ex)
			throws Exception
	{

		Class<?>[] parameterTypes = this.aspectMethod.getParameterTypes();

		if (null == parameterTypes || parameterTypes.length == 0)
		{

			return this.aspectMethod.invoke(aspectTarget);
		}
		else
		{
			Object[] args = new Object[parameterTypes.length];

			for (int i = 0; i < parameterTypes.length; i++)
			{
				if (parameterTypes[i] == ZhwJoinPoint.class)
				{
					args[i] = point;
				}
				else if (parameterTypes[i] == Throwable.class)
				{
					args[i] = ex;
				}
				else if (parameterTypes[i] == Object.class)
				{
					args[i] = returnValue;
				}
			}
			return this.aspectMethod.invoke(aspectTarget, args);
		}
	}

}
