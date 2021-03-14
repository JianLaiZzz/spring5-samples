package com.zhw.mvcframework.aop;

/**
 * @author zhangwei
 * @date 2021/3/14 16:43
 **/

import lombok.Data;

/**
 * 定义AOP的配置信息的封装对象，以方便在之后的代码中相互传
 * 递
 */
@Data
public class ZhwAopConfig
{

	//切面表达式
	private String pointCut;

	//前置通知方法名
	private String aspectBefore;

	//后置通知方法名
	private String aspectAfter;

	//切面类
	private String aspectClass;

	//异常通知方法名
	private String aspectAfterThrow;

	//需要通知的异常类型
	private String aspectAfterThrowName;

}
