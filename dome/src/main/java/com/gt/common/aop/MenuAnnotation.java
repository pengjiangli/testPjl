package com.gt.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 菜单aop
 * @author Administrator
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MenuAnnotation {
	/**
	 * 功能模块
	 * @return
	 */
	public String menus_url() default "" ;
	/**
	 * 0代表菜单，1代表按钮  是按钮的情况下，给上一级的菜单
	 * @return
	 */
	public String styles() default "";
}
