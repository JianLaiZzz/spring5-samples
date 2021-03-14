package com.zhw.mvcframework.aop.interceptor;

/**
 * 方法拦截器顶层接口
 */
public interface ZhwMethodInterceptor
{

	Object invoke(ZhwMethodInvocation invocationHandler) throws Throwable;
}
