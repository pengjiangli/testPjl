package com.gt.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Properties 读取工具
 * @author lfx
 *
 */
public class PropertiesUtil {
	
	private static Properties prop=null;
	static {   
        prop = new Properties();   
        InputStream in = PropertiesUtil.class.getResourceAsStream("/system.properties");   
        try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}   
    }   
	/**
	 * 获取静态资源url
	 * @return
	 */
	public static String getResourceUrl(){
        return prop.getProperty("resource.url.prefix");
	}
	/**
	 * 获取图片存放路径
	 * @return
	 */
	public static String getResImagePath(){
		String url=prop.getProperty("res.image.path");
		return url;
	}
	
	/**
	 * socket链接
	 * @return
	 */
	public static String getSocketUrl(){
		String url=prop.getProperty("socket.url");
		return url;
	}	
	/**
	 * 企业短信通-账户
	 * @return
	 */
	public static String getSmsUser(){
		String url=prop.getProperty("sms.user");
		return url;
	}
	
	/**
	 * 企业短信通-密码
	 * @return
	 */
	public static String getSmsPwd(){
		String url=prop.getProperty("sms.pwd");
		return url;
	}
	
	
	/**
	 * 企业短信通-密码
	 * @return
	 */
	public static String getFollowVisitUrl(){
		String url=prop.getProperty("follow.visit.url");
		return url;
	}
	/**
	 * 读取MQ队列的URL
	 * @return
	 */
	public static String getMqUrl(){
		String url=prop.getProperty("mq.uri");
		return url;
	}
	/**
	 * 读取MQ队列的用户名
	 * @return
	 */
	public static String getMqUser(){
		String url=prop.getProperty("mq.user");
		return url;
	}
	/**
	 * 读取MQ队列的密码
	 * @return
	 */
	public static String getMqPassWord(){
		String url=prop.getProperty("mq.password");
		return url;
	}
	/**
	 * 图片资源ftp ip
	 * @return
	 */
	public static String getStaticSourceFtpIp(){
		return prop.getProperty("static.source.ftp.ip");
	}
	
	
	/**
	 * 图片资源ftp 端口
	 * @return
	 */
	public static String getStaticSourceFtpPort(){
		return prop.getProperty("static.source.ftp.port");
	}
	

	/**
	 * 图片资源ftp 密码
	 * @return
	 */
	public static String getStaticSourceFtpPwd(){
		return prop.getProperty("static.source.ftp.password");
	}
	

	/**
	 * 图片资源ftp 用户
	 * @return
	 */
	public static String getStaticSourceFtpUser(){
		return prop.getProperty("static.source.ftp.user");
	}
	
	/**
	 * redis IP
	 * @return
	 */
	public static String getRedisIp(){
		return prop.getProperty("redis.ip");
	}
	/**
	 * redis 端口号
	 * @return
	 */
	public static Integer getRedisPort(){
		return Integer.valueOf(prop.getProperty("redis.port"));
	}
	/**
	 * redis pwd
	 * @return
	 */
	public static String getRedisPwd(){
		return prop.getProperty("redis.pwd");
	}
	
	/**
	 * 餐饮属性
	 * @return
	 */
	public static String getErpType(){
		return prop.getProperty("erp_type");
	}
	/**
	 * redis 前缀命名方法
	 * @return
	 */
	public static String redisNamePrefix(){
		return prop.getProperty("redisNamePrefix");
	}
	
	public  static void main(String arg[]){
		System.out.print(redisNamePrefix());
	}
	
}
