package com.system.web.common.component.service.impl.commonQuery;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.system.web.common.component.dao.base.CommonQueryPersistence;
import com.system.web.common.component.module.query.QueryCondition;
import com.system.web.common.component.module.query.QueryResultCount;
import com.system.web.common.component.module.query.QueryResultMap;
import com.system.web.common.component.service.commonQuery.MessageHandler;
import com.system.web.common.component.service.commonQuery.QueryService;

/**
 * @Title: QueryServiceImpl.java
 * @Package com.yunhan.scc.trading.web.service.impl.CommonQuery 查询处理器,查询业务逻辑在此类中完成
 * @author wt
 * @date 2015-6-15 上午11:45:57
 * @version V0.1
 */
@Service
public class QueryServiceImpl implements QueryService {

	@Resource(name = "messageHandler")
	private MessageHandler messageHandler;

	@Resource(name = "commonQueryPersistence")
	private CommonQueryPersistence commonQueryPersistence;

	@SuppressWarnings("unchecked")
	public QueryResultCount doQuery(HttpServletRequest request,
			Map<String, String> requestMap) throws Exception {
		// 首先进行基础参数有效性验证
		if (this.messageHandler.requestParamCheck(requestMap)) {
			// 通过基本参数有效性验证后开始初始化创建查询参数对象
			QueryCondition condition = this.commonQueryPersistence
					.buildQueryCondition(requestMap);// 对象的属性都为null?

			// 处理请求参数,将所有参数从map中取出填充查询参数对象
			this.messageHandler.handRequestParam(requestMap, condition);

			// 处理有返回的情况，把查询条件放入session
			Map<String, Object> backMap = (Map<String, Object>) request
					.getSession().getAttribute("backMap");
			if (null != backMap) {
				Map<String, Object> thisBack = (Map<String, Object>) backMap
						.get(request.getParameter("query_id"));
				if (null != thisBack) {
					thisBack.put("condition", condition);
				}
			}

			// 执行查询数据库
			QueryResultCount resultCount = this.commonQueryPersistence
					.queryDataBase(condition);
			return resultCount;
		} else {
			throw new Exception("基础数据验证未通过，请求地址：" + request.getRequestURI());
		}
	}
	/**
	 * 以map方式返回查询数据（页面显示需全部大写）
	 * @param request
	 * @param requestMap
	 * @return
	 * @throws Exception 
	 * @author wangtao
	 * @date 2015-9-6
	 */
	public QueryResultMap doQueryMap(HttpServletRequest request,
			Map<String, String> requestMap) throws Exception {
		// 首先进行基础参数有效性验证
		if (this.messageHandler.requestParamCheck(requestMap)) {
			// 通过基本参数有效性验证后开始初始化创建查询参数对象
			QueryCondition condition = this.commonQueryPersistence
					.buildQueryCondition(requestMap);// 对象的属性都为null?

			// 处理请求参数,将所有参数从map中取出填充查询参数对象
			this.messageHandler.handRequestParam(requestMap, condition);

			// 处理有返回的情况，把查询条件放入session
			Map<String, Object> backMap = (Map<String, Object>) request
					.getSession().getAttribute("backMap");
			if (null != backMap) {
				Map<String, Object> thisBack = (Map<String, Object>) backMap
						.get(request.getParameter("query_id"));
				if (null != thisBack) {
					thisBack.put("condition", condition);
				}
			}
			// 执行查询数据库
			QueryResultMap resultCount = this.commonQueryPersistence
					.queryDataMap(condition);
			return resultCount;
		} else {
			throw new Exception("基础数据验证未通过，请求地址：" + request.getRequestURI());
		}
	}
}
