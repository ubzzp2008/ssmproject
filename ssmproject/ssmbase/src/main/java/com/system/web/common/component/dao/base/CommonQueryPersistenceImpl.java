package com.system.web.common.component.dao.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.stereotype.Component;

import com.system.web.common.component.module.query.DataExportDataSource;
import com.system.web.common.component.module.query.QueryCondition;
import com.system.web.common.component.module.query.QueryConstant;
import com.system.web.common.component.module.query.QueryResult;
import com.system.web.common.component.module.query.QueryResultCount;
import com.system.web.common.component.module.query.QueryResultMap;
import com.system.web.common.util.ParameterTool;
import com.system.web.common.util.StringUtil;

/**  
 * @Title: CommonQueryPersistenceImpl.java 
 * @Package com.yunhan.scc.trading.web.dao.impl.CommonQuery 
 * 
 * @author wt
 * @date 2015-6-15 上午11:17:37 
 * @version V0.1  
 */
@Component("commonQueryPersistence")
public class CommonQueryPersistenceImpl implements CommonQueryPersistence{

	 protected Log log = LogFactory.getLog(this.getClass());

	    @Resource(name = "sqlSessionFactory")
	    private DefaultSqlSessionFactory sqlSessionFactoryBean;

	    /**
	     * 建立查询条件
	     */
	    public QueryCondition buildQueryCondition(Map<String, String> parameterMap) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
	        String queryID = parameterMap.get(QueryConstant.QUERY_ID).toString();
	        if (!queryID.equals("")) {
	            String conditionClassName = this.sqlSessionFactoryBean.getConfiguration().getMappedStatement(queryID).getParameterMap().getType().getName();
	            return (QueryCondition) Class.forName(conditionClassName).newInstance();
	        }
	        return null;
	    }

	    /**
	     * 建立导出条件
	     */
	    public QueryCondition buildExportCondition(String selectID) throws Exception {
	        String conditionClassName = this.sqlSessionFactoryBean.getConfiguration().getMappedStatement(selectID).getParameterMap().getType().getName();
	        return (QueryCondition) Class.forName(conditionClassName).newInstance();
	    }

	    /**
	     * 查询数据
	     */
		public QueryResultCount queryDataBase(QueryCondition condition) throws Exception {
	        QueryResultCount resultCount = new QueryResultCount();
	        SqlSession sqlSession = this.sqlSessionFactoryBean.openSession();
	        try {
	            // 获取总数据记录数a
	        	Object sizeTmp = sqlSession.selectOne(condition.getQueryID() + "_count", condition);
	        	if(null == sizeTmp){
	        		sizeTmp = 0;
	        	}
	            Long size = Long.valueOf(sizeTmp.toString());
//           Long size = Long.valueOf(sqlSession.selectOne(condition.getQueryID() + "_count", condition).toString());
	        	// Long size = Long.valueOf(new Integer(1).toString());
	            // 查询具体数据
	            RowBounds rowBounds = new RowBounds(condition.getOffset(), condition.getLimit());
	            List<QueryResult> list = sqlSession.selectList(condition.getQueryID(), condition, rowBounds);
	            resultCount.setCondition(condition);
	            resultCount.setQueryResultList(list);
	            resultCount.setSize(size);
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            sqlSession.close();
	        }
	        return resultCount;
	    }

	    /**
	     * 得到配置行项
	     */
	    public String getConfigColumnWithMapName(String searchField, String queryID) {
	        for (ResultMap rm : this.sqlSessionFactoryBean.getConfiguration().getMappedStatement(queryID).getResultMaps()) {
	            for (ResultMapping rmapping : rm.getResultMappings()) {
	                if (searchField.equals(rmapping.getProperty())) {
	                    return rmapping.getColumn();
	                }
	            }
	        }
	        return null;
	    }

	    /**
	     * 得到导出路径
	     */
	    public String getDataExportMainPath() throws Exception {
	        String result = null;
	        /*SqlSession sqlSession = this.sqlSessionFactoryBean.openSession();
	        try {
	            result = sqlSession.selectOne("getDataExportMainPath").toString();
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            sqlSession.close();
	        }*/
	        result = ParameterTool.getParameter("exportMainPath");
	        return result;
	    }
	    /**
	     * 得到模板路径
	     */
	    public String getTemplatePath() throws Exception {
	        String result = ParameterTool.getParameter("templatePath");
	       /* SqlSession sqlSession = this.sqlSessionFactoryBean.openSession();
	        try {
	            result = sqlSession.selectOne("getTemplatePath").toString();
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            sqlSession.close();
	        }*/
	        return result;
	    }

	    /**
	     * 导出数据的查询结果
	     */
	    public QueryResultCount exportDataBase(QueryCondition condition, DataExportDataSource dataSourceConfig) throws Exception {
	        QueryResultCount resultCount = new QueryResultCount();
	        resultCount.setCondition(condition);
	        SqlSession sqlSession = this.sqlSessionFactoryBean.openSession();
	        try {
	            // 查询具体数据
	            if (dataSourceConfig.isCollection()) {
	                List<QueryResult> list = new ArrayList<QueryResult>();
	                /*
	                 * 传入参数如果有分页信息，用分页的信息，否则用最大数， 但一次从库里查询300条记录
	                 */
	            	long size = 20000L;
	            	if (null != condition.getLimit() && condition.getLimit().intValue() >= 0) {
	            		long sizeTemp = Long.valueOf(condition.getLimit());
	            		if (size > sizeTemp) {
	            			size = sizeTemp;
	            		}
	            	} else {
	                    // 获取总数据记录数
	            		long sizeTemp = Long.parseLong(StringUtil.parseString(sqlSession.selectOne(dataSourceConfig.getSelectID() + "_count", condition),"0"));
	            		if (size > sizeTemp) {
	            			size = sizeTemp;
	            		}
	            	}
	            	resultCount.setSize(size);
	            	
	                int limit = 300;
	                
	                long step = 1;
	                if (size > limit) {
	                	if (0 == size % limit) {
	                		step = size / limit;
	                	} else {
	                		step = size / limit + 1;
	                	}
	                }
	                
	            	int offset = 0;
	            	if (null != condition.getOffset() && condition.getOffset().intValue() >= 0) {
	            		offset = condition.getOffset();
	            	}
	            	
	                for (int j=0; j<step; j++) {
	                	RowBounds rowBounds = new RowBounds(offset, limit);
	                    List<QueryResult> listTemp = sqlSession.selectList(dataSourceConfig.getSelectID(), condition, rowBounds);
	                    if (null != listTemp && !listTemp.isEmpty()) {
	                    	list.addAll(listTemp);
	                    }
	                    listTemp = null;
	                    offset = limit * (j + 1);
	                }
	                
	                resultCount.setQueryResultList(list);
	            } else {
	                QueryResult sin = (QueryResult) sqlSession.selectOne(dataSourceConfig.getSelectID(), condition);
	                resultCount.setSize(1l);
	                resultCount.setQueryResult(sin);
	            }
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            sqlSession.close();
	        }
	        return resultCount;
	    }
	    /**
	     * 查询数据(以Map方式返回)
	     */
		public QueryResultMap queryDataMap(QueryCondition condition) throws Exception {
			QueryResultMap resultCount = new QueryResultMap();
	        SqlSession sqlSession = this.sqlSessionFactoryBean.openSession();
	        try {
	            // 获取总数据记录数
	        	Long size = Long.valueOf(sqlSession.selectOne(condition.getQueryID() + "_count", condition).toString());
	            // 查询具体数据
	            RowBounds rowBounds = new RowBounds(condition.getOffset(), condition.getLimit());
	            List<Map<String, Object>> list = sqlSession.selectList(condition.getQueryID(), condition, rowBounds);
	            resultCount.setCondition(condition);
	            resultCount.setQueryResultList(list);
	            resultCount.setSize(size);
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            sqlSession.close();
	        }
	        return resultCount;
	    }
	    
	    
	    
	    
		 /**
	     * 导出数据的查询结果by map方式
	     */
	    public QueryResultMap exportDataBaseByMap(QueryCondition condition, DataExportDataSource dataSourceConfig) throws Exception {
	    	QueryResultMap resultCount = new QueryResultMap();
	        resultCount.setCondition(condition);
	        SqlSession sqlSession = this.sqlSessionFactoryBean.openSession();
	        try {
	            // 查询具体数据
	            if (dataSourceConfig.isCollection()) {
	                List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	                /*
	                 * 传入参数如果有分页信息，用分页的信息，否则用最大数， 但一次从库里查询300条记录
	                 */
	            	long size = 20000L;
	            	if (null != condition.getLimit() && condition.getLimit().intValue() >= 0) {
	            		long sizeTemp = Long.valueOf(condition.getLimit());
	            		if (size > sizeTemp) {
	            			size = sizeTemp;
	            		}
	            	} else {
	                    // 获取总数据记录数
	            		long sizeTemp = Long.parseLong(sqlSession.selectOne(dataSourceConfig.getSelectID() + "_count", condition).toString());
	            		if (size > sizeTemp) {
	            			size = sizeTemp;
	            		}
	            	}
	            	resultCount.setSize(size);
	            	
	                int limit = 300;
	                
	                long step = 1;
	                if (size > limit) {
	                	if (0 == size % limit) {
	                		step = size / limit;
	                	} else {
	                		step = size / limit + 1;
	                	}
	                }
	                
	            	int offset = 0;
	            	if (null != condition.getOffset() && condition.getOffset().intValue() >= 0) {
	            		offset = condition.getOffset();
	            	}
	            	
	                for (int j=0; j<step; j++) {
	                	RowBounds rowBounds = new RowBounds(offset, limit);
	                    List<Map<String,Object>> listTemp = sqlSession.selectList(dataSourceConfig.getSelectID(), condition, rowBounds);
	                    if (null != listTemp && !listTemp.isEmpty()) {
	                    	list.addAll(listTemp);
	                    }
	                    listTemp = null;
	                    offset = limit * (j + 1);
	                }
	                
	                resultCount.setQueryResultList(list);
	            } else {
	            	Map<String,Object> sin = (Map<String,Object>) sqlSession.selectOne(dataSourceConfig.getSelectID(), condition);
	                resultCount.setSize(1l);
	                resultCount.setQueryResult(sin);
	            }
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            sqlSession.close();
	        }
	        return resultCount;
	    }
	    
	    
	    
	    
	    
}
