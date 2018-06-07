package com.system.web.service.system;

import java.util.List;

import com.system.web.entity.system.TRole;


/** 
 * 
*
* 类名称：IRoleService   
* 类描述：   
* 创建人：lhd  
* 创建时间：2015-5-27 上午10:19:36   
* 修改人：
* 修改时间： 
* 修改备注：   
* @version V0.1 
*
 */
public interface RoleService {
	/**
	 * 保存角色信息
	 * @TODO
	 * @param menu
	 * @author justion.zhou
	 * @date 2016-6-11 下午6:31:10
	 */
	public void saveRoleInfo(TRole role);

	/**
	 * 根据ID获取菜单信息
	 * @TODO
	 * @param id
	 * @return
	 * @author justion.zhou
	 * @date 2016-6-11 下午6:31:48
	 */
	public TRole getRoleInfoById(Integer id);

	/**
	 * 修改角色信息
	 * @TODO
	 * @param menu
	 * @author justion.zhou
	 * @date 2016-6-11 下午6:32:13
	 */
	public void updateRoleInfo(TRole role);

	/**
	 * 根据Id删除菜单信息
	 * @TODO
	 * @param ids
	 * @author justion.zhou
	 * @date 2016-6-11 下午6:32:42
	 */
	public void deleteRoleInfoByIds(List<Integer> ids);

	/**
	 * 获取角色集合
	 * @TODO
	 * @return
	 * @author justion.zhou
	 * @date 2016-6-11 下午7:20:13
	 */
	public List<TRole> getRoleInfoList();
}
