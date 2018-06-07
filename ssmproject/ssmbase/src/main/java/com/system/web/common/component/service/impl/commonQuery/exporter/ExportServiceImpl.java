package com.system.web.common.component.service.impl.commonQuery.exporter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.web.common.component.dao.base.CommonQueryPersistence;
import com.system.web.common.component.module.query.DataExportConfig;
import com.system.web.common.component.module.query.DataExportDataSource;
import com.system.web.common.component.module.query.DataExportTemplateConfig;
import com.system.web.common.component.module.query.QueryCondition;
import com.system.web.common.component.module.query.QueryResult;
import com.system.web.common.component.module.query.QueryResultCount;
import com.system.web.common.component.module.query.QueryResultMap;
import com.system.web.common.component.service.commonQuery.MessageHandler;
import com.system.web.common.component.service.commonQuery.exporter.ConfigService;
import com.system.web.common.component.service.commonQuery.exporter.ExportService;
import com.system.web.common.util.ParameterTool;
import com.system.web.common.util.StreamUtils;
import com.system.web.common.util.StringUtil;
import com.system.web.common.util.excel.ExcelUtil;
import com.system.web.common.util.excel.FileDownload;
import com.system.web.common.util.excel.FileUtils;
import com.system.web.common.util.excel.PutMasterDataInExcel;
/**  
 * @Title: ExportServiceImpl.java 
 * @Package com.yunhan.scc.trading.web.service.impl.CommonQuery.exporter 
 * TODO(用一句话描述该文件做什么) 
 * @author wt
 * @date 2015-6-15 上午11:58:41 
 * @version V0.1  
 */
@Service
public class ExportServiceImpl implements ExportService {

	private Log log = LogFactory.getLog(ExportService.class);

    @Autowired
    private ConfigService configHandler;

    @Resource(name = "messageHandler")
    private MessageHandler messageHandler;

    @Resource(name="commonQueryPersistence")
    private CommonQueryPersistence commonQueryPersistence;

    public void export(String templateName) {

    }
    
    /**
     * 描述：以传入的集合生成文件
     * @author lurf
     * @param id 此导出的名字
     * @param type 导出类型
     * @param isPst 是否是持久文件
     * @param templateName 模板名称
     * @param fileName 生成的文件名，不加后缀
     * @param beans 要生成文件的集合
     * @param request
     * @param response
     * @throws Exception
     */
    public void export(String id, String type, boolean isPst, String templateName, String fileName, Map<String, Object> beans,
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
		Map<String, String> pageTimer = new HashMap<String, String>();
    	try {
	    	if (null == id || StringUtils.isBlank(id) ||
	    			null == templateName || StringUtils.isBlank(templateName) ||
	    			null == fileName || StringUtils.isBlank(fileName) || 
	    			null == beans || beans.isEmpty() ||
	    			null == request || null == response) {
				pageTimer.put("status", "1"); // 导出完成，通知前台
	    		return;
	    	}
	    	request.getSession().setAttribute("pageTimer", pageTimer);
	    	
	    	String exType = null;
	    	if (null != type && ("EXCEL".equalsIgnoreCase(type) || "PDF".equalsIgnoreCase(type))) {
	    		exType = type.toLowerCase();
	    	} else {
	    		throw new Exception("导出类型不被支持!");
	    	}
	
	        // 首先获取常数管理里面配置的信息
	        String mainPath = this.configHandler.getDataExportMainPath();
	        String templatePath = this.configHandler.getTemplatePath() + "/" + exType + "/" + templateName;
	        
	        if (null != mainPath && !"".equals(mainPath)) {
	        	
	            // 首先检查是否存在该目录, 如果没有就创建, 包括对应的子目录
	            this.configHandler.checkPath(mainPath);
	            
	            //输出路径：常量里配置的导出路径+导出类型+是否是持久的路径（持久文件放在persistent目录下，临时文件放在temp目录下）+配置的ID
	            String outPutPath = mainPath + "/" + exType + "/" + (isPst?"persistent":"temp") + "/" + id;
	            File outFile = new File(outPutPath);
	    		if (!outFile.exists()) {
	    			outFile.mkdirs();
	    		}
	
	    		// 输出文件名为：输出路径+文件名+文件后缀
	    		String filePath = outPutPath + "/" + fileName.trim() + ("excel".equals(exType)?".xls":".pdf");
	    		
	            genWookbook(templatePath, filePath, false, null, beans, null);
	            
	            //下载文件
	            FileDownload.downloadFile(request, response, filePath);
	            // 如果是临时文件删除文件
	            if (!isPst) {
	            	FileDownload.deleteFile(filePath);
	            }
	        } else {
	            throw new Exception("未能找到导出文件的主目录, 请检查常数配置.");
	        }
    		pageTimer.put("status", "1"); // 导出完成，通知前台
    		
    	} catch (Exception e) {
    		pageTimer.put("status", "9"); // 导出出错，通知前台
            pageTimer.put("message", "导出出错，请稍候再试！");
			throw e;
		}
    }
    
