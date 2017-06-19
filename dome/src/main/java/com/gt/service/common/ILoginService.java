package com.gt.service.common;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gt.entity.common.BusUser;
import com.gt.entity.common.TCommonStaff;

/**
 * 登陆方法的接口
 * @author Administrator
 *
 */
public interface ILoginService {
	/**
	 * 验证员工是否存在
	 * @param login_name
	 * @return
	 */
	public Integer Stafflogin_name(String login_name);
	/**
	 * 员工登陆验证
	 * @param login_name 登录名
	 * @param password 登录人密码
	 * @return
	 */
	public TCommonStaff StaffVerification(String login_name,String password);
	/**
	 * 验证店长登录名是否存在
	 * @param login_name
	 * @return
	 */
	public Integer BusUserLogin_name(String login_name);
	/**
	 * 老板登陆
	 * @param login_name登录名
	 * @param password密码
	 * @return
	 */
	public BusUser busUserVerification(String login_name,String password);
	/**
	 * 登陆成功之后，需要调用的信息  data 有值,暂时传一个1，代表是要移动端菜单封装  data 
	 * @param request
	 * @return
	 */
	public Map<String,Object> loginSussessMap(HttpServletRequest request,Integer loginuc,String levelmodel);
	/**
	 * 根据商家信息，获取商家拥有的版本
	 * @param request
	 * @return loginuc  0  代表PC  1代表移动端
	 */
	public List<Map<String,Object>> menusLevelList(HttpServletRequest request,Integer loginuc); 

}
