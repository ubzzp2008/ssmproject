package com.system.web.common.component.service.commonQuery.parser;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.system.web.common.component.module.query.ConditionFilter;
import com.system.web.common.component.module.query.ConditionRule;
import com.system.web.common.component.module.query.QueryCondition;
import com.system.web.common.component.module.query.QueryResult;
import com.system.web.common.component.module.query.QueryResultCount;
import com.system.web.common.component.module.query.QueryResultMap;
import com.system.web.common.component.module.query.UserDataRule;
import com.system.web.common.util.json.JSONException;

/**  
 * @Title: JQGridDataParser.java 
 * @Package com.yunhan.scc.trading.web.service.CommonQuery.parser 
 * TODO(用一句话描述该文件做什么) 
 * @author wt
 * @date 2015-6-15 上午11:37:59 
 * @version V0.1  
 */
public interface JQGridDataParser {

	 /**
     * 导出文件的条件解析
     * 
     * @param requestMap
     * @param condition
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws NoSuchFieldException 
     */
    public void ExportConditionParseRule(Map<String, String> requestMap, QueryCondition condition) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException;

    /**
     * 
     * 描述：条件处理
     * @author hanbin
     * @param requestMap
     * @param condition
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws NoSuchFieldException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public void JQGridConditionParseRule(Map<String, String> requestMap, QueryCondition condition) throws SecurityException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;

    /**
     * 利用反射查询QueryCondition中是否存在条件的setter方法.<br>
     * 如果有则通过反射将条件值填充进QueryCondition对象中
     * 
     * @param condition
     * @param filter
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public void dispersedCondition(QueryCondition condition, ConditionFilter filter) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;

    /**
     * 将条件对象转换成where可以识别的条件字符串
     * 
     * @param filter
     * @return
     * @throws NoSuchFieldException 
     * @throws SecurityException 
     */
    public String conditionRuleToConditionStr(QueryCondition condition, ConditionFilter filter) throws SecurityException, NoSuchFieldException;

    /**
     * 
     * 描述：创建单一条件
     * @author hanbin
     * @param rule
     * @param condition
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    public String createSingelCondition(ConditionRule rule, QueryCondition condition) throws SecurityException, NoSuchFieldException;

    /**
     * 
     * 描述：建立返回json
     * @author hanbin
     * @param queryResultCount
     * @param result
     * @throws JSONException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public void buildResponseJson(QueryResultCount queryResultCount, StringBuffer result) throws JSONException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;

    /**
     * 
     * 描述：解析用户数据
     * @author hanbin
     * @param userdata
     * @param userDataRule
     * @param obj
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
	public void parserUserData(Map<String, Object> userdata,
			UserDataRule userDataRule, QueryResult obj) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;

	/**
	 * 
	 * 描述：建立返回json
	 * 
	 * @author hanbin
	 * @param queryResultCount
	 * @param result
	 * @throws JSONException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void buildResponseJson(QueryResultMap queryResultCount,
			StringBuffer result) throws JSONException, SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException;

}
