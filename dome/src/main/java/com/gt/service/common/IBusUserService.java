package com.gt.service.common;

import com.gt.entity.common.BusUser;

/**
 * BUSuser 商家用户id接口列表
 * @author Administrator
 *
 */
public interface IBusUserService {
     
	/**
	 * 根据子账号id获取最初父类的全部信息
	 * @param userid
	 * @return
	 */
	public BusUser pidUser(Integer userid);
}
