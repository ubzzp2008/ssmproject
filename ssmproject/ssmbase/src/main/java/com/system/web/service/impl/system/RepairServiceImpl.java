package com.system.web.service.impl.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.web.common.util.PasswordUtil;
import com.system.web.dao.system.UserInfoDao;
import com.system.web.entity.system.TUserInfo;
import com.system.web.service.system.RepairService;

/** 
 * @Description 修复数据库Service
 * @ClassName: RepairService
 * @author tanghan  @Transactional
 * @date 2013-7-19 下午01:31:00
 */
@Service
public class RepairServiceImpl implements RepairService {

	@Autowired
	private UserInfoDao userInfoDao;

	/**
	* @Description 先清空数据库，然后再修复数据库
	* @author zzp 
	*/
	public void repairDatabase() { // 由于表中有主外键关系，清空数据库需注意
		repair();
	}

	/**
	* @Description 修复数据库
	* @author zzp 2013-7-19
	*/
	synchronized public void repair() {
		repairUser(); // 修复基本用户 
	}

	private void repairUser() {
		TUserInfo admin = new TUserInfo();
		admin.setStatus(0L);
		admin.setRealName("管理员");
		admin.setUserName("ADMIN");//用户名存入数据库时，统一转大写
		admin.setPassword(PasswordUtil.getMD5("123456"));
		userInfoDao.saveUserInfo(admin);
	}

}
