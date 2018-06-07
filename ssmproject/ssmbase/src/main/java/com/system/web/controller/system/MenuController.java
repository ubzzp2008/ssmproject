package com.system.web.controller.system;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.system.web.common.util.SessionUser;
import com.system.web.common.util.json.Json;
import com.system.web.controller.base.BaseController;
import com.system.web.entity.system.TMenu;
import com.system.web.model.system.Menu;
import com.system.web.service.system.MenuService;

/**
 * 项目名称：sshbase 类名称：MenuController 类描述：菜单Controller 创建人：zzp 创建时间：2015-5-27
 * 下午10:09:59 修改人： 修改时间： 修改备注：
 * 
 * @version V0.1
 */
@Controller
@RequestMapping("/menuController")
public class MenuController extends BaseController {
	private static Log log = LogFactory.getLog(MenuController.class);

	@Autowired
	private MenuService menuService;

	/**
	 * 进入菜单管理页面
	 * 
	 * @TODO
	 * @param request
	 * @param response
	 * @return
	 * @author justion.zhou
	 * @date 2016-6-1 下午2:49:44
	 */
	@RequestMapping("/page/menuList")
	public ModelAndView menuList(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("system/menuList");
	}

	/**
	 * 根据菜单层级获取菜单
	 * 
	 * @TODO
	 * @param request
	 * @param response
	 * @author justion.zhou
	 * @date 2016-6-1 下午11:17:30
	 */
	@RequestMapping("/json/getMenuByLevele")
	@ResponseBody
	public void getMenuByLevele(HttpServletRequest request,
			HttpServletResponse response) {
		Json json = new Json();
		try {
			String param = request.getParameter("levele");
			Integer levele = StringUtils.isEmpty(param) ? 0 : Integer
					.parseInt(param);
			List<TMenu> menus = menuService.getMenuByLevele(levele);
			json.setSuccess(true);
			json.setObj(menus);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("根据菜单层级获取菜单失败", e);
			json.setSuccess(false);
			json.setMsg("获取父级菜单失败");
		}
		writeJson(json, response);
	}

	/**
	 * 保存菜单
	 * 
	 * @TODO
	 * @param request
	 * @param response
	 * @author justion.zhou
	 * @date 2016-6-1 下午3:56:24
	 */
	@RequestMapping("/json/saveMenuInfo")
	@ResponseBody
	public void saveMenuInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Json json = new Json();
		try {
			String userName = SessionUser.getUserName();
			String dataObj = request.getParameter("dataObj");
			TMenu menu = JSONObject.parseObject(dataObj, TMenu.class);
			if (null == menu.getPid()) {
				menu.setPid(0);
			}
			menu.setAddUser(userName);
			menu.setAddDate(new Date());
			menuService.saveMenuInfo(menu);
			json.setSuccess(true);
			json.setMsg("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("保存菜单失败", e);
			json.setSuccess(false);
			json.setMsg("保存菜单失败");
		}
		writeJson(json, response);
	}

	/**
	 * 根据ID获取菜单信息
	 * 
	 * @TODO
	 * @param request
	 * @param response
	 * @author justion.zhou
	 * @date 2016-6-1 下午10:09:57
	 */
	@RequestMapping("/json/getMenuInfoById")
	@ResponseBody
	public void getMenuInfoById(HttpServletRequest request,
			HttpServletResponse response) {
		Json json = new Json();
		try {
			String param = request.getParameter("id");
			Integer id = StringUtils.isEmpty(param) ? 0 : Integer
					.parseInt(param);
			TMenu menu = menuService.getMenuInfoById(id);
			json.setSuccess(true);
			json.setObj(menu);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("根据ID获取菜单信息失败", e);
			json.setSuccess(false);
			json.setMsg("获取菜单信息异常");
		}
		writeJson(json, response);
	}

	/**
	 * 修改用户信息
	 * 
	 * @TODO
	 * @param request
	 * @param response
	 * @author justion.zhou
	 * @date 2016-5-22 下午7:19:45
	 */
	@RequestMapping("/json/updateMenuInfo")
	@ResponseBody
	public void updateMenuInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Json json = new Json();
		try {
			String userName = SessionUser.getUserName();
			String dataObj = request.getParameter("dataObj");
			TMenu menu = JSONObject.parseObject(dataObj, TMenu.class);
			menu.setUpdateUser(userName);
			menu.setUpdateDate(new Date());
			menuService.updateMenuInfo(menu);
			json.setSuccess(true);
			json.setMsg("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改菜单保存失败", e);
			json.setSuccess(false);
			json.setMsg("修改菜单保存失败");
		}
		writeJson(json, response);
	}

	/**
	 * 根据主键ID删除数据
	 * 
	 * @TODO
	 * @param request
	 * @param response
	 * @author justion.zhou
	 * @date 2016-6-1 下午10:11:27
	 */
	@RequestMapping("/json/deleteMenuInfo")
	@ResponseBody
	public void deleteMenuInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Json json = new Json();
		try {
			String param = request.getParameter("ids");
			List<Integer> ids = JSONArray.parseArray(param, Integer.class);
			menuService.deleteMenuInfoByIds(ids);
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除菜单失败", e);
			json.setSuccess(false);
			json.setMsg("删除菜单失败");
		}
		writeJson(json, response);
	}

	@RequestMapping("/json/findMenuByUserId")
	@ResponseBody
	public void findMenuByUserId(HttpServletRequest request,
			HttpServletResponse response) {
		Json json = new Json();
		try {
			String param = request.getParameter("userId");
			if (StringUtils.isEmpty(param)) {
				json.setMsg("登录超时，请重新登录");
				json.setSuccess(false);
			} else {
				Integer userId = Integer.parseInt(param);
				List<Menu> menuList = menuService.findMenuByUserId(userId);
				json.setObj(menuList);
				json.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取菜单异常", e);
			json.setSuccess(false);
			json.setMsg("获取菜单异常");
		}
		writeJson(json, response);
	}

}
