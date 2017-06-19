package com.gt.common.aop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gt.dao.common.BusErpLogMapper;
import com.gt.dao.common.BusUserMapper;
import com.gt.dao.common.TCommonStaffMapper;
import com.gt.entity.common.BusErpLog;
import com.gt.entity.common.BusUser;
import com.gt.entity.common.TCommonStaff;
import com.gt.util.CommonUtil;
import com.gt.util.DateTimeKit;
import com.gt.util.IPKit;
import com.gt.util.PropertiesUtil;
import com.gt.util.SessionUtil;
/**
 * aop 注解
 * @author Administrator
 *
 */
@Aspect
@Component
public class SpringMenusService {
	@Autowired
	private BusUserMapper busUserMapper;
	@Autowired
	private TCommonStaffMapper tCommonstaffMapper;
	@Autowired
	private BusErpLogMapper busErpLog;
	/**
	 * 找到每个controller中拥有anno注解的方法,在执行目标函数之前执行 菜单权限AOP
	 * @param joinPoint
	 * @param anno
	 * @throws Throwable
	 */
	@Before("within(com.gt.controller..*) && @annotation(anno)")
	public void conntrollerAop(JoinPoint  joinPoint, MenuAnnotation anno)
			throws Throwable {
		HttpServletResponse response = null;
		HttpServletRequest request = null;
		try {
			Object[] paramObjs = joinPoint.getArgs();
			for (Object object : paramObjs) {
				if(object.getClass().toString().endsWith("ResponseFacade"))
				{
					response = (HttpServletResponse) object;
				}
				if(object.getClass().toString().endsWith("RequestFacade"))
				{
					request = (HttpServletRequest) object;
				}
			}
			if(CommonUtil.isEmpty(response)){
				throw new Exception("方法中缺少参数：HttpServletResponse！");
			}
			if(CommonUtil.isEmpty(request)){
				throw new Exception("方法中缺少参数：HttpServletRequest！");
			}
			if(CommonUtil.isEmpty(anno.menus_url())){
				throw new Exception("菜单路径为空！");
			}
			String menus_url = anno.menus_url();
			String styles = anno.styles();
			List<Map<String,Object>> menusListTwo = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> menusList = SessionUtil.GetMenusList(request);//获取全部菜单
			Integer pid = 0;
			for(int i=0;i<menusList.size();i++){
				Map<String,Object> menusListwpxMap = menusList.get(i);
				Object url = menusListwpxMap.get("url");
				if(url.equals(menus_url)){
					if(!styles.equals("1")){
						pid = Integer.valueOf(menusListwpxMap.get("menus_pid").toString());
					}else{
						pid = Integer.valueOf(menusListwpxMap.get("id").toString());
					}

				}
			}
			if(!pid.equals(0)){
				for(int i=0;i<menusList.size();i++){
					Map<String,Object> menusListwpxMap = menusList.get(i);
					Object menus_pid = menusListwpxMap.get("menus_pid");
					if(menus_pid.equals(pid)){
						menusListTwo.add(menusListwpxMap);
					}
				}	
			}
			request.setAttribute("menusList", menusListTwo);			
		} catch (Exception e) {
			response.sendRedirect("/jsp/error/404.jsp");
			e.printStackTrace();
		}
	}
	/**
	 * 找到每个controller中拥有anno注解的方法,在执行目标函数之前执行 ，日志记录AOP,
	 * @param joinPoint
	 * @param loganno 1代表查询 2代表新增，3代表修改，4代表删除，5代表其他
	 * @throws Throwable
	 */
	@Before("within(com.gt.controller..*) && @annotation(loganno)")
	public void conntrollerAop(JoinPoint  joinPoint, LogAnnotation loganno)
			throws Throwable {
		HttpServletResponse response = null;
		HttpServletRequest request = null;
		try {
			Object[] paramObjs = joinPoint.getArgs();
			for (Object object : paramObjs) {
				if(object.getClass().toString().endsWith("ResponseFacade"))
				{
					response = (HttpServletResponse) object;
				}
				if(object.getClass().toString().endsWith("RequestFacade"))
				{
					request = (HttpServletRequest) object;
				}
			}
			if(CommonUtil.isEmpty(response)){
				throw new Exception("方法中缺少参数：HttpServletResponse！");
			}
			if(CommonUtil.isEmpty(request)){
				throw new Exception("方法中缺少参数：HttpServletRequest！");
			}
			BusErpLog obj = new BusErpLog();
			Integer loginStyle = SessionUtil.getLoginStyle(request);//获取登录人来源
			//==0员工属性
			if(loginStyle==0){
				TCommonStaff staff = SessionUtil.getTCommonStaff(request);
				obj.setLogStyle(0);
				obj.setLogPerson(staff.getName());
			}else{
				BusUser user = SessionUtil.getBusUser(request);
				obj.setLogStyle(1);
				obj.setLogPerson(user.getName());
			}
			obj.setLogTime(DateTimeKit.getNow());
			obj.setLogDesc(loganno.log_desc());
			obj.setLogController(joinPoint.getSignature().getDeclaringTypeName());//获取AOP使用的CONTROLLER名；
			obj.setLogMethod(joinPoint.getSignature().getName());//获取AOP使用的方法名；
			obj.setLogFunction(loganno.log_type());
			obj.setLogIp(IPKit.getIpAddr(request));
			obj.setLogTypeErp(PropertiesUtil.getErpType());
			busErpLog.insertSelective(obj);
		} catch (Exception e) {
			response.sendRedirect("/jsp/error/404.jsp");
			e.printStackTrace();
		}
	}
}
