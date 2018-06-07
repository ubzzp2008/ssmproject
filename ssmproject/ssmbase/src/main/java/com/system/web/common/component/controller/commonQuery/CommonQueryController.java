package com.system.web.common.component.controller.commonQuery;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.system.web.common.component.module.query.QueryConstant;
import com.system.web.common.component.module.query.QueryConstant.QueryType;
import com.system.web.common.component.module.query.QueryResultCount;
import com.system.web.common.component.module.query.QueryResultForObject;
import com.system.web.common.component.module.query.QueryResultMap;
import com.system.web.common.component.service.commonQuery.MessageHandler;
import com.system.web.common.component.service.commonQuery.QueryService;
import com.system.web.common.component.service.commonQuery.exporter.ConfigService;
import com.system.web.common.component.service.commonQuery.exporter.ExportService;

/**
 * for jqgrid 的通用查询入口,
 * 
 * @author lxxccc
 * 
 */
@Controller
@RequestMapping("/console")
public class CommonQueryController{

    protected Log log = LogFactory.getLog(this.getClass());
    @Autowired
    private QueryService queryHandler;
    @Resource(name = "messageHandler")
    private MessageHandler messageHandler;

    @Autowired
    private ConfigService configHandler;

    @Autowired
    private ExportService exportHandler;

