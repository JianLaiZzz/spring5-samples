package com.zhw.mvcframework.aop.aspect;

import java.lang.reflect.Method;

/**
 * @author zhangwei
 * @date 2021/3/14 16:34
 **/
public interface ZhwJoinPoint
{

	/**
	 * 业务方法本身
	 * 
	 * @return
	 */
	Method getMethod();

	/**
	 * 该方法实参列表
	 * 
	 * @return
	 */
	Object[] getMethodArg();

	/**
	 * 该方法所属实列对象
	 * 
	 * @return
	 */
	Object getThis();

	/**
	 * 在JoinPoint 添加自定义属性
	 * 
	 * @param key
	 * @param value
	 */
	void setAttribute(String key, Object value);

	/**
	 * 获取属性
	 * 
	 * @param key
	 * @return
	 */
	Object getAttribute(String key);

}
