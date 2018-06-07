package com.system.web.service.impl.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.web.common.util.PasswordUtil;
import com.system.web.dao.system.UserInfoDao;
import com.system.web.dao.system.UserRoleDao;
import com.system.web.entity.system.TUserInfo;
import com.system.web.entity.system.TUserRole;
import com.system.web.service.system.UserInfoService;

/** 
 * 项目名称：sshbase 类名称：UserInfoServiceImpl 类描述： 创建人：zzp 创建时间：2015-4-10 下午3:45:58
 * 修改人： 修改时间： 修改备注：
 * 
 * @version V0.1
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private UserRoleDao userRoleDao;

	@Override
	public TUserInfo checkUserExits(String userName, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("password", PasswordUtil.getMD5(password));
		return userInfoDao.checkUserExits(map);
	}

	@Override
	public Long getCountUserInfo() {
		return userInfoDao.getCountUserInfo();
	}

	@Override
	public void saveUserInfo(TUserInfo userInfo) {
		userInfo.setPassword(PasswordUtil.getMD5(userInfo.getPassword()));
		//保存用户基本信息
		userInfoDao.saveUserInfo(userInfo);
		//保存用户与角色的关系
		if (CollectionUtils.isNotEmpty(userInfo.getRoleIds())) {
			List<TUserRole> userRoles = new ArrayList<TUserRole>();
			for (Integer roleId : userInfo.getRoleIds()) {
				TUserRole userRole=new TUserRole();
				userRole.setRoleId(roleId);
				userRole.setUserId(userInfo.getId());
				userRoles.add(userRole);
			}
			userRoleDao.saveUserRoleBath(userRoles);
		}
	}

	@Override
	public void deleteUserInfoByIds(List<Integer> ids) {
		//根据用户ID删除用户数据
		userInfoDao.deleteUserInfoByIds(ids);
		//根据用户ID删除用户与角色的关系表数据
		userRoleDao.deleteByUserId(ids);
	}

	@Override
	public TUserInfo getUserInfoById(Integer id) {
		return userInfoDao.getUserInfoById(id);
	}

	@Override
	public void updateUserInfo(TUserInfo userInfo) {
		userInfoDao.updateUserInfo(userInfo);
	}

}
