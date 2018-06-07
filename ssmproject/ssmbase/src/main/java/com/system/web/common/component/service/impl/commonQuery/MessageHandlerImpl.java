package com.system.web.common.component.service.impl.commonQuery;

import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.system.web.common.component.module.query.QueryCondition;
import com.system.web.common.component.module.query.QueryConstant;
import com.system.web.common.component.module.query.QueryConstant.QueryType;
import com.system.web.common.component.module.query.QueryResultCount;
import com.system.web.common.component.module.query.QueryResultMap;
import com.system.web.common.component.service.commonQuery.MessageHandler;
import com.system.web.common.component.service.commonQuery.parser.JQGridDataParser;
import com.system.web.common.util.json.JSONException;

/**
 * 查询配套的数据处理器,用于数据的解析封装等
 * 
 */
@Service("messageHandler")
public class MessageHandlerImpl implements MessageHandler {

    protected Log log = LogFactory.getLog(this.getClass());

    @Resource(name = "jQGridDataParser")
    private JQGridDataParser jQGridDataParser;

    /**
     * 请求参数的检查验证
     * 
     * @param request
     * @return
     */
    public boolean requestParamCheck(Map<String, String> requestMap) {
        // 必要参数的检查
        if (null == requestMap.get(QueryConstant.QUERY_ID)) {//看参数中有没有query_id
            log.info("必要参数缺失" + QueryConstant.QUERY_ID);
            return false;
        }
        if (null == requestMap.get(QueryConstant.QUERY_TYPE)) {//看参数中有没有query_type
            log.info("必要参数缺失" + QueryConstant.QUERY_TYPE);
            return false;
        }
        return true;
    }

    /**
     * 将所有查询参数从request中提取出来生成map返回
     * 
     * @param request
     */
    @SuppressWarnings("unchecked")
	public Map<String, String> buildRequestMap(HttpServletRequest request) {
        Map<String, String> requestMap = new HashMap<String, String>();
        for (Enumeration<?> e = request.getParameterNames(); e.hasMoreElements();) {
            String key = e.nextElement().toString();
            Object value = null;
            value = request.getParameter(key);
            /*try {
            	//根据请求情况确定转码 by pangzhenhua 20151217
            	if(request.getMethod().equals("GET")){
    				value = new String(value.toString().trim().getBytes("ISO-8859-1"), "UTF-8");
            	}
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				log.error("参数转码异常",e1);
			}*/
//            log.debug("request: " + key + "=" + value);
            if (key.equals("page")) {
                if (null != request.getParameter("_search1") && "true".equals(request.getParameter("_search1"))) {
                	value = "1"; // 如果是查询或删除重置页数为1
//                } else if (null != request.getParameter("_page") && !"0".equals(request.getParameter("_page"))) {
//                	value = request.getParameter("_page"); // 如果设置了页数用设置的页数
                }
            }
            requestMap.put(key, value.toString());
        }
        
        // 处理有返回的情况
        Map<String, Map<String, Object>> backMap = (Map<String, Map<String, Object>>)request.getSession().getAttribute("backMap");
        if (null != backMap) {
            if (null != request.getParameter("_hisType") && "back".equals(request.getParameter("_hisType"))) {
        		Map<String, Object> thisBack = (Map<String, Object>)backMap.get(request.getParameter("query_id"));
        		if (null != thisBack) {
        			Map<String, String> requestMap1 = (Map<String, String>)thisBack.get("postData");
        			if (null != requestMap1) {
        				requestMap = requestMap1;
            			requestMap.put("_hisType", request.getParameter("_hisType"));
        			}
        		} else {
                	Map<String, Object> thisQuery = new HashMap<String, Object>();
                	thisQuery.put("postData", requestMap);
            		backMap.put(request.getParameter("query_id"), thisQuery);
        		}
            } else {
            	Map<String, Object> thisQuery = new HashMap<String, Object>();
            	thisQuery.put("postData", requestMap);
        		backMap.put(request.getParameter("query_id"), thisQuery);
            }
        }
        
        return requestMap;
    }

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
    public void handRequestParam(Map<String, String> requestMap, QueryCondition condition) throws SecurityException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        QueryType queryType = QueryType.getQueryType(requestMap.get(QueryConstant.QUERY_TYPE));
        switch (queryType) {
	        case JQGRID:
	            this.jQGridDataParser.JQGridConditionParseRule(requestMap, condition);
	            break;
	        case WEB:
	
	            break;
	        case GWT:
	
	            break;
	        case EXPORT:
	            this.jQGridDataParser.ExportConditionParseRule(requestMap, condition);
	            break;
	        case UNKNOWN:
	
	            break;
	        default :
	        	this.jQGridDataParser.JQGridConditionParseRule(requestMap, condition);
	            break;
        }
    }

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
    public void toJson(QueryResultCount queryResultCount, StringBuffer result) throws JSONException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        QueryType queryType = QueryType.getQueryType(queryResultCount.getCondition().getQueryType());
        switch (queryType) {
        case JQGRID:
            this.jQGridDataParser.buildResponseJson(queryResultCount, result);
            break;
        case WEB:

            break;
        case GWT:

            break;
        case UNKNOWN:

            break;
        }
    }
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
    public void toJson(QueryResultMap queryResultCount, StringBuffer result) throws JSONException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        QueryType queryType = QueryType.getQueryType(queryResultCount.getCondition().getQueryType());
        switch (queryType) {
        case JQGRID:
            this.jQGridDataParser.buildResponseJson(queryResultCount, result);
            break;
        case WEB:

            break;
        case GWT:

            break;
        case UNKNOWN:

            break;
        }
    }
}
