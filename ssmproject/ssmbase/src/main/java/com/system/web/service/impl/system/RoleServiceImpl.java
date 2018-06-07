package com.system.web.service.impl.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.web.dao.system.RoleDao;
import com.system.web.dao.system.UserRoleDao;
import com.system.web.entity.system.TRole;
import com.system.web.service.system.RoleService;


@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserRoleDao userRoleDao;

	@Override
	public void saveRoleInfo(TRole role) {
		roleDao.saveRoleInfo(role);
	}

	@Override
	public TRole getRoleInfoById(Integer id) {
		return roleDao.getRoleInfoById(id);
	}

	@Override
	public void updateRoleInfo(TRole role) {
		roleDao.updateRoleInfo(role);
	}

	@Override
	public void deleteRoleInfoByIds(List<Integer> ids) {
		//根据角色ID删除角色信息
		roleDao.deleteRoleInfoByIds(ids);
		//根据角色ID删除用户与角色关系表数据
		userRoleDao.deleteByRoleId(ids);
		//根据角色ID删除菜单与角色关系表数据
	}

	@Override
	public List<TRole> getRoleInfoList() {
		return roleDao.getRoleInfoList();
	}

}
