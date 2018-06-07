package com.system.web.common.component.module.query;

import java.util.List;

/**
 * 查询结果的统计,包含请求参数,查询结果,以及统计字符串等额外的数据
 * 
 * @author lxxccc
 * 
 */
public class QueryResultCount {
    /** 总数据条数 */
    private Long size;

    private QueryCondition condition;

    private List<QueryResult> queryResultList;

    /** 该参数和queryResultList互斥,不能两者同时不为null */
    private QueryResult queryResult;

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

    public List<QueryResult> getQueryResultList() {
        return queryResultList;
    }

    public void setQueryResultList(List<QueryResult> queryResultList) {
        this.queryResultList = queryResultList;
    }

    public QueryResult getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(QueryResult queryResult) {
        this.queryResult = queryResult;
    }

}
