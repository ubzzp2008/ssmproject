/**
 * 文 件 名：QueryResultForObject.java
 * CopyRight (c) 2012 Anze, Inc. All rights reserved.
 * 创 建 人：lurf
 * 日      期：2012-4-10 下午1:21:20
 * 描      述：
 * 版 本 号：v3.0
 */
package com.system.web.common.component.module.query;

import java.util.List;

/**
 * 描述：通查返回结果集，用于特殊情况，如不用jqGrid，而直接在后台组装数据
 * @author lurf
 * @date 2012-4-10 下午1:21:20
 * @version v3.0
 */
public class QueryResultForObject {

	private Long total;
	
	private Integer page;
	
	private Long records;
	
	private Integer limit;
	
	private Integer offset;

	public List<QueryResult> rows;
	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Long getRecords() {
		return records;
	}

	public void setRecords(Long records) {
		this.records = records;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public List<QueryResult> getRows() {
		return rows;
	}

	public void setRows(List<QueryResult> rows) {
		this.rows = rows;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}
}
