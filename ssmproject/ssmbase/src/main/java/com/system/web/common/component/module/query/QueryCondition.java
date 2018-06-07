package com.system.web.common.component.module.query;

/**
 * 查询条件接口,所有使用通查的作为条件的对象都必须继承该类
 * 
 * 查询需要以下参数:<br>
 * <ol>
 * <li>offset - 查询开始的条数</li>
 * <li>limit - 查询结束的条数</li>
 * <li>page - 需要查询的页数</li>
 * <li>rows - 需要查询的条数</li>
 * <li>sortField - 需要排序的字段</li>
 * <li>sortRule - 排序方式</li>
 * </ol>
 * 
 * 
 * @author lxxccc
 */
public abstract class QueryCondition implements QueryObjectInterface {

    private String queryID;

    private String queryType;

    private String queryEfficiency;

    private Integer offset;

    private Integer limit;

    private Integer page;

    private Integer rows;

    private String sortField;

    private String sortRule;

    /** 条件字符串 */
    private String conditionStr;
    
    /**
     * 查询记录数
     */
    private Integer rownum;
    
    private UserDataFilter userDataFilter;
    
	/**
	 * 日期查询开始值
	 */
	private String queryStartDate;
	/**
	 * 日期查询结束值
	 */
	private String queryEndDate;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortRule() {
        return sortRule;
    }

    public void setSortRule(String sortRule) {
        this.sortRule = sortRule;
    }

    public String getQueryID() {
        return queryID;
    }

    public void setQueryID(String queryID) {
        this.queryID = queryID;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getQueryEfficiency() {
        return queryEfficiency;
    }

    public void setQueryEfficiency(String queryEfficiency) {
        this.queryEfficiency = queryEfficiency;
    }

    public String getConditonStr() {
        return conditionStr;
    }

    public void setConditonStr(String conditonStr) {
        this.conditionStr = conditonStr;
    }

	public Integer getRownum() {
		return rownum;
	}

	public void setRownum(Integer rownum) {
		this.rownum = rownum;
	}

	public UserDataFilter getUserDataFilter() {
		return userDataFilter;
	}

	public void setUserDataFilter(UserDataFilter userDataFilter) {
		this.userDataFilter = userDataFilter;
	}

	public String getQueryStartDate() {
		return queryStartDate;
	}

	public void setQueryStartDate(String queryStartDate) {
		this.queryStartDate = queryStartDate;
	}

	public String getQueryEndDate() {
		return queryEndDate;
	}

	public void setQueryEndDate(String queryEndDate) {
		this.queryEndDate = queryEndDate;
	}

}
