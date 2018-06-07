package com.system.web.common.component.dao.base;

import java.util.Map;

import com.system.web.common.component.module.query.DataExportDataSource;
import com.system.web.common.component.module.query.QueryCondition;
import com.system.web.common.component.module.query.QueryResultCount;
import com.system.web.common.component.module.query.QueryResultMap;

/**
 * 
 * 类名称：com.yunhan.scc.trading.web.daobase.CommonQueryPersistence   
 * 类描述：查询中与数据库交互的工具, 包含参数封装, 查询执行等方法
 * 创建人：wt  
 * 创建时间：2015-6-15 下午5:11:24   
 * 修改人： 
 * 修改时间： 
 * 修改备注：   
 * @version V0.1
 */
public interface CommonQueryPersistence {

	/**
	 * 
	 * 描述：创建通查条件
	 * 
	 * @author hanbin
	 * @param parameterMap
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public QueryCondition buildQueryCondition(Map<String, String> parameterMap)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException;

	/**
	 * 
	 * TODO(这里用一句话描述这个方法的作用) 
	 * @param condition
	 * @return
	 * @throws Exception 
	 * @author wt
	 * @date 2015-6-15
	 */
	public QueryResultCount queryDataBase(QueryCondition condition)
			throws Exception;

	/**
	 * 
	 * 描述：获取配置行项
	 * 
	 * @author hanbin
	 * @param searchField
	 * @param queryID
	 * @return
	 */
	public String getConfigColumnWithMapName(String searchField, String queryID);

	/**
	 * 
	 * 描述：获取数据导出路径
	 * 
	 * @author hanbin
	 * @return
	 * @throws Exception
	 */
	public String getDataExportMainPath() throws Exception;

	/**
	 * 
	 * 描述：创建导出数据条数
	 * 
	 * @author hanbin
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public QueryCondition buildExportCondition(String id) throws Exception;

	/**
	 * 
	 * 描述：导出数据条数
	 * 
	 * @author hanbin
	 * @param condition
	 * @param dataSourceConfig
	 * @return
	 * @throws Exception
	 */
	public QueryResultCount exportDataBase(QueryCondition condition,
			DataExportDataSource dataSourceConfig) throws Exception;

	/**
	 * 
	 * 描述：得到模板路径
	 * 
	 * @author hanbin
	 * @return
	 * @throws Exception
	 */
	public String getTemplatePath() throws Exception;
	
	
	 /**
     * 查询数据(以Map方式返回)
     */
	public QueryResultMap queryDataMap(QueryCondition condition) throws Exception;
	
	/**
     * 导出数据的查询结果by map方式
     */
    public QueryResultMap exportDataBaseByMap(QueryCondition condition, DataExportDataSource dataSourceConfig) throws Exception;
}
