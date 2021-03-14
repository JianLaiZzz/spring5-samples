package com.zhw.mvcframework.aop.interceptor;

import com.zhw.mvcframework.aop.invocation.ZhwInvocationHandler;

/**
 * 方法拦截器顶层接口
 */
public interface ZhwMethodInterceptor
{

	Object invoke(ZhwInvocationHandler invocationHandler) throws Exception;
}
