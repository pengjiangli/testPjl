package com.gt.util;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.gt.entity.common.BusUser;
import com.gt.entity.common.TCommonStaff;



/**
 * 专用来存SESSION 的数据方法
 * @author Administrator
 *
 */
public class SessionUtil {
	private static final org.apache.log4j.Logger log = Logger
			.getLogger(SessionUtil.class);
	/**
	 * 登陆员工还是老板属性，老板属性是loginStyle=1，员工属性是loginStyle=0
	 * @param loginStyle
	 * @param request
	 */
	public static void setLoginStyle(Integer loginStyle,HttpServletRequest request){
		request.getSession().setAttribute("loginStyle", loginStyle);
	}
	/**
	 * 获取session登陆属性老板属性是loginStyle=1，员工属性是loginStyle=0,老板用户表是bususer，员工登陆表是TEatSTAFF
	 * @param request
	 * @return
	 */
	public static Integer getLoginStyle(HttpServletRequest request){
		try {
			return  (Integer) request.getSession().getAttribute("loginStyle");
		} catch (Exception e) {
			log.info(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 存取老板登录人的详情信息
	 * @param loginStyle
	 * @param request
	 */
	public static void setBusUser(BusUser obj,HttpServletRequest request){
		request.getSession().setAttribute("BusUser", obj);
	}
	/**
	 * 获取老板登录人的详情信息
	 * @param request
	 * @return
	 */
	public static BusUser getBusUser(HttpServletRequest request){
		try {
			return  (BusUser) request.getSession().getAttribute("BusUser");
		} catch (Exception e) {
			log.info(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 存取员工登录人的详情信息
	 * @param loginStyle
	 * @param request
	 */
	public static void setTCommonStaff(TCommonStaff obj,HttpServletRequest request){
		request.getSession().setAttribute("TCommonStaff", obj);
	}
	/**
	 * 获取员工登录人的详情信息
	 * @param request
	 * @return
	 */
	public static TCommonStaff getTCommonStaff(HttpServletRequest request){
		try {
			return  (TCommonStaff) request.getSession().getAttribute("TCommonStaff");
		} catch (Exception e) {
			log.info(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 用户的菜单存入SESSION
	 * @param request
	 * @param menusList
	 */
	public static void SetMenusList(HttpServletRequest request,List<Map<String,Object>> menusList){
		request.getSession().setAttribute(PropertiesUtil.redisNamePrefix()+"menusList", menusList);
	}
	
	/**
	 * 获取菜单存入的Session
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> GetMenusList(HttpServletRequest request){
		List<Map<String,Object>> menusList = null;
		if(CommonUtil.isNotEmpty(request.getSession().getAttribute(PropertiesUtil.redisNamePrefix()+"menusList"))){
			menusList = (List<Map<String, Object>>) request.getSession().getAttribute(PropertiesUtil.redisNamePrefix()+"menusList");
		}
		return menusList;
	}
}
