/**
 * 文 件 名：UserDataFilter.java
 * CopyRight (c) 2012 Anze, Inc. All rights reserved.
 * 创 建 人：lurf
 * 日      期：2012-2-1 下午4:46:22
 * 描      述：
 * 版 本 号：v3.0
 */
package com.system.web.common.component.module.query;

import java.util.List;

/**
 * 描述：生成表格最后一行小计，合计等
 * @author lurf
 * @date 2012-2-1 下午4:46:22
 * @version v3.0
 */
public class UserDataFilter {
	
	private String field;
	
	private String data;

	private List<UserDataRule> rules;
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public List<UserDataRule> getRules() {
		return rules;
	}

	public void setRules(List<UserDataRule> rules) {
		this.rules = rules;
	}
}
