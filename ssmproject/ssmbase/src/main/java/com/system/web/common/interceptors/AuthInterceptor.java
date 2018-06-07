package com.system.web.common.interceptors;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.system.web.common.util.Client;
import com.system.web.common.util.ClientManager;
import com.system.web.common.util.ContextHolderUtils;

/**
 * 权限拦截器
 * 
 */
public class AuthInterceptor implements HandlerInterceptor {

	private List<String> excludeUrls;

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	/**
	 * 在controller后拦截
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception)
			throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView)
			throws Exception {
	}

	/**
	 * 在controller前拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		//		String requestPath = SessionUser.getRequestPath(request);// 用户访问的资源地址
		//		System.out.println(requestPath);
		//字符集拦截
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//        response.setContentType("text/html;charset=UTF-8"); 

		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		if (client == null) {
			client = ClientManager.getInstance().getClient(request.getParameter("sessionId"));
		}
		if (client != null && client.getUser() != null) {
			return true;
		} else {
			response.sendRedirect(request.getContextPath() + "/");//登陆失效，跳转登陆页面
			return false;
		}
	}

}
