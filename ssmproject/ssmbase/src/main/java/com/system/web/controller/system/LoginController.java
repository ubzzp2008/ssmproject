package com.system.web.controller.system;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.system.web.common.util.Client;
import com.system.web.common.util.ClientManager;
import com.system.web.common.util.ContextHolderUtils;
import com.system.web.common.util.IpUtil;
import com.system.web.common.util.SessionUser;
import com.system.web.common.util.json.Json;
import com.system.web.controller.base.BaseController;
import com.system.web.entity.system.TUserInfo;
import com.system.web.service.system.UserInfoService;

/**      
* 项目名称：sshbase   
* 类名称：LoginController   
* 类描述：   
* 创建人：zzp
* 创建时间：2015-4-10 下午4:26:33   
* 修改人：
* 修改时间： 
* 修改备注：   
* @version V0.1 
*/
@Controller
public class LoginController extends BaseController {
	@Autowired
	private UserInfoService userInfoService;

	@RequestMapping(value = { "/", "/loginController/login" })
	public ModelAndView login(HttpServletRequest request) {
		TUserInfo user = SessionUser.getSessionUser();
		if (user != null) {
			request.setAttribute("userName", user.getUserName());
			request.setAttribute("realName", user.getRealName());
			return new ModelAndView("main/mainPage");
		} else {
			return new ModelAndView("login/login");
		}
	}

	@RequestMapping("/loginController/checkuser")
	@ResponseBody
	public void checkuser(TUserInfo user, HttpServletRequest req, HttpServletResponse response) {
		Json json = new Json();
		HttpSession session = ContextHolderUtils.getSession();
		// 添加验证码
		String randCode = req.getParameter("randCode");
		if (StringUtils.isEmpty(randCode)) {
			json.setMsg("请输入验证码");
			json.setSuccess(false);
		} else if (!randCode.equalsIgnoreCase(String.valueOf(session.getAttribute("randCode")))) {
			// "randCode"和验证码servlet中该变量一样，通过统一的系统常量配置比较好，暂时不知道系统常量放在什么地方合适
			json.setMsg("验证码错误！");
			json.setSuccess(false);
		} else {
			//获取用户数，如果为0 ，则需要进行数据初始化
			Long count = userInfoService.getCountUserInfo();
			if (count == 0) {
				json.setMsg("a");
				json.setSuccess(false);
			} else {
				TUserInfo u = userInfoService.checkUserExits(user.getUserName(), user.getPassword());
				if (u != null) {
					Client client = new Client();
					client.setIp(IpUtil.getIpAddr(req));
					client.setLogindatetime(new Date());
					client.setUser(u);
					ClientManager.getInstance().addClinet(session.getId(), client);
				} else {
					json.setMsg("用户名或密码错误!");
					json.setSuccess(false);
				}
			}
		}
		writeJson(json, response);
	}

	/**
	 * 退出系统
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		ClientManager.getInstance().removeClinet(session.getId());
		ModelAndView modelAndView = new ModelAndView("redirect:/");
		return modelAndView;
	}

	/**
	 * 无权限页面提示跳转
	 * 
	 * @return
	 */
	@RequestMapping("/noAuth")
	public ModelAndView noAuth(HttpServletRequest request) {
		return new ModelAndView("login/login");
	}

	/**
	 * 获取session中的用户，返回到静态页面
	 * @TODO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @author justion.zhou
	 * @date 2016-5-23 上午9:45:27
	 */
	@RequestMapping("/page/userInfo")
	public ModelAndView commonUserInfo(Model model, HttpServletRequest request, HttpServletResponse response) {
		TUserInfo user = SessionUser.getSessionUser();
		model.addAttribute("user", user);
		return new ModelAndView("/common/userinfo");
	}

}
