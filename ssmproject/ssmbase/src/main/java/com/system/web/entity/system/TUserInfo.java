package com.system.web.entity.system;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.system.web.common.component.module.query.QueryResult;


/**     
* 项目名称：sshbase   
* 类名称：TUserInfo   
* 类描述： 用户信息实体  
* 创建人：zzp
* 创建时间：2015-5-27 下午10:05:16   
* 修改人：
* 修改时间： 
* 修改备注：   
* @version V0.1 
*/
public class TUserInfo extends QueryResult {
	private static final long serialVersionUID = 6437363451413525694L;
	private Integer id;
	private String userName;// 用户名
	private String realName;// 真实姓名
	private String password;// 用户密码
	private Long status;// 状态0：启用 ,1：禁用
	private String phone;

	private List<Integer> roleIds;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Integer> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = new ArrayList<Integer>();
		if (StringUtils.isNotEmpty(roleIds)) {
			String[] list=roleIds.split(",");
			for (String str : list) {
				this.roleIds.add(Integer.parseInt(str));
			}
//			this.roleIds = Arrays.asList(roleIds.split(","));
		} 
	}

}