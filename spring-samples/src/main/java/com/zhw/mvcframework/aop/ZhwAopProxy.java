package com.zhw.mvcframework.aop;

/**
 * 代理工厂的顶层接口
 */
public interface ZhwAopProxy
{

	/**
	 * 获取一个代理对象
	 * 
	 * @return
	 */
	Object getProxy();

	/**
	 * 通过自定义类加载器获得一个代理对象
	 * 
	 * @param classLoader
	 * @return
	 */
	Object getProxy(ClassLoader classLoader);

}
