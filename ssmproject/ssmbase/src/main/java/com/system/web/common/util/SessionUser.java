package com.system.web.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.system.web.entity.system.TUserInfo;

/**
 * 项目参数工具类
 * 
 */
public class SessionUser {

	//	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("sysConfig");

	/**
	 * 获取session定义名称
	 * 
	 * @return
	 */
	//		public static final String getSessionattachmenttitle(String sessionName) {
	//			return bundle.getString(sessionName);
	//		}

	public static final TUserInfo getSessionUser() {
		HttpSession session = ContextHolderUtils.getSession();
		if (ClientManager.getInstance().getClient(session.getId()) != null) {
			return ClientManager.getInstance().getClient(session.getId()).getUser();
		}
		return null;
	}

	public static final String getUserName() {
		TUserInfo userInfo = getSessionUser();
		if (userInfo != null) {
			return userInfo.getUserName();
		}
		return "";
	}

	/**
	 * 获得请求路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestPath(HttpServletRequest request) {
		//		String requestPath = request.getRequestURI() + "?" + request.getQueryString();
		//		if (requestPath.indexOf("&") > -1) {// 去掉其他参数
		//			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
		//		}
		String requestPath = request.getRequestURI();
		if (requestPath.indexOf("?") > -1) {// 去掉其他参数
			requestPath = requestPath.substring(0, requestPath.indexOf("?"));
		}
		requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
		return requestPath;
	}

	/**
	 * 获取配置文件参数
	 * 
	 * @param name
	 * @return
	 */
	//	public static final String getConfigByName(String name) {
	//		return bundle.getString(name);
	//	}

	public static String getParameter(String field) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		return request.getParameter(field);
	}

	/**
	 * 获取随机码的长度
	 *
	 * @return 随机码的长度
	 */
	//	public static String getRandCodeLength() {
	//		return bundle.getString("randCodeLength");
	//	}

	/**
	 * 获取随机码的类型
	 *
	 * @return 随机码的类型
	 */
	//	public static String getRandCodeType() {
	//		return bundle.getString("randCodeType");
	//	}

}