    /**
     * 示例请求地址: http://127.0.0.1:8080/trading/console/query.do 除IP和上下文跟名称可变外,其他为固定<br>
     * 提交方式: POST (only) <br>
     * 默认参数: 查询请求中必须包含的参数,如果缺少则不会有数据返回,以下参数名称以及参数值不区分大小写,不支持中文.<br>
     * <ol>
     * <li>query_id - 请求的查询标识,对应iBatis的select节点的ID,同时iBatis的配置中需要一个以该ID开头,以"_count"结尾的总数统计查询配置.</li>
     * <li>query_type - 请求的查询类型, 默认值为QUERY_WEB</li>
     * </ol>
     * 可选参数: 查询请求中可选的参数,缺少并不会影响正常的查询,以下参数名称以及参数值不区分大小写, 不支持中文.<br>
     * <ol>
     * <li>query_efficiency - 是否进行查询优化,参数值yes/no,默认no.针对于大数量数据库的操作,需分页SQL的支持.</li>
     * </ol>
     * 注意: 该查询模块仅支持iBatis
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/query")
    public void query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        StringBuffer result = new StringBuffer();
        long start = System.currentTimeMillis();
        Map<String, String> reqeustMap = null;
        String callback = null;
        
        try {
        	callback = request.getParameter("callback");
        	
            // 先将所有请求数据提出来生成一个map
            reqeustMap = this.messageHandler.buildRequestMap(request);
            
            if (log.isDebugEnabled()) {
                String queryID = "";
                if (null != reqeustMap && null != reqeustMap.get("query_id")) {
                	queryID = reqeustMap.get("query_id");
                }
                log.debug("CommonQuery [" + queryID + "] start ===  RequestMethod[POST]");
            }
            
            QueryResultCount queryResultCount = this.queryHandler.doQuery(request, reqeustMap);
            this.messageHandler.toJson(queryResultCount, result);
        } catch (Exception e) {
        	
            result.delete(0, result.length());
            // result.append(this.QUERY_NULL_RETURN);
            String queryID = "";
            if (null != reqeustMap && null != reqeustMap.get("query_id")) {
            	queryID = reqeustMap.get("query_id");
            }
            log.error("CommonQuery [" + queryID + "] 出现[" + e.toString() + "]异常", e);
            throw e;
        }
        // 设置字符编码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        // 返回json对象（通过PrintWriter输出）
        if(null != callback){
        	response.getWriter().write(callback + "(" + result.toString() + ")");
        }else{
            response.getWriter().write(result.toString());
        }
        
        long end = System.currentTimeMillis();
        long time = end - start;
        if (time > 5000) {
            String queryID = "";
            if (null != reqeustMap) {
            	queryID = reqeustMap.get("query_id");
            }
//            log.debug("CommonQuery [" + queryID + "] result ===: [" + result.toString() + "]");
            log.info("CommonQuery [" + queryID + "] time is ===: " + time + "ms");
        }
    }
    
    /**
     * 示例请求地址: http://127.0.0.1:8080/trading/console/query.do 除IP和上下文跟名称可变外,其他为固定<br>
     * 提交方式: POST (only) <br>
     * 默认参数: 查询请求中必须包含的参数,如果缺少则不会有数据返回,以下参数名称以及参数值不区分大小写,不支持中文.<br>
     * <ol>
     * <li>query_id - 请求的查询标识,对应iBatis的select节点的ID,同时iBatis的配置中需要一个以该ID开头,以"_count"结尾的总数统计查询配置.</li>
     * <li>query_type - 请求的查询类型, 默认值为QUERY_WEB</li>
     * </ol>
     * 可选参数: 查询请求中可选的参数,缺少并不会影响正常的查询,以下参数名称以及参数值不区分大小写, 不支持中文.<br>
     * <ol>
     * <li>query_efficiency - 是否进行查询优化,参数值yes/no,默认no.针对于大数量数据库的操作,需分页SQL的支持.</li>
     * </ol>
     * 注意: 该查询模块仅支持iBatis
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/queryMap")
    public void queryMap(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	request.setCharacterEncoding("UTF-8");
        StringBuffer result = new StringBuffer();
        long start = System.currentTimeMillis();
        Map<String, String> reqeustMap = null;
        String callback = null;
        try {
        	callback = request.getParameter("callback");
            // 先将所有请求数据提出来生成一个map
            reqeustMap = this.messageHandler.buildRequestMap(request);
            
            if (log.isDebugEnabled()) {
                String queryID = "";
                if (null != reqeustMap && null != reqeustMap.get("query_id")) {
                	queryID = reqeustMap.get("query_id");
                }
                log.debug("CommonQuery [" + queryID + "] start ===  RequestMethod[POST]");
            }
            QueryResultMap queryResultCount = this.queryHandler.doQueryMap(request, reqeustMap);
            this.messageHandler.toJson(queryResultCount, result);
        } catch (Exception e) {
            result.delete(0, result.length());
            String queryID = "";
            if (null != reqeustMap && null != reqeustMap.get("query_id")) {
            	queryID = reqeustMap.get("query_id");
            }
            log.error("CommonQuery [" + queryID + "] 出现[" + e.toString() + "]异常", e);
            throw e;
        }
        // 设置字符编码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        // 返回json对象（通过PrintWriter输出）
        if(null != callback){
        	response.getWriter().write(callback + "(" + result.toString() + ")");
        }else{
            response.getWriter().write(result.toString());
        }
        
        long end = System.currentTimeMillis();
        long time = end - start;
        if (time > 5000) {
            String queryID = "";
            if (null != reqeustMap) {
            	queryID = reqeustMap.get("query_id");
            }
            log.info("CommonQuery [" + queryID + "] time is ===: " + time + "ms");
        }
    }
    
    public QueryResultForObject query1(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        log.debug("CommonQuery start ===  RequestMethod[POST]");
        StringBuffer result = new StringBuffer();
        QueryResultForObject queryResultForObject = null;
        long start = System.currentTimeMillis();
        Map<String, String> reqeustMap = null;
        try {
            // 先将所有请求数据提出来生成一个map
            reqeustMap = this.messageHandler.buildRequestMap(request);
            
            if (log.isDebugEnabled()) {
                String queryID = "";
                if (null != reqeustMap && null != reqeustMap.get("query_id")) {
                	queryID = reqeustMap.get("query_id");
                }
                log.debug("CommonQuery [" + queryID + "] start ===  RequestMethod[POST]");
            }
            
            QueryResultCount queryResultCount = this.queryHandler.doQuery(request, reqeustMap);
            this.messageHandler.toJson(queryResultCount, result);
            queryResultForObject = new QueryResultForObject();
            queryResultForObject.setPage(queryResultCount.getCondition().getPage()); // 当前页数
            queryResultForObject.setOffset(queryResultCount.getCondition().getOffset());
            queryResultForObject.setLimit(queryResultCount.getCondition().getLimit());
            // 计算总页数
            if (queryResultCount.getSize() % queryResultCount.getCondition().getRows() == 0) {
            	queryResultForObject.setTotal(queryResultCount.getSize() / queryResultCount.getCondition().getRows()); // 总页数
            } else {
            	queryResultForObject.setTotal(queryResultCount.getSize() / queryResultCount.getCondition().getRows() + 1); // 总页数
            }
            queryResultForObject.setRecords(queryResultCount.getSize()); // 总记录数
            queryResultForObject.setRows(queryResultCount.getQueryResultList());
        } catch (Exception e) {
        	e.printStackTrace();
            result.delete(0, result.length());
            // result.append(this.QUERY_NULL_RETURN);
            String queryID = "";
            if (null != reqeustMap && null != reqeustMap.get("query_id")) {
            	queryID = reqeustMap.get("query_id");
            }
            log.error("CommonQuery [" + queryID + "] 出现[" + e.toString() + "]异常");
        }
        
        long end = System.currentTimeMillis();
        long time = end - start;
        if (time > 5000) {
            String queryID = "";
            if (null != reqeustMap) {
            	queryID = reqeustMap.get("query_id");
            }
            log.info("CommonQuery [" + queryID + "] time is ===: " + (end - start) + "ms");
        }
        return queryResultForObject;
    }

    /**
     * 描述：导出
     * @author lurf
     * @param id 配置的导出id
     * @param type 导出类型
     * @param source 导出类型  Object-对象  map map键值对 
     * @param pst 是否是持久文件，true:导出生成的文件不删除，false:导出生成的文件下载后删除
     * @param request
     * @param response
     */
	@RequestMapping(value = "/export/{id}/{type}")
    public void exportData(@PathVariable("id") String id, @PathVariable("type") String type,@RequestParam(value="source",required=false) String source,
    		@RequestParam(value="fileName",required=false) String fileName,
    		@RequestParam(value="pst",defaultValue="false",required=false) boolean isPst,
    		HttpServletRequest request, HttpServletResponse response) {

        long start = System.currentTimeMillis();
        
		Map<String, String> pageTimer = new HashMap<String, String>();
		request.getSession().setAttribute("pageTimer", pageTimer);
		String name = "";
        try {
    		fileName = URLDecoder.decode(fileName, "UTF-8");
    		if (null != request.getSession()) {
////    			SecurityContextImpl securityContext = (SecurityContextImpl)SecurityContextHolder.getContext();
//    			if(null != securityContext && null != securityContext.getAuthentication()){
//    				name =  securityContext.getAuthentication().getName();
//    			}else{
//    				throw new Exception("Session Timeout");
//    			}
    		}
            log.info("=== Export start is ===  ID:" + id + "，操作者：" + name + "，文件名：" + fileName);
        	
            // 首先获取常数管理里面配置的信息
            String mainPath = this.configHandler.getDataExportMainPath();
            
            if (null != mainPath && !"".equals(mainPath)) {
            	
                // 首先检查是否存在该目录, 如果没有就创建, 包括对应的子目录
                this.configHandler.checkPath(mainPath);
                
                // 先将所有请求数据提出来生成一个map
                Map<String, String> reqeustMap = this.messageHandler.buildRequestMap(request);
                reqeustMap.put(QueryConstant.QUERY_TYPE, QueryType.EXPORT.toString());
                
                if(source==null||!source.equalsIgnoreCase("map")){
	                // 导出文件开始
	                this.exportHandler.exportFormQuery(id, type, isPst, mainPath, fileName, reqeustMap, request, response);
                }else{
                	 // 导出文件开始
	                this.exportHandler.exportFormQueryByMap(id, type, isPst, mainPath, fileName, reqeustMap, request, response);
                }
                
        		pageTimer.put("status", "1"); // 导出完成，通知前台
            } else {
                throw new Exception("未能找到导出文件的主目录, 请检查常数配置.");
            }

            long end = System.currentTimeMillis();
//            long time = end - start;
//            if (time > 5000) {
                log.info("=== Export end is ===  ID:" + id + "，操作者：" + name + "，文件名：" + fileName + "， 耗时：" + (end - start) + "ms");
//            }
        } catch (Exception e) {
    		pageTimer.put("status", "9"); // 导出出错，通知前台
            pageTimer.put("message", "导出出错，请稍候再试！");
            log.error("=== Export end is ===  ID:" + id + "，操作者：" + name + "，文件名：" + fileName + "出现[" + e.toString() + "]异常", e);
        }
    }
}
