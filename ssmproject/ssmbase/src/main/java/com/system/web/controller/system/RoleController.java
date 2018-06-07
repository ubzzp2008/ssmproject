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
import com.system.web.entity.system.TRole;
import com.system.web.service.system.RoleService;

/**
 * 项目名称：sshbase 
 * 类名称：RoleController 
 * 类描述： 
 * 创建人：zzp 
 * 创建时间：2015-4-10 下午3:44:54
 * 修改人： 
 * 修改时间： 
 * 修改备注：
 *  
 * @version V0.1
 */
@Controller
@RequestMapping("/roleController")
public class RoleController extends BaseController {
	private static Log log = LogFactory.getLog(RoleController.class);
	
	@Autowired
	private RoleService roleService;

	/**
	 * 进入角色管理页面
	 * @TODO
	 * @param request
	 * @param response
	 * @return
	 * @author justion.zhou
	 * @date 2016-6-11 下午5:49:15
	 */
	@RequestMapping("/page/roleList")
	public ModelAndView toRoleList(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("system/roleList");
	}

	/**
	 * 保存角色信息
	 * @TODO
	 * @param request
	 * @param response
	 * @author justion.zhou
	 * @date 2016-6-1 下午3:56:24
	 */
	@RequestMapping("/json/saveRoleInfo")
	@ResponseBody
	public void saveRoleInfo(HttpServletRequest request, HttpServletResponse response) {
		Json json = new Json();
		try {
			String userName = SessionUser.getUserName();
			String dataObj = request.getParameter("dataObj");
			TRole role = JSONObject.parseObject(dataObj, TRole.class);
			role.setAddUser(userName);
			role.setAddDate(new Date());
			roleService.saveRoleInfo(role);
			json.setSuccess(true);
			json.setMsg("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("保存角色失败", e);
			json.setSuccess(false);
			json.setMsg("保存角色失败");
		}
		writeJson(json, response);
	}

	/**
	 * 根据ID获取角色信息
	 * @TODO
	 * @param request
	 * @param response
	 * @author justion.zhou
	 * @date 2016-6-1 下午10:09:57
	 */
	@RequestMapping("/json/getRoleInfoById")
	@ResponseBody
	public void getRoleInfoById(HttpServletRequest request, HttpServletResponse response) {
		Json json = new Json();
		try {
			String param = request.getParameter("id");
			Integer id = StringUtils.isEmpty(param) ? 0 : Integer.parseInt(param);
			TRole role = roleService.getRoleInfoById(id);
			json.setSuccess(true);
			json.setObj(role);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("根据ID获取角色信息失败", e);
			json.setSuccess(false);
			json.setMsg("获取角色信息异常");
		}
		writeJson(json, response);
	}

	/**
	 * 修改角色信息
	 * @TODO
	 * @param request
	 * @param response
	 * @author justion.zhou
	 * @date 2016-5-22 下午7:19:45
	 */
	@RequestMapping("/json/updateRoleInfo")
	@ResponseBody
	public void updateRoleInfo(HttpServletRequest request, HttpServletResponse response) {
		Json json = new Json();
		try {
			String userName = SessionUser.getUserName();
			String dataObj = request.getParameter("dataObj");
			TRole role = JSONObject.parseObject(dataObj, TRole.class);
			role.setUpdateUser(userName);
			role.setUpdateDate(new Date());
			roleService.updateRoleInfo(role);
			json.setSuccess(true);
			json.setMsg("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改角色保存失败", e);
			json.setSuccess(false);
			json.setMsg("修改角色保存失败");
		}
		writeJson(json, response);
	}

	/**
	 * 根据主键ID删除数据
	 * @TODO
	 * @param request
	 * @param response
	 * @author justion.zhou
	 * @date 2016-6-1 下午10:11:27
	 */
	@RequestMapping("/json/deleteRoleInfo")
	@ResponseBody
	public void deleteRoleInfo(HttpServletRequest request, HttpServletResponse response) {
		Json json = new Json();
		try {
			String param = request.getParameter("ids");
			List<Integer> ids = JSONArray.parseArray(param, Integer.class);
			roleService.deleteRoleInfoByIds(ids);
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除角色失败", e);
			json.setSuccess(false);
			json.setMsg("删除角色失败");
		}
		writeJson(json, response);
	}

	/**
	 * 获取角色集合
	 * @TODO
	 * @param request
	 * @param response
	 * @author justion.zhou
	 * @date 2016-6-11 下午7:19:16
	 */
	@RequestMapping("/json/getRoleInfoList")
	@ResponseBody
	public void getRoleInfoList(HttpServletRequest request, HttpServletResponse response) {
		Json json = new Json();
		try {
			List<TRole> roleList = roleService.getRoleInfoList();
			json.setSuccess(true);
			json.setObj(roleList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("获取角色信息失败", e);
			json.setSuccess(false);
			json.setMsg("获取角色信息异常");
		}
		writeJson(json, response);
	}

}