	/**
	 * 描述：以传入的集合分页生成sheet文件。
	 * 传入的beans会按limit的值被分成多个sheet页。
	 * 在模板中通过map.get('list')代码获取被分在当前页的子集合，再对子集合迭代输出。
	 * sheetNameTemplate参数指定每页名字的开头部分，如：sheetNameTemplate=data，那么每个sheet页的名字就是data1，data2，data3...
	 * 如果为null，那么每个sheet页的名字就是Sheet1，Sheet2，Sheet3...<br><br>
	 * <i>注意：这个方法和{@link exportToMultipleSheets}方法不一样，此方法适用于一页是一个List集合的情况，
	 * {@link exportToMultipleSheets}适用于一页是一个对象的情况。</i>
	 * 
	 * @param id 此导出的名字
	 * @param type 导出类型
	 * @param isPst 是否是持久文件
	 * @param templateName 模板名称
	 * @param fileName 生成的文件名，不加后缀
	 * @param sheetNameTemplate sheet的名字模板
	 * @param beans 要生成文件的集合
	 * @param limit 每页的记录数
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author lurf
	 * @version v3.1
	 * @since v3.1
	 */
	public void exportToPageingSheets(String id, String type, boolean isPst, String templateName, String fileName,
			String sheetNameTemplate, List<Object> beans, int limit, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, String> pageTimer = new HashMap<String, String>();
		
		List<Map<String, Object>> beansList = null;
    	try {
	    	if (null == id || StringUtils.isBlank(id) ||
	    			null == templateName || StringUtils.isBlank(templateName) ||
	    			null == fileName || StringUtils.isBlank(fileName) ||
	    			null == beans || beans.isEmpty() ||
	    			null == request || null == response) {
				pageTimer.put("status", "1"); // 导出完成，通知前台
	    		return;
	    	}
	    	request.getSession().setAttribute("pageTimer", pageTimer);
	    	
	    	String exType = null;
	    	if (null != type && ("EXCEL".equalsIgnoreCase(type) || "PDF".equalsIgnoreCase(type))) {
	    		exType = type.toLowerCase();
	    	} else {
	    		throw new Exception("导出类型不被支持!");
	    	}
	    	
	    	// 转换beans为需要的集合形式
	    	int limitSize = limit != 0 ? limit : 5000; //默认每页5000条
	    	int count = (beans.size() % limitSize == 0) ? beans.size() / limitSize : beans.size() / limitSize + 1;
	    	String sheetName = null == sheetNameTemplate ? "Sheet" : sheetNameTemplate;
	    	beansList = new ArrayList<Map<String,Object>>();
	    	List<String> sheetNames = new ArrayList<String>();
	    	for (int i=1; i<=count; i++) {
	    		Map<String, Object> map = new HashMap<String, Object>();
	    		if (i == count) {
		    		map.put("list", beans.subList((i-1) * limitSize, beans.size()));
	    		} else {
		    		map.put("list", beans.subList((i-1) * limitSize, i * limitSize));
	    		}
	    		beansList.add(map);
	    		sheetNames.add(sheetName + i);
	    	}
	
	        // 获取常数管理里面配置的信息
	        String mainPath = this.configHandler.getDataExportMainPath();
	        String templatePath = this.configHandler.getTemplatePath() + "/" + exType + "/" + templateName;
	        
	        if (null != mainPath && !"".equals(mainPath)) {
	        	
	            // 首先检查是否存在该目录, 如果没有就创建, 包括对应的子目录
	            this.configHandler.checkPath(mainPath);
	            
	            //输出路径：常量里配置的导出路径+导出类型+是否是持久的路径（持久文件放在persistent目录下，临时文件放在temp目录下）+配置的ID
	            String outPutPath = mainPath + "/" + exType + "/" + (isPst?"persistent":"temp") + "/" + id;
	            File outFile = new File(outPutPath);
	    		if (!outFile.exists()) {
	    			outFile.mkdirs();
	    		}
	
	    		// 输出文件名为：输出路径+文件名+文件后缀
	    		String filePath = outPutPath + "/" + fileName.trim() + ("excel".equals(exType)?".xls":".pdf");
	    		
	            genWookbook(templatePath, filePath, true, sheetNames, null, beansList);
	            
	            //下载文件
	            FileDownload.downloadFile(request, response, filePath);
	            // 如果是临时文件删除文件
	            if (!isPst) {
	            	FileDownload.deleteFile(filePath);
	            }
	        } else {
	            throw new Exception("未能找到导出文件的主目录, 请检查常数配置.");
	        }
			pageTimer.put("status", "1"); // 导出完成，通知前台
		} catch (Exception e) {
			pageTimer.put("status", "9"); // 导出出错，通知前台
	        pageTimer.put("message", "导出出错，请稍候再试！");
			throw e;
		} finally {
			if (null != beansList) {
				beansList.clear();
				beansList = null;
			}
		}
	}
    
