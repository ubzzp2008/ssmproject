package com.system.web.common.component.service.commonQuery;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.system.web.common.component.module.query.QueryCondition;
import com.system.web.common.component.module.query.QueryResultCount;
import com.system.web.common.component.module.query.QueryResultMap;
import com.system.web.common.util.json.JSONException;

/**  
 * @Title: MessageHandler.java 
 * @Package com.yunhan.scc.trading.web.service.CommonQuery 
 * 查询配套的数据处理器,用于数据的解析封装等
 * @date 2015-6-15 上午11:44:02 
 * @version V0.1  
 */
public interface MessageHandler {
	
	/**
     * 请求参数的检查验证
     * 
     * @param request
     * @return
     */
    public boolean requestParamCheck(Map<String, String> requestMap);

    /**
     * 将所有查询参数从request中提取出来生成map返回
     * 
     * @param request
     */
    @SuppressWarnings("unchecked")
	public Map<String, String> buildRequestMap(HttpServletRequest request);

    /**
     * 处理请求参数,将参数转换成查询所能识别的内容.<br>
     * 
     * @param request
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws SecurityException
     */
    public void handRequestParam(Map<String, String> requestMap, QueryCondition condition) throws SecurityException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;

    /**
     * 
     * 描述：转化成JSON
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
    public void toJson(QueryResultCount queryResultCount, StringBuffer result) throws JSONException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;
    /**
     * 
     * 描述：转化成JSON
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
    public void toJson(QueryResultMap queryResultCount, StringBuffer result) throws JSONException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;
}
