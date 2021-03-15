package com.zhw.mvcframework.aop.invocation;

import java.lang.reflect.Method;

public interface ZhwInvocationHandler
{

	Object invoke(Object proxy, Method method, Object[] args) throws Exception;
}