    /**
     * 描述：以传入的集合生成多sheet文件。在模板中通过map获取集合里的对象<br><br>
	 * <i>注意：这个方法和{@link exportToPageingSheets}方法不一样，此方法适用于一页是一个对象的情况，
	 * {@link exportToPageingSheets}适用于一页是一个List集合的情况。</i>
	 * 
     * @author lurf
     * @param id 此导出的名字
     * @param type 导出类型
     * @param isPst 是否是持久文件
     * @param templateName 模板名称
     * @param fileName 生成的文件名，不加后缀
     * @param sheetNames sheet的名字
     * @param beansList 要生成文件的集合
     * @param request
     * @param response
     * @throws Exception
     */
	@SuppressWarnings("unchecked")
    public void exportToMultipleSheets(String id, String type, boolean isPst, String templateName, String fileName,
    		List<String> sheetNames, List<Map<String, Object>> beansList, HttpServletRequest request, HttpServletResponse response) throws Exception {

//		Map<String, String> pageTimer = new HashMap<String, String>();
//		request.getSession().setAttribute("pageTimer", pageTimer);
//    	try {
//	    	if (null == id || StringUtils.isBlank(id) ||
//	    			null == templateName || StringUtils.isBlank(templateName) ||
//	    			null == fileName || StringUtils.isBlank(fileName) || 
//	    			null == beansList || beansList.isEmpty() ||
//	    			null == request || null == response) {
//				pageTimer.put("status", "1"); // 导出完成，通知前台
//	    		return;
//	    	}
//	    	
//	    	String exType = null;
//	    	if (null != type && ("EXCEL".equalsIgnoreCase(type) || "PDF".equalsIgnoreCase(type))) {
//	    		exType = type.toLowerCase();
//	    	} else {
//	    		throw new Exception("导出类型不被支持!");
//	    	}
//	
//	        // 首先获取常数管理里面配置的信息
//	        String mainPath = this.configHandler.getDataExportMainPath();
//	        String templatePath = this.configHandler.getTemplatePath() + "/" + exType + "/" + templateName;
//	        
//	        if (null != mainPath && !"".equals(mainPath)) {
//	        	
//	            // 首先检查是否存在该目录, 如果没有就创建, 包括对应的子目录
//	            this.configHandler.checkPath(mainPath);
//	            
//	            //输出路径：常量里配置的导出路径+导出类型+是否是持久的路径（持久文件放在persistent目录下，临时文件放在temp目录下）+配置的ID
//	            String outPutPath = mainPath + "/" + exType + "/" + (isPst?"persistent":"temp") + "/" + id;
//	            File outFile = new File(outPutPath);
//	    		if (!outFile.exists()) {
//	    			outFile.mkdirs();
//	    		}
//	
//	    		// 输出文件名为：输出路径+文件名+文件后缀
//	    		String filePath = outPutPath + "/" + fileName.trim() + ("excel".equals(exType)?".xls":".pdf");
//	    		
//	            genWookbook(templatePath, filePath, true, sheetNames, null, beansList);
//	            
//	            //下载文件
//	            FileDownload.downloadFile(request, response, filePath);
//	            // 如果是临时文件删除文件
//	            if (!isPst) {
////	            	FileDownload.deleteFile(filePath);
//	            }
//	        } else {
//	            throw new Exception("未能找到导出文件的主目录, 请检查常数配置.");
//	        }
//			pageTimer.put("status", "1"); // 导出完成，通知前台
//		} catch (Exception e) {
//			pageTimer.put("status", "9"); // 导出出错，通知前台
//	        pageTimer.put("message", "导出出错，请稍候再试！");
//			throw e;
//		}
    	exportToMultipleSheets(id, type, isPst, templateName, fileName, sheetNames, beansList, request, response, new HashMap());
    }
    
    
    /**
     * 基本是拷贝上面的方法， 多一个Map，放入供excel模板文件使用的参数
     * 
     * @author lvjie
     * @param id 此导出的名字
     * @param type 导出类型
     * @param isPst 是否是持久文件
     * @param templateName 模板名称
     * @param fileName 生成的文件名，不加后缀
     * @param sheetNames sheet的名字
     * @param beansList 要生成文件的集合
     * @param otherEntries 供excel模板文件使用的额外参数
     * @param request
     * @param response
     * @throws Exception
     * @version 3.2
     */
    @SuppressWarnings("unchecked")
    public void exportToMultipleSheets(String id, String type, boolean isPst, String templateName, String fileName,
    		List<String> sheetNames, List<Map<String, Object>> beansList, HttpServletRequest request, HttpServletResponse response,
    		Map otherEntries) throws Exception {

		Map<String, String> pageTimer = new HashMap<String, String>();
    	try {
	    	if (null == id || StringUtils.isBlank(id) ||
	    			null == templateName || StringUtils.isBlank(templateName) ||
	    			null == fileName || StringUtils.isBlank(fileName) || 
	    			null == beansList || beansList.isEmpty() ||
	    			null == request || null == response) {
				pageTimer.put("status", "1"); // 导出完成，通知前台
	    		return;
	    	}
	    	request.getSession().setAttribute("pageTimer", pageTimer);
	    	
	    	String exType = null;
	    	if (null != type && ("EXCEL".equalsIgnoreCase(type) || "PDF".equalsIgnoreCase(type))) {
	    		exType = type.toLowerCase();
	    	} else {
	    		throw new Exception("导出类型不被支持!");
	    	}
	
	        // 首先获取常数管理里面配置的信息
	        String mainPath = this.configHandler.getDataExportMainPath();
	        String templatePath = this.configHandler.getTemplatePath() + "/" + exType + "/" + templateName;
	        
	        if (null != mainPath && !"".equals(mainPath)) {
	        	
	            // 首先检查是否存在该目录, 如果没有就创建, 包括对应的子目录
	            this.configHandler.checkPath(mainPath);
	            
	            //输出路径：常量里配置的导出路径+导出类型+是否是持久的路径（持久文件放在persistent目录下，临时文件放在temp目录下）+配置的ID
	            String outPutPath = mainPath + "/" + exType + "/" + (isPst?"persistent":"temp") + "/" + id;
	            File outFile = new File(outPutPath);
	    		if (!outFile.exists()) {
	    			outFile.mkdirs();
	    		}
	
	    		// 输出文件名为：输出路径+文件名+文件后缀
	    		String filePath = outPutPath + "/" + fileName.trim() + ("excel".equals(exType)?".xls":".pdf");
	    		
	            genWookbook(templatePath, filePath, true, sheetNames, null, beansList, otherEntries, true);	// modified by @lvjie @3.2 @2013-9-16
	            
	            //下载文件
	            FileDownload.downloadFile(request, response, filePath);
	            // 如果是临时文件删除文件
	            if (!isPst) {
	            	FileDownload.deleteFile(filePath);
	            }
	        } else {
	            throw new Exception("未能找到导出文件的主目录, 请检查常数配置.");
	        }
			pageTimer.put("status", "1"); // 导出完成，通知前台
		} catch (Exception e) {
			pageTimer.put("status", "9"); // 导出出错，通知前台
	        pageTimer.put("message", "导出出错，请稍候再试！");
			throw e;
		}
    }
    
    
    
    
    /**
     * 导出数据, response不为空则表示需要下载
     * 
     * @param id 配置文件配置的ID
     * @param type 导出类型：EXCEL或PDF等
     * @param mainPath 导出功能的工作路径
     * @param fileName 导出文件的名字
     * @param requestMap 导出参数
     * @throws Exception
     * @author lurf
     */
    @SuppressWarnings("unchecked")
    public void exportFormQuery(String id, String type, boolean isPst, String mainPath, String fileName, Map<String, String> requestMap,
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// 读取配置
        DataExportConfig config = this.configHandler.getConfigWithIDandMainPath(id, mainPath);
        if (null == config) {
            throw new Exception("未能找到[" + id + "]对应的导出配置.");
        }
        // 将type转换成小写,后面会用到
        config.setType(type.toLowerCase());

        Map<String, Object> data = new HashMap<String, Object>();
        for (String dataSourceKey : config.getDataSourceNames()) {
            DataExportDataSource dataSource = config.getDataSources().get(dataSourceKey);
            // 通开始初始化创建查询参数对象
            QueryCondition condition = this.commonQueryPersistence.buildExportCondition(dataSource.getSelectID());
            condition.setQueryID(dataSource.getSelectID());

            // 处理请求参数,将所有参数从map中取出填充查询参数对象
            this.messageHandler.handRequestParam(requestMap, condition);
            // 执行查询数据库
            QueryResultCount resultCount = this.commonQueryPersistence.exportDataBase(condition, dataSource);
            this.checkQueryResultCount(resultCount);
            // 检查是否存在子数据源,存在则进行子数据源的查询
            if (null != dataSource.getChild()) {
                for (DataExportDataSource childDs : dataSource.getChild()) {
                    // 将父类的关联属性值提取出来放到子条件里面
                    if (dataSource.isCollection()) {
                        for (QueryResult paretnResult : resultCount.getQueryResultList()) {
                            this.buildChildData(paretnResult, dataSource, childDs);
                        }
                    } else {
                        this.buildChildData(resultCount.getQueryResult(), dataSource, childDs);
                    }
                }
            }
            if (dataSource.isCollection()) {
                data.put(dataSource.getId(), resultCount.getQueryResultList());
            } else {
                data.put(dataSource.getId(), resultCount.getQueryResult());
            }
        }

        // 开始导出excel
        // 获取模板路径
        DataExportTemplateConfig tempLate = config.getDataExportTemplateConfigWithType(config.getType());
        String templatePath = this.configHandler.getTemplatePath() + "/" + config.getType() + "/" + tempLate.getRelativePath();

        //输出路径：常量里配置的导出路径+导出类型+是否是持久的路径（持久文件放在persistent目录下，临时文件放在temp目录下）+配置的ID
//      String separator = System.getProperty("file.separator");
//      String outPutPath = mainPath + separator + config.getType() + separator + id;
        String outPutPath = mainPath + "/" + config.getType() + "/" + (isPst?"persistent":"temp") + "/" + id;
        File outFile = new File(outPutPath);
		if (!outFile.exists()) {
			outFile.mkdirs();
		}

		// 输出文件名为：输出路径+配置的文件名+_当前时间+文件后缀
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//      String filePath = mainPath + separator + config.getType() + separator + id + separator + tempLate.getOutFileName();
//		String filePath = outPutPath + "/" + tempLate.getOutFileName() + "_" + format.format(new Date())
//				+ ("EXCEL".equals(config.getType())?".xls":".pdf");
		String filePath = null;
		if (null == fileName || StringUtils.isBlank(fileName)) {
			filePath = outPutPath + "/" + tempLate.getOutFileName() + "_" + format.format(new Date())
				+ ("excel".equals(config.getType())?".xls":".pdf");
		} else {
			filePath = outPutPath + "/" + fileName.trim() + ("excel".equals(config.getType())?".xls":".pdf");
		}

        // 要迭代list生成多sheet的
        if (tempLate.getMultipleSheets()) {
            List<String> sheetNames = new ArrayList<String>();
            List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
            
            for (String dataSourceName : config.getDataSourceNames()) {
                DataExportDataSource ds = config.getDataSources().get(dataSourceName);
                if (ds.isCollection()) {
                    List<QueryResult> list = (List<QueryResult>) data.get(ds.getId());
                    for (QueryResult queryResult : list) {
                        sheetNames.add(this.getValueWithObjAttribute(queryResult, ds.getSheetNameAtt()).toString());
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put(ds.getId(), queryResult);
                        //子数据源数据填充
                        maps.add(map);
                    }
                } else {
                    QueryResult result = (QueryResult) data.get(ds.getSelectID());
                    sheetNames.add(this.getValueWithObjAttribute(result, ds.getSheetNameAtt()).toString());
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put(ds.getId(), result);
                    maps.add(map);
                }
            }

            genWookbook(templatePath, filePath, true, sheetNames, null, maps);
        }else if(tempLate.getMultipleExcels()){
        	List<String> excelNames = new ArrayList<String>();
            List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
            
            for (String dataSourceName : config.getDataSourceNames()) {
                DataExportDataSource ds = config.getDataSources().get(dataSourceName);
                if (ds.isCollection()) {
                    List<QueryResult> list = (List<QueryResult>) data.get(ds.getId());
                    for (QueryResult queryResult : list) {
                    	excelNames.add(this.getValueWithObjAttribute(queryResult, ds.getSheetNameAtt()).toString());
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put(ds.getId(), queryResult);
                        //子数据源数据填充
                        maps.add(map);
                    }
                } else {
                	QueryResult result = (QueryResult) data.get(ds.getId());
                	excelNames.add(this.getValueWithObjAttribute(result, ds.getSheetNameAtt()).toString());
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put(ds.getId(), result);
                    maps.add(map);
                }
            }
            String excelPath = outPutPath + "/" + fileName.trim();
//            filePath = excelPath+"/zip/"+fileName.trim()+".zip";
            //edit by yangtao  2016-5-10 单个文件不压缩。
            if(excelNames.size() == 1){
            	filePath = excelPath + "/" + excelNames.get(0) + ".xls";
            }else{
            	filePath = excelPath+".zip";
            }
            //end by yangtao  2016-5-10
            //log.debug("filePath--->" + filePath + "excelPath--->" + excelPath + "excelNames-->" + excelNames);
            genWookbookZip(templatePath, filePath, excelPath, excelNames, maps);
        }  else {
        // 不迭代的情况
            Map<String, Object> beanParams = new HashMap<String, Object>();
            
            for (String dataSourceName : config.getDataSourceNames()) {
                DataExportDataSource ds = config.getDataSources().get(dataSourceName);
                if (ds.isCollection()) {
                    List<QueryResult> list = (List<QueryResult>) data.get(ds.getId());
                    beanParams.put(ds.getId(), list);
                } else {
                    QueryResult result = (QueryResult) data.get(ds.getId());
                    beanParams.put(ds.getId(), result);
                }
            }
            genWookbook(templatePath, filePath, false, null, beanParams, null);
        }
        
        //下载文件
        FileDownload.downloadFile(request, response, filePath);
        // 如果是临时文件删除文件
        if (!isPst) {
        	FileDownload.deleteFile(filePath);
        }
    }
    
