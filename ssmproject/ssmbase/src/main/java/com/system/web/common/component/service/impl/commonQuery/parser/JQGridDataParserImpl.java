package com.system.web.common.component.service.impl.commonQuery.parser;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.system.web.common.component.dao.base.CommonQueryPersistence;
import com.system.web.common.component.module.query.ConditionFilter;
import com.system.web.common.component.module.query.ConditionRule;
import com.system.web.common.component.module.query.QueryCondition;
import com.system.web.common.component.module.query.QueryConstant;
import com.system.web.common.component.module.query.QueryConstant.QueryConditionParseType;
import com.system.web.common.component.module.query.QueryConstant.QueryGroupOperation;
import com.system.web.common.component.module.query.QueryConstant.QueryRule;
import com.system.web.common.component.module.query.QueryResult;
import com.system.web.common.component.module.query.QueryResultCount;
import com.system.web.common.component.module.query.QueryResultMap;
import com.system.web.common.component.module.query.UserDataFilter;
import com.system.web.common.component.module.query.UserDataRule;
import com.system.web.common.component.service.commonQuery.parser.JQGridDataParser;
import com.system.web.common.util.json.JSONArray;
import com.system.web.common.util.json.JSONException;
import com.system.web.common.util.json.JSONObject;

/**
 * @Title: JQGridDataParser.java
 * @Package com.yunhan.scc.trading.web.service.impl.CommonQuery.parser
 * TODO(用一句话描述该文件做什么)
 * @author wt
 * @date 2015-6-15 上午11:31:07
 * @version V0.1
 */
@Service("jQGridDataParser")
public class JQGridDataParserImpl implements JQGridDataParser {

	protected Log log = LogFactory.getLog(this.getClass());

	@Resource(name = "commonQueryPersistence")
	public CommonQueryPersistence commonQueryPersistence;

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
	public void ExportConditionParseRule(Map<String, String> requestMap, QueryCondition condition) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
		Gson gson = new Gson();// 基本参数提取
		for (String key : requestMap.keySet()) {
			Object value = requestMap.get(key);
			// 页数
			if ("page".equals(key)) {
				condition.setPage(Integer.valueOf(value.toString()));
			}
			// 条数
			if ("rows".equals(key)) {
				condition.setRows(Integer.valueOf(value.toString()));
			}
			// rownum
			if ("rownum".equals(key)) {
				if (!"".equals(value)) {
					condition.setRownum(Integer.valueOf(value.toString()));
				}
			}
		}

		/* 部分特殊条件单独处理 */
		// 页数和条数处理成offset和limit
		if (null != condition.getPage() && null != condition.getRows()) {
			condition.setOffset((condition.getPage() - 1) * condition.getRows());
			condition.setLimit((condition.getPage() - 1) * condition.getRows() + condition.getRows());
		}

		// 排序处理
		// if (null != requestMap.get("sidx") &&
		// !requestMap.get("sidx").equals("")) {
		// condition.setSortField(this.commonQueryPersistence.getConfigColumnWithMapName(requestMap.get("sidx"),
		// condition.getQueryID()));
		// String sord = "asc";
		// if (null != requestMap.get("sord") &&
		// "desc".equalsIgnoreCase(requestMap.get("sord"))) {
		// sord = "desc";
		// }
		// condition.setSortRule(sord);
		// }

