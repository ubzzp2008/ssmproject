package com.system.web.dao.system;

import java.util.List;

import com.system.web.entity.system.TRole;

/**     
* 项目名称：ssmdemo   
* 类名称：RoleDao   
* 类描述：   
* 创建人：zzp
* 创建时间：2016-6-11 下午6:35:07   
* 修改人：
* 修改时间： 
* 修改备注：   
* @version V0.1 
*/

public interface RoleDao {
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
	 * @date 2016-6-11 下午7:20:51
	 */
	public List<TRole> getRoleInfoList();
}