    /**
     * 描述：生成excel文件
     * @author lurf
     * @param templatePath 模板路径
     * @param filePath 生成文件路径
     * @param isMultipleSheets 是否是多sheet，true:是
     * @param sheetNames 多sheet时，每个sheet的名字
     * @param beans 数据集合
     * @param beansList 多sheet时的数据集合
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	private void genWookbook(String templatePath, String filePath, boolean isMultipleSheets, List<String> sheetNames,
    		Map<String, Object> beans, List<Map<String, Object>> beansList) throws Exception {
//    	log.debug("=====开始生成导出文件===== 文件为：" + filePath);
//    	long startTime = System.currentTimeMillis();
//    	
//        XLSTransformer transformer = new XLSTransformer();
//        InputStream is = null;
//        OutputStream os = null;
//    	try {
//            is = new BufferedInputStream(new FileInputStream(templatePath));
//            os = new BufferedOutputStream(new FileOutputStream(filePath));
//
//            Workbook workBook = null;
//			if (isMultipleSheets) {
//    			workBook = transformer.transformMultipleSheetsList(is, beansList, sheetNames, "map", new HashMap(), 0);
//			} else {
//                workBook = transformer.transformXLS(is, beans);
//			}
//			
//            workBook.write(os);
//            os.flush();
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			if (null != is) {
//				is.close();
//			}
//			if (null != os) {
//				os.close();
//			}
//			
//			long endTime = System.currentTimeMillis();
//			log.debug("=====生成导出文件结束===== 文件名：" + filePath + "，耗时：" + (endTime-startTime) + "ms");
//		}
    	genWookbook(templatePath, filePath, isMultipleSheets, sheetNames, beans, beansList, new HashMap(), true);
    }
    
    
    /**
     * 
     * 描述：在导出excel的同时，能够放入额外的参数(otherEntries)供模板使用，基本是从上面拷贝的
     * @param templatePath 模板路径
     * @param filePath 生成文件路径
     * @param isMultipleSheets 是否是多sheet，true:是
     * @param sheetNames 多sheet时，每个sheet的名字
     * @param beans 数据集合
     * @param beansList 多sheet时的数据集合
     * @param otherEntries 其他的，在模板中使用的参数值
     * @param dumb otherEntries含有beanName时,是否报错, true:不报错，false:报错
     * @throws Exception
     * @author lvj 2013-9-16
     * @version 3.2
     * @since 3.2
     */
//	@SuppressWarnings("unchecked")
	public  void genWookbook(String templatePath, String filePath, boolean isMultipleSheets, List<String> sheetNames,
    		Map<String, Object> beans, List<Map<String, Object>> beansList, Map otherEntries, boolean dumb) throws Exception {
    	log.debug("=====开始生成导出文件===== 文件为：" + filePath);
    	long startTime = System.currentTimeMillis();
    	
        XLSTransformer transformer = new XLSTransformer();
        InputStream is = null;
        OutputStream os = null;
    	try {
            is = new BufferedInputStream(new FileInputStream(templatePath));
            os = new BufferedOutputStream(new FileOutputStream(filePath));

            Workbook workBook = null;
            /**************** modified by @lvjie @3.2 @2013-9-16 start *********************/
			if (isMultipleSheets) {
//    			workBook = transformer.transformMultipleSheetsList(is, beansList, sheetNames, "map", new HashMap(), 0);
				String beanName = "map";
				if(!dumb && otherEntries.containsKey(beanName)){
					throw new IllegalArgumentException("传入的供excel模板使用的参数不能用"+beanName+"做key");
				}
    			workBook = transformer.transformMultipleSheetsList(is, beansList, sheetNames, "map", otherEntries, 0);
			} else {
                workBook = transformer.transformXLS(is, beans);
			}
			/**************** modified by @lvjie @3.2 @2013-9-16 end *********************/
			try {
				ExcelUtil.resetCellFormula(workBook);
			} catch (Exception e) {
				log.error("重新计算excel异常，不重要",e);
			}
            workBook.write(os);
            os.flush();
		} catch (Exception e) {
			throw e;
		} finally {
			if (null != is) {
				is.close();
			}
			if (null != os) {
				os.close();
			}
			
			long endTime = System.currentTimeMillis();
			log.debug("=====生成导出文件结束===== 文件名：" + filePath + "，耗时：" + (endTime-startTime) + "ms");
		}
    }
    
