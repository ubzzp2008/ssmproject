package com.system.web.model.system;

import com.system.web.common.component.module.query.QueryCondition;

/**      
* 项目名称：ssmdemo   
* 类名称：UserInfo   
* 类描述：用户信息model   
* 创建人：zzp
* 创建时间：2016-5-20 下午3:03:13   
* 修改人：
* 修改时间： 
* 修改备注：   
* @version V0.1 
*/

public class UserInfo extends QueryCondition {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1767789998250193036L;
	private Integer id;
	private String userName;// 用户名
	private String realName;// 真实姓名
	private String password;// 用户密码
	private Long status;// 状态1：启用 ,0：禁用
	private String phone;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}

