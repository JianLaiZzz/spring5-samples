package com.zhw.mvcframework.annotation;

import java.lang.annotation.*;

@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZhwRequestParam
{
	String value() default "";
}