    /**
     * 
     * 描述：检查查询结果
     * @author hanbin
     * @param resultCount
     * @throws Exception
     */
    private void checkQueryResultCount(QueryResultCount resultCount) throws Exception {
        if (null == resultCount.getQueryResultList() && null == resultCount.getQueryResult()) {
            throw new Exception("查询无结果.");
        }
    }

    /**
     * 
     * 描述：检查附属属性
     * @author hanbin
     * @param source
     * @param target
     * @param conditionAttributeName
     * @throws Exception
     */
    private void extractAttribute(Object source, Object target, String conditionAttributeName) throws Exception {
        if (null == conditionAttributeName) {
            throw new Exception("条件属性[export.conf.{id}.dataSource.{name}.child.{cName}.conditionAttribute]配置未配置.");
        }
        // 获取目标对象的属性值
        Class<? extends Object> targetClass = target.getClass();
        // 获取源对象的属性值
        Class<? extends Object> sourceClass = source.getClass();
        String [] mehodNames = conditionAttributeName.split(",");
        for(String mehodName : mehodNames){
        	
			String getMehodName = "get" + StringUtil.toUpperCaseFirstOne(mehodName);
			String setMehodName = "set" + StringUtil.toUpperCaseFirstOne(mehodName);
	       
//	        String getMehodName = "get" + conditionAttributeName.substring(0, 1).toUpperCase() + conditionAttributeName.substring(1);
//	        String setMehodName = "set" + conditionAttributeName.substring(0, 1).toUpperCase() + conditionAttributeName.substring(1);
	        
	        Method sourceMethod = sourceClass.getDeclaredMethod(getMehodName);
	        Class<?> sourceType = sourceMethod.getReturnType();
	        Object property = sourceMethod.invoke(source);
	        // set进目标对象的对应属性中
	        Method targetMethod = targetClass.getDeclaredMethod(setMehodName, sourceType);
	        targetMethod.invoke(target, property);
        }
    }

