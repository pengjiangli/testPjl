package com.gt.service.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.common.BusUserMapper;
import com.gt.dao.common.TCommonStaffMapper;
import com.gt.entity.common.BusUser;
import com.gt.entity.common.TCommonStaff;
import com.gt.service.common.IBusUserService;
import com.gt.service.common.ILoginService;
import com.gt.util.CommonUtil;
import com.gt.util.MD5Util;
import com.gt.util.PropertiesUtil;
import com.gt.util.SHA1;
import com.gt.util.SessionUtil;

@Service
public class LoginServiceImpl implements ILoginService{
	@Autowired
	private BusUserMapper busUserMapper;
	@Autowired
	private TCommonStaffMapper tCommonstaffMapper;
	@Autowired
	private IBusUserService busUserService;
	@Override
	public Integer Stafflogin_name(String login_name) {
		return tCommonstaffMapper.Stafflogin_name(login_name);
	}
	@Override
	public TCommonStaff StaffVerification(String login_name,
			String password) {
		password = MD5Util.getMD5(password);
		return tCommonstaffMapper.selectStaff(login_name, password);
	}
	@Override
	public Integer BusUserLogin_name(String login_name) {
		return busUserMapper.BusUserLogin_name(login_name);
	}
	@Override
	public BusUser busUserVerification(String login_name, String password) {
		password = SHA1.encode(password);
		return busUserMapper.selectUser(login_name, password);
	}
	@Override
	public Map<String, Object> loginSussessMap(HttpServletRequest request,Integer loginuc,String levelmodel) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String,Object>> menuslist = SessionUtil.GetMenusList(request);//获取菜单
		List<Map<String,Object>> menusListOne = new ArrayList<Map<String,Object>>();
		if(CommonUtil.isEmpty(menuslist)){
			Integer loginstyle = SessionUtil.getLoginStyle(request);
			List<Map<String,Object>> menusListwpx = new ArrayList<Map<String,Object>>();
			//员工登陆,否则店长登陆
			if(loginstyle==0){
				TCommonStaff obj = SessionUtil.getTCommonStaff(request); 
				menusListwpx = tCommonstaffMapper.menusList(obj.getId(), PropertiesUtil.getErpType(),loginuc);
			}else{
				BusUser user = SessionUtil.getBusUser(request);		
				if(user.getPid()!=0){
					user = busUserService.pidUser(user.getId());
				}
				menusListwpx = busUserMapper.menusList(user.getId(),PropertiesUtil.getErpType(), user.getLevel(),user.getIndustryid(),loginuc);
			}
			List<Map<String,Object>> menusList = new ArrayList<Map<String,Object>>();
			for(int j=0;j<menusListwpx.size();j++){
				Map<String,Object> menusListwpxMap = menusListwpx.get(j);
				Integer menus_pid = Integer.valueOf(menusListwpxMap.get("menus_pid").toString());
				if(menus_pid==0){
					Object url = menusListwpxMap.get("url");
					Object levelstyle = menusListwpxMap.get("levelstyle").toString();
					if(CommonUtil.isEmpty(url)){
						Integer menus_id = Integer.valueOf(menusListwpxMap.get("id").toString());
						for(int k=0;k<menusListwpx.size();k++){
							Map<String,Object> menusListwpxMapkx = menusListwpx.get(k);
							Integer menus_pidkx = Integer.valueOf(menusListwpxMapkx.get("menus_pid").toString());
							if(menus_id==menus_pidkx){
								url = menusListwpxMapkx.get("url");
								break;
							}
						}
						menusListwpxMap.put("url", url);
					}
					if(levelstyle.equals(levelmodel)){
						menusListOne.add(menusListwpxMap);
					}
				}
				menusList.add(menusListwpxMap);
			}
			SessionUtil.SetMenusList(request, menusList);//菜单存入到Session 里
			map.put("menusListOne", menusListOne);//获取第一级菜单列表
			if(menusListOne != null && menusListOne.size() > 0){
				map.put("pageurl", menusListOne.get(0).get("url"));//登陆进来的第一个菜单
			}
		}else{
			for(int j=0;j<menuslist.size();j++){
				Map<String,Object> menusListwpxMap = menuslist.get(j);
				Integer menus_pid = Integer.valueOf(menusListwpxMap.get("menus_pid").toString());
				if(menus_pid==0){
					Object levelstyle = menusListwpxMap.get("levelstyle").toString();
					if(levelstyle.equals(levelmodel)){
						menusListOne.add(menusListwpxMap);
					}
				}
			}
			map.put("menusListOne", menusListOne);//获取第一级菜单列表
			if(menusListOne != null && menusListOne.size() > 0){
				map.put("pageurl", menusListOne.get(0).get("url"));//登陆进来的第一个菜单
			}
		}
		return map;
	}
	@Override
	public List<Map<String, Object>> menusLevelList(HttpServletRequest request,
			Integer loginuc) {
		List<Map<String,Object>> menusLevelList = new ArrayList<Map<String,Object>>();
		Integer loginstyle = SessionUtil.getLoginStyle(request);
		if(loginstyle==0){
			TCommonStaff obj = SessionUtil.getTCommonStaff(request); 
			menusLevelList = tCommonstaffMapper.menusLevelList(obj.getId(), PropertiesUtil.getErpType(),loginuc);
		}else{
			BusUser user = SessionUtil.getBusUser(request);		
			if(user.getPid()!=0){
				user = busUserService.pidUser(user.getId());
			}
			menusLevelList = busUserMapper.menusLevelList(user.getId(),PropertiesUtil.getErpType(), user.getLevel(),user.getIndustryid(),loginuc);
		}
		return menusLevelList;
	}
}
