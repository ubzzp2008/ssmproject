package com.system.web.entity.system;

import com.system.web.common.component.module.query.QueryResult;

/**     
* 项目名称：ssmdemo   
* 类名称：TUserRole   
* 类描述：用户与角色关系实体   
* 创建人：zzp
* 创建时间：2016-6-11 下午7:51:04   
* 修改人：
* 修改时间： 
* 修改备注：   
* @version V0.1 
*/

public class TUserRole extends QueryResult {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8692140948950121450L;
	/**
	 * 主键ID
	 */
	private Integer id;
	/**
	 * 用户表主键ID
	 */
	private Integer userId;
	/**
	 * 角色表主键ID
	 */
	private Integer roleId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}

