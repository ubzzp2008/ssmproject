package com.system.web.dao.system;

import java.util.List;

import com.system.web.entity.system.TUserRole;

/**     
* 项目名称：ssmdemo   
* 类名称：UserRoleDao   
* 类描述：用户与角色关系Dao   
* 创建人：zzp
* 创建时间：2016-6-11 下午7:49:54   
* 修改人：
* 修改时间： 
* 修改备注：   
* @version V0.1 
*/

public interface UserRoleDao {
	/**
	 * 批量保存用户与角色的关系
	 * @TODO
	 * @author justion.zhou
	 * @date 2016-6-11 下午8:00:20
	 */
	public void saveUserRoleBath(List<TUserRole> userRoles);

	/**
	 * 根据用户ID批量删除数据
	 * @TODO
	 * @param ids
	 * @author justion.zhou
	 * @date 2016-6-11 下午8:08:50
	 */
	public void deleteByUserId(List<Integer> ids);

	/**
	 * 根据角色ID删除数据
	 * @TODO
	 * @param ids
	 * @author justion.zhou
	 * @date 2016-6-11 下午8:12:21
	 */
	public void deleteByRoleId(List<Integer> ids);
}

