package com.system.web.common.component.module.query;

import java.util.List;
import java.util.Map;

/**  
 * 查询结果的统计,包含请求参数,查询结果,以及统计字符串等额外的数据(以map方式返回)
 * @Title: QueryResultMap.java 
 * @Package com.yunhan.scc.trading.web.model.commonQuery 
 * @author wangtao
 * @date 2015-9-6 下午1:33:08 
 * @version V0.1  
 */
public class QueryResultMap {

	  /** 总数据条数 */
    private Long size;

    private QueryCondition condition;

    private List<Map<String, Object>> queryResultList;
    
    /** 该参数和queryResultList互斥,不能两者同时不为null */
    private Map<String,Object> queryResult;

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public QueryCondition getCondition() {
		return condition;
	}

	public void setCondition(QueryCondition condition) {
		this.condition = condition;
	}

	public List<Map<String, Object>> getQueryResultList() {
		return queryResultList;
	}

	public void setQueryResultList(List<Map<String, Object>> queryResultList) {
		this.queryResultList = queryResultList;
	}

	public Map<String, Object> getQueryResult() {
		return queryResult;
	}

	public void setQueryResult(Map<String, Object> queryResult) {
		this.queryResult = queryResult;
	}
    
    
}
