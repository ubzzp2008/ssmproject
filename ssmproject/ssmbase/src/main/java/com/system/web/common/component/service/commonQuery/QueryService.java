package com.system.web.common.component.service.commonQuery;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.system.web.common.component.module.query.QueryResultCount;
import com.system.web.common.component.module.query.QueryResultMap;

/**  
 * @Title: QueryService.java 
 * @Package com.yunhan.scc.trading.web.service.CommonQuery 
 * 查询处理器,查询业务逻辑在此类中完成 如有其它请求处理的来源,重载doQuery()方法即可
 * @author wt
 * @date 2015-6-15 上午11:25:32 
 * @version V0.1  
 */
public interface QueryService {
	/**
	 * 
	 * 通查接口
	 * @param request
	 * @param requestMap
	 * @return
	 * @throws Exception 
	 * @date 2015-6-15
	 */
	public QueryResultCount doQuery(HttpServletRequest request,
			Map<String, String> requestMap) throws Exception;
	
	/**
	 * 通查接口-- 以map方式返回查询数据（页面显示需全部大写）
	 * @param request
	 * @param requestMap
	 * @return
	 * @throws Exception 
	 * @author wangtao
	 * @date 2015-9-6
	 */
	public QueryResultMap doQueryMap(HttpServletRequest request,
			Map<String, String> requestMap) throws Exception;
}