		ConditionFilter filter = gson.fromJson(requestMap.get("reqData"), ConditionFilter.class);
		this.dispersedCondition(condition, filter);
		condition.setConditonStr(this.conditionRuleToConditionStr(condition, filter));
	}

	/**
	 * 
	 * 描述：条件处理
	 * 
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
	public void JQGridConditionParseRule(Map<String, String> requestMap, QueryCondition condition) throws SecurityException,
			IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		// 基本参数提取
		for (String key : requestMap.keySet()) {
			Object value = requestMap.get(key);
			// this.log.debug(key + "=" + value);
			// 查询的ID
			if (key.equals(QueryConstant.QUERY_ID)) {
				condition.setQueryID(value.toString());
				if (StringUtils.isNotBlank(value.toString()) && StringUtils.isNotBlank(requestMap.get("sidx"))) {
					condition.setSortField(this.commonQueryPersistence.getConfigColumnWithMapName(requestMap.get("sidx").toString(),
							condition.getQueryID()));
				}
			}
			// 查询的类型
			if (key.equals(QueryConstant.QUERY_TYPE)) {
				condition.setQueryType(value.toString());
			}
			// 是否需要优化查询
			if (key.equals(QueryConstant.QUERY_EFFICIENCY)) {
				condition.setQueryEfficiency(value.toString());
			}
			// 排序字段
			if ("sidx".equals(key)) {
				if (StringUtils.isNotBlank(value.toString()) && StringUtils.isNotBlank(condition.getQueryID())) {
					condition.setSortField(this.commonQueryPersistence.getConfigColumnWithMapName(value.toString(), condition.getQueryID()));
				}
			}
			// 排序方式
			if ("sord".equals(key)) {
				// condition.put(QueryConstant.SORTRULE, value);
				condition.setSortRule(value.toString());
			}
			// 页数
			if ("page".equals(key)) {
				condition.setPage(Integer.valueOf(value.toString()));
			}
			// 条数
			if ("rows".equals(key)) {
				condition.setRows(Integer.valueOf(value.toString()));
			}
			// rownum
			if ("rownum".equals(key)) {
				if (!"".equals(value)) {
					condition.setRownum(Integer.valueOf(value.toString()));
				}
			}
		}

		/* 部分特殊条件单独处理 */
		// 页数和条数处理成offset和limit
		if (null != condition.getPage() && null != condition.getRows()) {
			condition.setOffset((condition.getPage() - 1) * condition.getRows());
			// condition.setLimit((condition.getPage() - 1) *
			// condition.getRows() + condition.getRows());
			condition.setLimit(condition.getRows());
		}

		// 排序处理
		// if (!requestMap.get("sidx").equals("")) {
		// condition.setSortField(this.commonQueryPersistence.getConfigColumnWithMapName(requestMap.get("sidx"),
		// condition.getQueryID()));
		// condition.setSortRule(requestMap.get("sord"));
		// String sordSql = " order by " + condition.getSortField() + " " +
		// condition.getSortRule();
		// condition.setConditonStr(condition.getConditonStr() + sordSql);
		// }

		// 处理其它sql

		// 单条件处理, 必须在查询条件和查询方式都存在的情况下才成立.
		if ((null != requestMap.get("searchField") && !requestMap.get("searchField").equals(""))
				&& (null != requestMap.get("searchOper") && !requestMap.get("searchOper").equals(""))) {
			// 获取搜索列对应的数据库字段
			ConditionRule rule = new ConditionRule();
			rule.setField(requestMap.get("searchField"));
			rule.setOp(requestMap.get("searchOper"));
			rule.setData(requestMap.get("searchString"));
			String reuslt = this.createSingelCondition(rule, condition);
			condition.setConditonStr(reuslt == null ? null : "where " + reuslt);
		}
		// 多条件,自定义的请求数据
		if (null != requestMap.get("reqData") && !requestMap.get("reqData").equals("")) {
			// filters传过来的数据是json字符串, so需要在这里将json转换成java对象
			Gson gson = new Gson();
			ConditionFilter filter = gson.fromJson(requestMap.get("reqData"), ConditionFilter.class);
			if (null != requestMap.get("rdParseType") && !requestMap.get("rdParseType").equals("")) {
				switch (QueryConditionParseType.getQueryConditionSubmitType(requestMap.get("rdParseType"))) {
				case dispersed:
					this.dispersedCondition(condition, filter);
					break;
				case unified:
					this.dispersedCondition(condition, filter);
					condition.setConditonStr(this.conditionRuleToConditionStr(condition, filter));
					break;
				default:
					this.log.debug("不支持的请求条件解析类型");
					break;
				}
			}
		}

		// 统计信息
		if (null != requestMap.get("userData") && StringUtils.isNotBlank(requestMap.get("userData"))) {
			Gson gson = new Gson();
			UserDataFilter dataFilter = gson.fromJson(requestMap.get("userData"), UserDataFilter.class);
			condition.setUserDataFilter(dataFilter);
		}
	}

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
	public void dispersedCondition(QueryCondition condition, ConditionFilter filter) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class<?> ruleClass = condition.getClass();
		for (ConditionRule rule : filter.getRules()) {
			//修复条件后带,号造成的空值异常 by pangzhenhua 20151218
			if (null == rule) {
				continue;
			}
			if (rule.getField() != null && rule.getField() != "") {
				String methodName = "set" + rule.getField().substring(0, 1).toUpperCase() + rule.getField().substring(1);
				Method method = null;
				Field field = null;
				try {
					field = ruleClass.getDeclaredField(rule.getField());
				} catch (NoSuchFieldException e) {
					this.log.debug("不存在的setter方法, 请检查请求参数中[" + rule.getField() + "]是否已经作为条件对象的属性进行过配置.");
					continue;
				}

				/*
				 * *******如果按属性的类型找不到方法的话，就按字符类型去找 start modified by lurf @3.1
				 * 
				 * @2013-1-24*******
				 */
				// 为了把sql能处理集合，为Condition类声明一个参数为字符串，返回类型为List的冗余方法，再在方法里把字符串转换为集合。
				try {
					method = ruleClass.getDeclaredMethod(methodName, field.getType());
				} catch (NoSuchMethodException e) {
					if (List.class == field.getType()) {
						method = ruleClass.getDeclaredMethod(methodName, String.class);
					} else {
						throw e;
					}
				}
				/* ******* end modified by lurf @3.1 @2013-1-24******* */

				// 参数值为空串，抛弃
				if ("".equals(rule.getData())) {
					continue;
				}

				if (Long.class == field.getType()) {
					method.invoke(condition, Long.parseLong(rule.getData()));
				} else if (Integer.class == field.getType()) {
					method.invoke(condition, Integer.parseInt(rule.getData()));
				} else if (Float.class == field.getType()) {
					method.invoke(condition, Float.parseFloat(rule.getData()));
				} else if (Double.class == field.getType()) {
					method.invoke(condition, Double.parseDouble(rule.getData()));
				} else if (BigDecimal.class == field.getType()) {
					method.invoke(condition, new BigDecimal(rule.getData()));
				} else {
					method.invoke(condition, rule.getData());
				}
			}
		}
	}

	/**
	 * 将条件对象转换成where可以识别的条件字符串
	 * 
	 * @param filter
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public String conditionRuleToConditionStr(QueryCondition condition, ConditionFilter filter) throws SecurityException, NoSuchFieldException {
		StringBuffer resultBuff = new StringBuffer();
		String result = null;
		QueryGroupOperation groupOper = QueryGroupOperation.getQueryGroupOperation(filter.getGroupOp().toUpperCase());

		for (ConditionRule rule : filter.getRules()) {
			String singleCondition = this.createSingelCondition(rule, condition);
			if (null == singleCondition)
				continue;

			resultBuff.append(" ");
			resultBuff.append(singleCondition);

			resultBuff.append(" ");
			resultBuff.append(groupOper.toString());
		}
		// 移除结尾的操作符
		if (0 <= resultBuff.lastIndexOf(groupOper.toString())) {
			result = resultBuff.substring(0, resultBuff.lastIndexOf(groupOper.toString())).trim();
		} else {
			result = resultBuff.toString().trim();
		}
		if (result.length() > 0) {
			return result;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * 描述：创建单一条件
	 * 
	 * @author hanbin
	 * @param rule
	 * @param condition
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public String createSingelCondition(ConditionRule rule, QueryCondition condition) throws SecurityException, NoSuchFieldException {
		String operation = rule.getOp();
		String searchString = rule.getData().trim();
		String searchField = rule.getField();

		String columnName = this.commonQueryPersistence.getConfigColumnWithMapName(searchField, condition.getQueryID());
		if(columnName!=null){
			Class<?> fieldType = condition.getClass().getDeclaredField(rule.getField()).getType();
			QueryRule queryRule = QueryRule.getQueryRule(operation);
			return queryRule.createSingelCondition(columnName, fieldType, searchString);
		}else{
			return null;
		}
	}

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
	public void buildResponseJson(QueryResultCount queryResultCount, StringBuffer result) throws JSONException, SecurityException,
			NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		JSONObject jsonObj = new JSONObject();
		// 根据jqGrid对JSON的数据格式要求给jsonObj赋值
		jsonObj.put("page", queryResultCount.getCondition().getPage()); // 当前页
		// 计算总页数
		if (queryResultCount.getSize() % queryResultCount.getCondition().getRows() == 0) {
			jsonObj.put("total", queryResultCount.getSize() / queryResultCount.getCondition().getRows()); // 总页数
		} else {
			jsonObj.put("total", queryResultCount.getSize() / queryResultCount.getCondition().getRows() + 1); // 总页数
		}
		jsonObj.put("records", queryResultCount.getSize()); // 总记录数

		// 处理统计
		Map<String, Object> userdata = new HashMap<String, Object>();
		UserDataFilter userDataFilter = queryResultCount.getCondition().getUserDataFilter();
		if (null != userDataFilter) {
			userdata.put(userDataFilter.getField(), userDataFilter.getData());
			// 初始化统计数量
			List<UserDataRule> dataRules = userDataFilter.getRules();
			if (null != dataRules) {
				for (UserDataRule userDataRule : dataRules) {
					userdata.put(userDataRule.getField(), 0);
				}
			}
		}

		// 定义rows，存放数据
		JSONArray rows = new JSONArray();
		for (QueryResult obj : queryResultCount.getQueryResultList()) {
			JSONObject cell = new JSONObject(obj);
			rows.put(cell);

			if (null != userDataFilter) {
				List<UserDataRule> dataRules = userDataFilter.getRules();
				if (null != dataRules) {
					for (UserDataRule userDataRule : dataRules) {
						parserUserData(userdata, userDataRule, obj);
					}
				}
			}
		}
		// 将rows放入json对象中
		jsonObj.put("rows", rows);
		// 把统计放入json中
		if (null != userDataFilter) {
			jsonObj.put("userdata", userdata);
		}

		result.append(jsonObj.toString());
	}

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
	public void buildResponseJson(QueryResultMap queryResultCount, StringBuffer result) throws JSONException, SecurityException,
			NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		JSONObject jsonObj = new JSONObject();
		// 根据jqGrid对JSON的数据格式要求给jsonObj赋值
		jsonObj.put("page", queryResultCount.getCondition().getPage()); // 当前页
		// 计算总页数
		if (queryResultCount.getSize() % queryResultCount.getCondition().getRows() == 0) {
			jsonObj.put("total", queryResultCount.getSize() / queryResultCount.getCondition().getRows()); // 总页数
		} else {
			jsonObj.put("total", queryResultCount.getSize() / queryResultCount.getCondition().getRows() + 1); // 总页数
		}
		jsonObj.put("records", queryResultCount.getSize()); // 总记录数

		// 处理统计
		Map<String, Object> userdata = new HashMap<String, Object>();
		UserDataFilter userDataFilter = queryResultCount.getCondition().getUserDataFilter();
		if (null != userDataFilter) {
			userdata.put(userDataFilter.getField(), userDataFilter.getData());
			// 初始化统计数量
			List<UserDataRule> dataRules = userDataFilter.getRules();
			if (null != dataRules) {
				for (UserDataRule userDataRule : dataRules) {
					userdata.put(userDataRule.getField(), 0);
				}
			}
		}

		// 定义rows，存放数据
		JSONArray rows = new JSONArray();
		for (Map obj : queryResultCount.getQueryResultList()) {
			JSONObject cell = new JSONObject(obj);
			rows.put(cell);

			if (null != userDataFilter) {
				List<UserDataRule> dataRules = userDataFilter.getRules();
				if (null != dataRules) {
					for (UserDataRule userDataRule : dataRules) {
						parserUserData(userdata, userDataRule, obj);
					}
				}
			}
		}
		// 将rows放入json对象中
		jsonObj.put("rows", rows);
		// 把统计放入json中
		if (null != userDataFilter) {
			jsonObj.put("userdata", userdata);
		}

		result.append(jsonObj.toString());
	}

	/**
	 * 
	 * 描述：解析用户数据
	 * 
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
	public void parserUserData(Map<String, Object> userdata, UserDataRule userDataRule, QueryResult obj) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String field = userDataRule.getField();

		Integer value = 0;
		if (null != userdata.get(field)) {
			value = (Integer) userdata.get(field);
		}

		String methodName = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
		Class<?> clazz = obj.getClass();
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(methodName, null);
			value = value.intValue() + (Integer) method.invoke(obj, null);
		} catch (NoSuchMethodException e) {
			this.log.debug("不存在的getter方法, 请检查请求参数中[" + field + "]是否已经作为条件对象的属性进行过配置.");
			return;
		}
		userdata.put(field, value);
	}

	/**
	 * 
	 * 描述：解析用户数据
	 * 
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
	public void parserUserData(Map<String, Object> userdata, UserDataRule userDataRule, Map obj) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String field = userDataRule.getField();

		Integer value = 0;
		if (null != userdata.get(field)) {
			value = (Integer) userdata.get(field);
		}

		String methodName = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
		Class<?> clazz = obj.getClass();
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(methodName, null);
			value = value.intValue() + (Integer) method.invoke(obj, null);
		} catch (NoSuchMethodException e) {
			this.log.debug("不存在的getter方法, 请检查请求参数中[" + field + "]是否已经作为条件对象的属性进行过配置.");
			return;
		}
		userdata.put(field, value);
	}
}
