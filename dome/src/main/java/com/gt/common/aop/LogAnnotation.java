package com.gt.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 日志
 * @author Administrator
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {

	/**
	 * 类型
	 * @return
	 */
	public String log_type();
	/**
	 * 描述
	 * @return
	 */
	public String log_desc();
}
