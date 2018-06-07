package com.system.web.common.component.module.query;

import java.util.Date;

/**
 * 
 * 描述：通查结果集
 * @author hanbin
 * @date 2012-3-29 下午02:37:09
 * @version v3.0
 */
public class QueryResult implements QueryObjectInterface {

	/** 
	 * 
	 */
	private static final long serialVersionUID = 3005185116466328747L;

	private Date addDate;
	private String addUser;
	private Date updateDate;
	private String updateUser;

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getAddUser() {
		return addUser;
	}

	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}
