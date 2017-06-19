package com.gt.service.common.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gt.dao.common.BusUserMapper;
import com.gt.entity.common.BusUser;
import com.gt.service.common.IBusUserService;
import com.gt.util.CommonUtil;

@Service
public class BusUserServiceImpl implements IBusUserService {
	@Autowired
	private BusUserMapper busUserMapper;
	@Override
	public BusUser pidUser(Integer userid) {
		BusUser user = new BusUser();
		Boolean result = true;
		while(result){
			if(CommonUtil.isNotEmpty(user)&&user.getPid()==0){
				result = false;
			}else{
				user = busUserMapper.selectByPrimaryKey(userid);
				userid = user.getId();
			}
		}
		return user;
	}

}
