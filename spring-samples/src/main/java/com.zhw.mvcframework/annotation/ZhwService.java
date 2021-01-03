package com.zhw.mvcframework.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ZhwService
{
	String value() default "";
}
