package com.gt.common.filter;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.springframework.web.context.ContextLoaderListener;
/**
 * 项目启动时，可以走的方法
 *
 */
@WebListener
public class InitListener extends ContextLoaderListener {
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
//		socke.socketConnet();//连接socket
	}
}
