package com.system.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.system.web.controller.base.BaseController;
import com.system.web.service.system.RepairService;

/** 
 * @Description   修复数据库
 * @ClassName: RepairController
 */
@Controller
@RequestMapping("/repairController")
public class RepairController extends BaseController {

	/**
	 * Logger for this class
	 */
	@Autowired
	private RepairService repairService;

	/** 
	 * @Description repair
	 */
	@RequestMapping("/repair")
	public ModelAndView repair() {
		repairService.repairDatabase();
		return new ModelAndView("login/login");
	}

}