    /**
     * 
     * 描述：创建子对象
     * @author hanbin
     * @param parentResult
     * @param dataSource
     * @param childDs
     * @throws Exception
     */
    private void buildChildData(QueryResult parentResult, DataExportDataSource dataSource, DataExportDataSource childDs) throws Exception {
        QueryCondition chCondition = this.commonQueryPersistence.buildExportCondition(childDs.getSelectID());
        //为子查询设置参数
        this.extractAttribute(parentResult, chCondition, childDs.getConditionAttribute());
        //付退信息 子查询增加采购商参数,暂时写死 added by zhangheng @4.0 @2014-3-26 start
        if(null != childDs.getSelectID() && childDs.getSelectID().equals("getWxPayReturnItems")){
        	this.extractAttribute(parentResult, chCondition, "purchaserID");
        }
        // added by zhangheng @4.0 @2014-3-26 end
        QueryResultCount chResultCount = this.commonQueryPersistence.exportDataBase(chCondition, childDs);

        String getMehodName = "get" + childDs.getAddAttribute().substring(0, 1).toUpperCase() + childDs.getAddAttribute().substring(1);
        String setMehodName = "set" + childDs.getAddAttribute().substring(0, 1).toUpperCase() + childDs.getAddAttribute().substring(1);
        Class<? extends Object> sourceClass = parentResult.getClass();
        Method getMethod = sourceClass.getDeclaredMethod(getMehodName);
        Class<?> type = getMethod.getReturnType();
        Method setMethod = sourceClass.getDeclaredMethod(setMehodName, type);

//        if (5 < chResultCount.getQueryResultList().size()) {
//            System.out.println("size=" + chResultCount.getQueryResultList().size());
//        }
        if (childDs.isCollection()) {
            setMethod.invoke(parentResult, chResultCount.getQueryResultList());
        } else {
            setMethod.invoke(parentResult, chResultCount.getQueryResult());
        }
    }

