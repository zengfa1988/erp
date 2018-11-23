package com.niuxing.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zengfa.platform.util.security.UserInfo;
import com.zengfa.platform.web.userinfo.LoginUtil;
import com.zengfa.platform.web.userinfo.SessionKey;

public class UserinfoFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String[] notFilter = new String[] { "login.html","login.do" };
		String uri = request.getRequestURI();
		boolean doFilter = true;
		for (String s : notFilter) {
			if (uri.indexOf(s) != -1) {
				doFilter = false;
				break;
			}
		}
		if(uri.indexOf("/static/")>0){
			doFilter = false;
		}
		if (doFilter) {
			boolean isLogin = false;
			String 	  token = request.getParameter("token");//屏端token
			if(StringUtils.isBlank(token)){
				token = LoginUtil.checkLoginByCookie(request, SessionKey.ZHC_COOKIE_USER_NAME);//web端token
			}
			
			if(StringUtils.isBlank(token)){
				isLogin = false;
			}
			
			UserInfo loginInfo = (UserInfo) request.getSession().getAttribute(token);
			
//			UserInfo loginInfo = (UserInfo) SerializeUtil.unserialize(RedisSlave.getInstance().get(token));
			
			if(null != loginInfo){
				isLogin = true;
			}
			if(isLogin){
				filterChain.doFilter(request, response);
			}else{
				String login_url="login.html";
				response.sendRedirect(login_url);
			}
		}else{
			filterChain.doFilter(request, response);
		}
	}


}
