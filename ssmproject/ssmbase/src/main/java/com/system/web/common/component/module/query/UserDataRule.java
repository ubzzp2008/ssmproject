/**
 * 文 件 名：UserDataRule.java
 * CopyRight (c) 2012 Anze, Inc. All rights reserved.
 * 创 建 人：lurf
 * 日      期：2012-2-1 下午4:47:30
 * 描      述：
 * 版 本 号：v3.0
 */
package com.system.web.common.component.module.query;

/**
 * 描述：生成表格最后一行小计，合计等
 * @author lurf
 * @date 2012-2-1 下午4:47:30
 * @version v3.0
 */
public class UserDataRule {

	private String field;
	
	private String op;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}
}
