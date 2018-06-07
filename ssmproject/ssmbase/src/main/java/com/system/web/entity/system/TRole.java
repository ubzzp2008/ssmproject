package com.system.web.entity.system;

import com.system.web.common.component.module.query.QueryResult;

/**
 * 
*
* 类名称：TRole   
* 类描述：   角色类
* 创建人：lhd  
* 创建时间：2015-5-27 上午10:06:35   
* 修改人：
* 修改时间： 
* 修改备注：   
* @version V0.1 
*
 */
public class TRole extends QueryResult {
	/**
	 * 
	 */
	private static final long serialVersionUID = -921742227232604652L;
	private Integer id;
	private String roleName;// 角色名
	private String roleDesc;// 角色描述
	private Long status;// 状态：0 启用; 1：禁用

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	

}