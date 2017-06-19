/**
 * @Description:
 * @author: wythelee
 * @date: 2015年7月27日 上午10:28:57
 */
package com.gt.common.filter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 拦截器方法
 * @author Administrator
 *
 */
public class LoginFilter implements Filter {

	private Logger logger=Logger.getLogger(LoginFilter.class);
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	//不需要登录就可访问的url
	public static final Map<String, String> urls=new HashMap<String, String>();
	//可通过的文件类型
	public static final List<String> suffixs=new ArrayList<String>();
	static{
		urls.put("/login/login.do", "/login/login.do");
		suffixs.add("js");
		suffixs.add("css");
		suffixs.add("gif");
		suffixs.add("png");
		suffixs.add("jpg");
		suffixs.add("ico");
		suffixs.add("html");
		suffixs.add("dwr");
		suffixs.add("mp3");
	}
	/**
	 * url访问时，所走的拦截方法
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse ServletResponse = (HttpServletResponse) response;
		/*String url = ((HttpServletRequest) request).getRequestURI();
		if(passSuffixs(url)||passUrl(url)){*/
			chain.doFilter(request, response);
		/*}else{
			Integer loginStyle = SessionUtil.getLoginStyle(servletRequest);
			if(CommonUtil.isEmpty(loginStyle)){
				ServletResponse.sendRedirect("/login/login.do");
			}
		}*/

	}
	/**
	 * 判断ajax请求
	 * @param request
	 * @return
	 */
	boolean isAjax(HttpServletRequest request){
		return  (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())   ) ;
	}

	@Override
	public void destroy() {

	}
	//判断是否是可通过的url
	private boolean passUrl(String url){
		return urls.containsKey(url);
	}

	private boolean passSuffixs(String url){
		boolean reuslt=false;
		for (int i = 0; i < suffixs.size(); i++) {
			if(url.endsWith(suffixs.get(i))){
				reuslt=true;
				break;
			}
		}
		return reuslt;
	}
}