    /**
     * 
     * 描述：根据属性获取值
     * @author hanbin
     * @param obj
     * @param attName
     * @return
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private Object getValueWithObjAttribute(Object obj, String attName) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        String getMehodName = "get" + attName.substring(0, 1).toUpperCase() + attName.substring(1);
        Class<? extends Object> sourceClass = obj.getClass();
        Method getMethod = sourceClass.getDeclaredMethod(getMehodName);

        Object property = getMethod.invoke(obj);
        return property;
    }

	@Override
	public <E> void exportExcelAppendinfo(HttpServletRequest request,
			HttpServletResponse response, List<E> datas,
			String excelName,String fileURL,String dataName) throws Exception {
        fileURL = ParameterTool.getParameter("templatePath")+ fileURL;
     
		String newFileName = excelName+"_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xls";
		// 向excel中写入数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(/*"goodsReportDatas"*/dataName, datas);
		
		response.setContentType("application/ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(newFileName, "UTF-8"))));
		OutputStream out = response.getOutputStream();
		PutMasterDataInExcel.createExcel(fileURL, map, out,dataName);
		
	}
	
	
	
	
	
	
	
	
	  /**
     * 导出数据, response不为空则表示需要下载 Map方式
     * 
     * @param id 配置文件配置的ID
     * @param type 导出类型：EXCEL或PDF等
     * @param mainPath 导出功能的工作路径
     * @param fileName 导出文件的名字
     * @param requestMap 导出参数
     * @throws Exception
     * @author wangtao
     */
    @SuppressWarnings("unchecked")
    public void exportFormQueryByMap(String id, String type, boolean isPst, String mainPath, String fileName, Map<String, String> requestMap,
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// 读取配置
        DataExportConfig config = this.configHandler.getConfigWithIDandMainPath(id, mainPath);
        if (null == config) {
            throw new Exception("未能找到[" + id + "]对应的导出配置.");
        }
        // 将type转换成小写,后面会用到
        config.setType(type.toLowerCase());

        Map<String, Object> data = new HashMap<String, Object>();
        for (String dataSourceKey : config.getDataSourceNames()) {
            DataExportDataSource dataSource = config.getDataSources().get(dataSourceKey);
            // 通开始初始化创建查询参数对象
            QueryCondition condition = this.commonQueryPersistence.buildExportCondition(dataSource.getSelectID());
            condition.setQueryID(dataSource.getSelectID());

            // 处理请求参数,将所有参数从map中取出填充查询参数对象
            this.messageHandler.handRequestParam(requestMap, condition);
            // 执行查询数据库
            QueryResultMap resultCount = this.commonQueryPersistence.exportDataBaseByMap(condition, dataSource);
            this.checkQueryResultMap(resultCount);
            // 检查是否存在子数据源,存在则进行子数据源的查询
            if (null != dataSource.getChild()) {
                for (DataExportDataSource childDs : dataSource.getChild()) {
                    // 将父类的关联属性值提取出来放到子条件里面
                    if (dataSource.isCollection()) {
                        for (Map<String, Object> paretnResult : resultCount.getQueryResultList()) {
                            this.buildChildDataByMap(paretnResult, dataSource, childDs);
                        }
                    } else {
                        this.buildChildDataByMap(resultCount.getQueryResult(), dataSource, childDs);
                    }
                }
            }
            if (dataSource.isCollection()) {
                data.put(dataSource.getId(), resultCount.getQueryResultList());
            } else {
                data.put(dataSource.getId(), resultCount.getQueryResult());
            }
        }

        // 开始导出excel
        // 获取模板路径
        DataExportTemplateConfig tempLate = config.getDataExportTemplateConfigWithType(config.getType());
        String templatePath = this.configHandler.getTemplatePath() + "/" + config.getType() + "/" + tempLate.getRelativePath();

        //输出路径：常量里配置的导出路径+导出类型+是否是持久的路径（持久文件放在persistent目录下，临时文件放在temp目录下）+配置的ID
//      String separator = System.getProperty("file.separator");
//      String outPutPath = mainPath + separator + config.getType() + separator + id;
        String outPutPath = mainPath + "/" + config.getType() + "/" + (isPst?"persistent":"temp") + "/" + id;
        File outFile = new File(outPutPath);
		if (!outFile.exists()) {
			outFile.mkdirs();
		}

		// 输出文件名为：输出路径+配置的文件名+_当前时间+文件后缀
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//      String filePath = mainPath + separator + config.getType() + separator + id + separator + tempLate.getOutFileName();
//		String filePath = outPutPath + "/" + tempLate.getOutFileName() + "_" + format.format(new Date())
//				+ ("EXCEL".equals(config.getType())?".xls":".pdf");
		String filePath = null;
		if (null == fileName || StringUtils.isBlank(fileName)) {
			filePath = outPutPath + "/" + tempLate.getOutFileName() + "_" + format.format(new Date())
				+ ("excel".equals(config.getType())?".xls":".pdf");
		} else {
			filePath = outPutPath + "/" + fileName.trim() + ("excel".equals(config.getType())?".xls":".pdf");
		}

        // 要迭代list生成多sheet的
        if (tempLate.getMultipleSheets()) {
            List<String> sheetNames = new ArrayList<String>();
            List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
            
            for (String dataSourceName : config.getDataSourceNames()) {
                DataExportDataSource ds = config.getDataSources().get(dataSourceName);
                if (ds.isCollection()) {
                    List<Map<String, Object>> list = (List<Map<String, Object>>) data.get(ds.getId());
                    for (Map<String, Object> queryResult : list) {
                        sheetNames.add(queryResult.get(ds.getSheetNameAtt()).toString().toUpperCase());
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put(ds.getId(), queryResult);
                        //子数据源数据填充
                        maps.add(map);
                    }
                } else {
                	Map<String, Object> result = (Map<String, Object>) data.get(ds.getSelectID());
                	sheetNames.add(result.get(ds.getSheetNameAtt()).toString().toUpperCase());
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put(ds.getId(), result);
                    maps.add(map);
                }
            }

            genWookbook(templatePath, filePath, true, sheetNames, null, maps);
        }else if(tempLate.getMultipleExcels()){
        	List<String> excelNames = new ArrayList<String>();
            List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
            
            for (String dataSourceName : config.getDataSourceNames()) {
                DataExportDataSource ds = config.getDataSources().get(dataSourceName);
                if (ds.isCollection()) {
                    List<Map<String, Object>> list = (List<Map<String, Object>>) data.get(ds.getId());
                    for (Map<String, Object> queryResult : list) {
                    	excelNames.add(queryResult.get(ds.getSheetNameAtt()).toString().toUpperCase());
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put(ds.getId(), queryResult);
                        //子数据源数据填充
                        maps.add(map);
                    }
                } else {
                	Map<String, Object> result = (Map<String, Object>) data.get(ds.getSelectID());
                	excelNames.add(result.get(ds.getSheetNameAtt()).toString().toUpperCase());
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put(ds.getId(), result);
                    maps.add(map);
                }
            }
            String excelPath = outPutPath + "/" + fileName.trim();
            //edit by yangtao  2016-5-10 单个文件不压缩。
            if(excelNames.size() == 1){
            	filePath = excelPath + "/" + excelNames.get(0) + ".xls";
            }else{
            	filePath = excelPath+".zip";
            }
            //end by yangtao  2016-5-10
            genWookbookZip(templatePath, filePath, excelPath, excelNames, maps);
        } else {
        // 不迭代的情况
            Map<String, Object> beanParams = new HashMap<String, Object>();
            
            for (String dataSourceName : config.getDataSourceNames()) {
                DataExportDataSource ds = config.getDataSources().get(dataSourceName);
                if (ds.isCollection()) {
                    List<Map<String, Object>> list = (List<Map<String, Object>>) data.get(ds.getId());
                    beanParams.put(ds.getId(), list);
                } else {
                	Map<String, Object> result = (Map<String, Object>) data.get(ds.getSelectID());
                    beanParams.put(ds.getId(), result);
                }
            }

            genWookbook(templatePath, filePath, false, null, beanParams, null);
        }
        
        //下载文件
        FileDownload.downloadFile(request, response, filePath);
        // 如果是临时文件删除文件
        if (!isPst) {
        	FileDownload.deleteFile(filePath);
        }
    }
	
    /**
     * 
     * 描述：检查查询结果
     * @author wangtao
     * @param resultCount
     * @throws Exception
     */
    private void checkQueryResultMap(QueryResultMap resultCount) throws Exception {
        if (null == resultCount.getQueryResultList() && null == resultCount.getQueryResult()) {
            throw new Exception("查询无结果.");
        }
    }
	
    /**
     * 
     * 描述：创建子对象
     * @author hanbin
     * @param parentResult
     * @param dataSource
     * @param childDs
     * @throws Exception
     */
    private void buildChildDataByMap(Map<String, Object> parentResult, DataExportDataSource dataSource, DataExportDataSource childDs) throws Exception {
        QueryCondition chCondition = this.commonQueryPersistence.buildExportCondition(childDs.getSelectID());
        //为子查询设置参数
        this.extractAttributeByMap(parentResult, chCondition, childDs.getConditionAttribute());
       
        QueryResultMap chResultCount = this.commonQueryPersistence.exportDataBaseByMap(chCondition, childDs);

        if (childDs.isCollection()) {
            parentResult.put(childDs.getAddAttribute(),chResultCount.getQueryResultList());
        } else {
        	parentResult.put(childDs.getAddAttribute(), chResultCount.getQueryResult());
        }
    }
	
    /**
     * 
     * 描述：检查附属属性
     * @author hanbin
     * @param source
     * @param target
     * @param conditionAttributeName
     * @throws Exception
     */
    private void extractAttributeByMap(Object source, Object target, String conditionAttributeName) throws Exception {
        if (null == conditionAttributeName) {
            throw new Exception("条件属性[export.conf.{id}.dataSource.{name}.child.{cName}.conditionAttribute]配置未配置.");
        }
        // set进目标对象的对应属性中
        Class<? extends Object> targetClass = target.getClass();
        // 获取源对象的属性值
        if(source!= null && source instanceof Map){
            Map<?,?> returnValue = (Map<?,?>)source;
            if(returnValue.containsKey(conditionAttributeName)){
                Object value = returnValue.get(conditionAttributeName);
	            Method[] methods = targetClass.getDeclaredMethods();//获得类的方法集合       
	            //遍历方法集合
	            for(int i =0 ;i<methods.length;i++){
	               //获取所有setXX()的返回值
	               //methods[i].getName()方法返回方法名
	               if(methods[i].getName().startsWith("set")){
	            	   if(methods[i].getName().equalsIgnoreCase("set"+conditionAttributeName)){
	            		   methods[i].invoke(target, value);
	            	   }
	               }
	            }
            }
            
        }else if(source!=null){
        	String getMehodName = "get" + conditionAttributeName.substring(0, 1).toUpperCase() + conditionAttributeName.substring(1);
        	String setMehodName = "set" + conditionAttributeName.substring(0, 1).toUpperCase() + conditionAttributeName.substring(1);
        	Class<? extends Object> sourceClass = source.getClass();
	        Method sourceMethod = sourceClass.getDeclaredMethod(getMehodName);
	        Class<?> sourceType = sourceMethod.getReturnType();
	        Object property = sourceMethod.invoke(source);
	        
	        // set进目标对象的对应属性中
	        Method targetMethod = targetClass.getDeclaredMethod(setMehodName, sourceType);
	        targetMethod.invoke(target, property);
	
        }
       
        
    }
    
    
    
    /**
     * 
     * 描述：导出多个excel 最终以压缩包方式导出
     * @param templatePath 模板路径
     * @param filePath 生成excel文件路径
     * @param isMultipleSheets 是否是多sheet，true:是
     * @param excelNames 多excle时，每个excle的名字
     * @param beans 数据集合
     * @param zipFilePath 生成zip文件路径
     * @param beansList 多excel时的数据集合
     * @throws Exception
     * @author wangtao 2016年2月29日16:27:07
     */
	@SuppressWarnings("unchecked")
	private void genWookbookZip(String templatePath,String zipFilePath, String filePath, List<String> excelNames,
    		 List<Map<String, Object>> beansList) throws Exception {
    	log.debug("=====开始生成导出文件===== 文件为：" + zipFilePath);
    	long startTime = System.currentTimeMillis();
    	try {
            int i = 0;
            File file = new File(filePath);
        	if(!file.exists()){
        		file.mkdirs();
        	}
        	
        	CountDownLatch countDownLatch = new CountDownLatch(beansList.size());
        	
            for(Map<String, Object> beans:beansList){
            	executorService.submit(new CreateExcelThread(templatePath, filePath, excelNames.get(i++), beans, countDownLatch));
            }
            countDownLatch.await();//表示所有线程都已经执行完成。
            //add by yangtao  2016-5-10 如果是一个Excel文件的话，不需要压缩
            if(excelNames.size() == 1){
            	
            }else{
            //end by yangtao 2016-5-10	
	            FileUtils.GenzipFile(file, zipFilePath); 
	            FileUtils.delDirAndFile(filePath);//打包完成后删掉文件夹
            }
		} catch (Exception e) {
			throw e;
		} finally {
			long endTime = System.currentTimeMillis();
			log.debug("=====生成导出文件结束===== 文件名：" + filePath + "，耗时：" + (endTime-startTime) + "ms");
		}
    }
	
	public static ExecutorService executorService = Executors.newFixedThreadPool(20);
	
	

}



