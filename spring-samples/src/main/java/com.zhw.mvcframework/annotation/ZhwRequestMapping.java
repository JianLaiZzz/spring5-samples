package com.zhw.mvcframework.annotation;

import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZhwRequestMapping
{
	String value() default "";
}
