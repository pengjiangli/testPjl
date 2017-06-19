package com.gt.dao.common;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 字典里面的所有接口
 * @author Administrator
 *
 */
public interface DictMapper {
	/**
	 * 根据字典属性获取key_value 值
	 * @param style 如1001
	 * @return
	 */
	public List<Map<String,Object>> KeyValueList(String style);
	
	/**
	 * 据字典属性和key 获取value 值
	 * @param style 如1001
	 * @param key 如1
	 * @return
	 */
	public List<Map<String,Object>> StyleKeyList(@Param("style")String style,@Param("item_key")String item_key);

}