/**
 * 生成excel文档线程
 * @author wangtao
 * @version created at 2016-3-18 上午10:34:37
 */
class CreateExcelThread implements Runnable{
	private static Log log = LogFactory.getLog(CreateExcelThread.class);
	private String templatePath;
	private String filePath;
	private String excelName;
	private Map<String, Object> beans;
	private CountDownLatch countDownLatch;
	
	public CreateExcelThread(String templatePath,String filePath,String excelName,Map<String, Object> beans,CountDownLatch countDownLatch){
		this.templatePath = templatePath;
		this.filePath = filePath;
		this.excelName = excelName;
		this.beans = beans;
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		InputStream is = null;
		OutputStream os = null;
		log.debug("生成excel线程启动---  文件名："+excelName);
		try {
			XLSTransformer transformer = new XLSTransformer();
			is = new BufferedInputStream(new FileInputStream(templatePath));
			String excelPath = filePath+"/"+excelName+".xls";
			os = new BufferedOutputStream(new FileOutputStream(excelPath));
			Workbook workBook = transformer.transformXLS(is, beans);
			try {
				ExcelUtil.resetCellFormula(workBook);
			} catch (Exception e) {
				log.error("重新计算excel异常，不重要",e);
			}
			workBook.write(os);
			os.flush();
		} catch (Exception e) {
			log.error("为打包生成的excel异常",e);
		}finally{
			StreamUtils.closeInStream(is);
			StreamUtils.closeOutStream(os);
			log.debug("生成excel线程结束---  文件名："+excelName);
			countDownLatch.countDown();
		}
	}
	
}
