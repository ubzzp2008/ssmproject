package com.system.web.controller.base;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.system.web.common.util.json.Json;

public class BaseController {

	public void writeJsonSimple(Object object, HttpServletResponse response) {
		try {
			Json jsonObject = new Json();
			jsonObject.setSuccess(true);
			jsonObject.setObj(object);
			String json = JSON.toJSONStringWithDateFormat(jsonObject, "yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/html;charset=utf-8");
			// response.setContentType("application/json");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeJson(Object object, HttpServletResponse response) {
		try {
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/html;charset=utf-8");
			// response.setContentType("application/json");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
