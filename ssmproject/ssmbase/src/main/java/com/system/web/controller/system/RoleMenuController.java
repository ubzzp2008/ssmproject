package com.system.web.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.system.web.controller.base.BaseController;

/**     
* 项目名称：ssmdemo   
* 类名称：RoleAuthorityController   
* 类描述：为角色进行功能菜单授权
* 创建人：zzp
* 创建时间：2016-6-21 下午2:44:47   
* 修改人：
* 修改时间： 
* 修改备注：   
* @version V0.1 
*/
@Controller
@RequestMapping("/roleMenuController")
public class RoleMenuController  extends BaseController {
	private static Log log = LogFactory.getLog(RoleMenuController.class);

	/**
	 * 进入角色菜单配置页面
	 * @TODO
	 * @param request
	 * @param response
	 * @return
	 * @author justion.zhou
	 * @date 2016-6-21 下午2:56:09
	 */
	@RequestMapping("/page/roleMenuList")
	public ModelAndView toRoleMenuList(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("system/roleMenuList");
	}
}

