package com.gt.controller.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gt.entity.common.BusUser;
import com.gt.entity.common.TCommonStaff;
import com.gt.service.common.ILoginService;
import com.gt.util.CommonUtil;
import com.gt.util.SessionUtil;
/**
 * 登陆方法
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="logins")
public class loginController {
	private Logger logger = Logger.getLogger(loginController.class);//记录日志
	@Autowired
	private ILoginService loginService;
	/**
	 * 登陆页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request , HttpServletResponse response){
		String jsp = "";
		String ua=request.getHeader("User-Agent");  
		boolean flag=CommonUtil.checkAgentIsMobile(ua);
		if(flag){
			jsp = "login/loginPhone";
		}else{
			jsp = "login/loginPc";
		}
		return jsp;		
	}
	/**
	 * 验证登陆
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/Verification")
	public void Verification(HttpServletRequest request , HttpServletResponse response) throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			String style = request.getParameter("style").toString();
			//style==0员工登陆验证,其余是老板验证
			if(style.equals("0")||style=="0"){
				String login_name = request.getParameter("login_name").toString();
				Integer islogin_name = loginService.Stafflogin_name(login_name);
				if(islogin_name==0){
					map.put("error", "2");
					map.put("message", "用户名不存在");
				}else{
					String password = request.getParameter("password").toString();
					TCommonStaff commonstaff = loginService.StaffVerification(login_name, password);
					if(CommonUtil.isEmpty(commonstaff)){
						map.put("error", "2");
						map.put("message", "用户名或密码错误");
					}else{
						if(commonstaff.getIsdelect()==1){
							map.put("error", "2");
							map.put("message", "该用户已删除");
						}else if(commonstaff.getStatus()==0){
							SessionUtil.setLoginStyle(0, request);
							SessionUtil.setTCommonStaff(commonstaff, request);//存到SESSION
							map.put("error", "0");
						}else{
							map.put("error", "2");
							map.put("message", "该用户已被冻结");
						}
					}
				}
			}else{
				String login_name = request.getParameter("login_name").toString();
				Integer islogin_name = loginService.BusUserLogin_name(login_name);
				if(islogin_name==0){
					map.put("error", "2");
					map.put("message", "用户名不存在");
				}else{
					String password = request.getParameter("password").toString();
					BusUser user = loginService.busUserVerification(login_name, password);
					if(CommonUtil.isEmpty(user)){
						map.put("error", "2");
						map.put("message", "用户名或密码错误");
					}else{
						if(user.getStatus().equals("0")||user.getStatus()=="0"){
							SessionUtil.setLoginStyle(1, request);
							SessionUtil.setBusUser(user, request);//存到SESSION
							map.put("error", "0");
						}else{
							map.put("error", "2");
							map.put("message", "该用户已被冻结");
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("员工登陆验证异常"+e.getMessage());
			map.put("error", "1");
		}
		CommonUtil.write(response, map);
	}
	/**
	 * 登陆成功里面的跳转页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/loginSussess")
	public String loginSussess(HttpServletRequest request , HttpServletResponse response){
		Map<String,Object> mapmenus = new HashMap<String,Object>();
		List<Map<String,Object>> menusLevelList = loginService.menusLevelList(request, 0);//获取商家的版本 
		if(menusLevelList.size()>0){
			mapmenus = loginService.loginSussessMap(request,0,menusLevelList.get(0).get("levelid").toString());
		}
		request.setAttribute("map", mapmenus);
		return "login/index";
	}
}
