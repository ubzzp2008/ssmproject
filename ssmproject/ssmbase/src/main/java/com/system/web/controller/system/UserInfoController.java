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
import com.system.web.entity.system.TUserInfo;
import com.system.web.service.system.UserInfoService;

/**
 * 项目名称：sshbase 类名称：UserInfoController 类描述： 创建人：zzp 创建时间：2015-4-10 下午3:44:54
 * 修改人： 修改时间： 修改备注：
 *  
 * @version V0.1
 */
@Controller
@RequestMapping("/userInfoController")
public class UserInfoController extends BaseController {
	private static Log log = LogFactory.getLog(UserInfoController.class);
	
	@Autowired
	private UserInfoService userInfoService;

	/**
	 * @Description: TODO(跳转到用户列表)
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return ModelAndView
	 * @throws
	 * @author swb
	 * @date 2015-5-26
	 */
	@RequestMapping("/page/userInfoList")
	public ModelAndView userInfoList(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("system/userInfoList");
	}

	/**
	 * 保存用户信息 ，注意在保存时，sql上将用户名转大写进行存储
	 * @TODO
	 * @param request
	 * @param response
	 * @author justion.zhou
	 * @date 2016-5-21 下午7:14:00
	 */
	@RequestMapping("/json/saveUserInfo")
	@ResponseBody
	public void saveUserInfo(HttpServletRequest request, HttpServletResponse response) {
		Json json = new Json();
		try {
			String userName = SessionUser.getUserName();
			String dataObj = request.getParameter("dataObj");
			TUserInfo userInfo = JSONObject.parseObject(dataObj, TUserInfo.class);
			userInfo.setUserName(userInfo.getUserName().trim());//用户名进行去空操作
			userInfo.setAddUser(userName);
			userInfo.setAddDate(new Date());
			userInfoService.saveUserInfo(userInfo);
			json.setSuccess(true);
			json.setMsg("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("保存用户失败", e);
			json.setSuccess(false);
			json.setMsg("保存用户失败");
		}
		writeJson(json, response);
	}
	
	/**
	 * 根据ID获取用户信息
	 * @TODO
	 * @param request
	 * @param response
	 * @author justion.zhou
	 * @date 2016-5-22 下午4:40:08
	 */
	@RequestMapping("/json/getUserInfoById")
	@ResponseBody
	public void getUserInfoById(HttpServletRequest request, HttpServletResponse response) {
		Json json = new Json();
		try {
			String param = request.getParameter("id");
			Integer id = StringUtils.isEmpty(param) ? 0 : Integer.parseInt(param);
			TUserInfo userInfo = userInfoService.getUserInfoById(id);
			json.setSuccess(true);
			json.setObj(userInfo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("根据ID获取用户信息失败", e);
			json.setSuccess(false);
			json.setMsg("获取用户信息异常");
		}
		writeJson(json, response);
	}

	/**
	 * 修改用户信息
	 * @TODO
	 * @param request
	 * @param response
	 * @author justion.zhou
	 * @date 2016-5-22 下午7:19:45
	 */
	@RequestMapping("/json/updateUserInfo")
	@ResponseBody
	public void updateUserInfo(HttpServletRequest request, HttpServletResponse response) {
		Json json = new Json();
		try {
			String userName = SessionUser.getUserName();
			String dataObj = request.getParameter("dataObj");
			TUserInfo userInfo = JSONObject.parseObject(dataObj, TUserInfo.class);
			userInfo.setUpdateUser(userName);
			userInfo.setUpdateDate(new Date());
			userInfoService.updateUserInfo(userInfo);
			json.setSuccess(true);
			json.setMsg("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改用户保存失败", e);
			json.setSuccess(false);
			json.setMsg("修改用户保存失败");
		}
		writeJson(json, response);
	}

	/**
	 * 删除用户
	 * @TODO
	 * @param request
	 * @param response
	 * @author justion.zhou
	 * @date 2016-5-22 下午4:40:08
	 */
	@RequestMapping("/json/deleteUserInfo")
	@ResponseBody
	public void deleteUserInfo(HttpServletRequest request, HttpServletResponse response) {
		Json json = new Json();
		try {
			String param = request.getParameter("ids");
			List<Integer> ids = JSONArray.parseArray(param, Integer.class);
			userInfoService.deleteUserInfoByIds(ids);
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除用户失败", e);
			json.setSuccess(false);
			json.setMsg("删除用户失败");
		}
		writeJson(json, response);
	}

}
