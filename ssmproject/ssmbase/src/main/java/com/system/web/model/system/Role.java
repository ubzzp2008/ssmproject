package com.system.web.model.system;

import com.system.web.common.component.module.query.QueryCondition;

/**     
* 项目名称：ssmdemo   
* 类名称：Role   
* 类描述：   
* 创建人：zzp
* 创建时间：2016-6-11 下午5:43:34   
* 修改人：
* 修改时间： 
* 修改备注：   
* @version V0.1 
*/

public class Role extends QueryCondition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5806476367127117110L;
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

