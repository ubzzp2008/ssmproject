package com.system.web.common.component.service.impl.commonQuery.exporter;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.system.web.common.component.dao.base.CommonQueryPersistence;
import com.system.web.common.component.module.query.DataExportConfig;
import com.system.web.common.component.module.query.DataExportDataSource;
import com.system.web.common.component.module.query.DataExportTemplateConfig;
import com.system.web.common.component.service.commonQuery.exporter.ConfigService;
import com.system.web.common.util.StreamUtils;

/**  
 * @Title: ConfigServiceImpl.java 
 * @Package com.yunhan.scc.trading.web.service.impl.CommonQuery.exporter 
 * 配置处理器, 用于导出文件的基础配置信息的处理准备
 * @date 2015-6-15 上午11:52:25 
 * @version V0.1  
 */
@Service
public class ConfigServiceImpl implements ConfigService{

	   /** 配置文件名称 */
	private static final String CONFIG_PROPERTIES_NAME = "/config/export";

    private Map<String, DataExportConfig> configs = new HashMap<String, DataExportConfig>();

    @Resource(name="commonQueryPersistence")
    private CommonQueryPersistence commonQueryPersistence;

    /**
     * 获取数据导出的主目录
     * 
     * @return
     * @throws Exception
     */
    public String getDataExportMainPath() throws Exception {
        return this.commonQueryPersistence.getDataExportMainPath();
    }

    /**
     * 
     * 描述：获取模板路径
     * @author hanbin
     * @return
     * @throws Exception
     */
	public String getTemplatePath() throws Exception {
		return this.commonQueryPersistence.getTemplatePath();
	}

    /**
     * 根据主目录检查是否存在对应的目录, 如果不存在就创建
     * 
     * @param mainPath
     */
    public void checkPath(String mainPath) {
//        String separator = System.getProperty("file.separator");
//        File excel = new File(mainPath + separator + FileType.EXCEL.toString() + separator + TEMPLATE);
        File excel = new File(mainPath/* + "/" + TEMPLATE + "/" + FileType.EXCEL.toString()*/);
        if (!excel.exists()) {
            excel.mkdirs();
        }
    }

    /**
     * 
     * 描述：获取数据导出配置信息
     * @author hanbin
     * @param id
     * @param mainPath
     * @return
     * @throws Exception
     */
    public DataExportConfig getConfigWithIDandMainPath(String id, String mainPath) throws Exception {
        if (0 >= this.configs.size()) {
            this.buildConfig(mainPath);
        }
        DataExportConfig result = this.configs.get(id);
        // 开始基础的验证
        if (null == result.getDataSources()) {
            throw new Exception("未配置数据源.");
        }
        return result;
    }

    /**
     * 根据ID解析对应的配置信息
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public void buildConfig(String mainPath) throws Exception {
//        String separator = System.getProperty("file.separator");
    	String configPath = this.getClass().getResource(CONFIG_PROPERTIES_NAME).getPath();
    	File pfile = new File(configPath);
    	System.out.println(configPath);
    	if(!pfile.exists() || pfile.isFile()){
    		throw new FileNotFoundException("配置文件没找到");
    	}
    	// 根据文件后缀获取配置文件
    	File[] pers = pfile.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().toLowerCase().endsWith("properties");
			}
		});
    	
    	for(File f : pers){
    		
    		Properties configPro = new Properties();
    		InputStream is = new FileInputStream(f);
    		configPro.load(is);
    		
    		Enumeration<String> e = (Enumeration<String>) configPro.propertyNames();
    		String rootStr = "";
  	        while(e.hasMoreElements()){
  	        	String properName = e.nextElement();
  	        	if(properName.startsWith("export.root")){
  	        		rootStr = (String) configPro.get(properName);
//  	        		System.out.println(rootStr);
  	        		break;
  	        	}else{
  	        		continue;
  	        	}
  	        }
	        if (null == rootStr) {
	            throw new Exception("未配置根,[export.root]缺失.");
	        }else{
	        String[] roots = rootStr.split(",");
	        for (String id : roots) {
	        	if(id == null || id.equals("")){
	        		continue;
	        	}
	            DataExportConfig dataExportConfig = new DataExportConfig();
	            dataExportConfig.setId(id);
	            // 解析模板配置
	            String supportStr = configPro.getProperty("export.conf." + id + ".support");
	            if (null == supportStr) continue;
	            String[] support = supportStr.split(",");
	            dataExportConfig.setSupportType(support);
	            Map<String, DataExportTemplateConfig> templates = new HashMap<String, DataExportTemplateConfig>();
	            for (String supportType : support) {
	                DataExportTemplateConfig dataExportTemplateConfig = new DataExportTemplateConfig();
	                dataExportTemplateConfig.setTemplateType(supportType);
	                String templatePath = configPro.getProperty("export.conf." + id + "." + supportType + ".template");
	                dataExportTemplateConfig.setRelativePath(templatePath);
	                String outFilename = configPro.getProperty("export.conf." + id + "." + supportType + ".outFileName");
	                dataExportTemplateConfig.setOutFileName(outFilename);
	                Boolean multipleSheets = Boolean.valueOf(configPro.getProperty("export.conf." + id + "." + supportType + ".MultipleSheets"));
	                dataExportTemplateConfig.setMultipleSheets(multipleSheets);
	                Boolean multipleExcels = Boolean.valueOf(configPro.getProperty("export.conf." + id + "." + supportType + ".multipleExcels"));
	                dataExportTemplateConfig.setMultipleExcels(multipleExcels);
	                templates.put(supportType, dataExportTemplateConfig);
	            }
	            dataExportConfig.setTemplates(templates);
	            // 数据源解析
	            String dataSourceStr = configPro.getProperty("export.conf." + id + ".dataSource");
	            String[] dataSourceNames = dataSourceStr.split(",");
	            dataExportConfig.setDataSourceNames(dataSourceNames);
	            Map<String, DataExportDataSource> dataSources = new HashMap<String, DataExportDataSource>();
	            for (String dataSourceName : dataSourceNames) {
	                DataExportDataSource ds = new DataExportDataSource();
	                ds.setId(dataSourceName);
	                // 配置的myBatis的ID
	                String selectID = configPro.getProperty("export.conf." + id + ".dataSource." + dataSourceName + ".id");
	                ds.setSelectID(selectID);
	                String sheetNameAtt = configPro.getProperty("export.conf." + id + ".dataSource." + dataSourceName + ".sheetNameAtt");
	                ds.setSheetNameAtt(sheetNameAtt);
	                // String paramClass = configPro.getProperty("export.conf." + id + ".dataSource." + dataSourceName + ".paramClass");
	                // dataExportDataSource.setParamClass(paramClass);
	                String isCollection = configPro.getProperty("export.conf." + id + ".dataSource." + dataSourceName + ".result.list");
	                ds.setCollection(Boolean.valueOf(isCollection));
	                // 检查是否存在子数据源
	                String child = configPro.getProperty("export.conf." + id + ".dataSource." + dataSourceName + ".child");
	                if (null != child && 0 < child.length()) {
	                    List<DataExportDataSource> cDSList = new ArrayList<DataExportDataSource>();
	                    for (String cID : child.split(",")) {
	                        DataExportDataSource chDS = new DataExportDataSource();
	                        chDS.setId(cID);
	                        String cSelectID = configPro.getProperty("export.conf." + id + ".dataSource." + dataSourceName + ".child." + cID + ".id");
	                        chDS.setSelectID(cSelectID);
	                        String conditionAttribute = configPro.getProperty("export.conf." + id + ".dataSource." + dataSourceName + ".child." + cID + ".conditionAttribute");
	                        chDS.setConditionAttribute(conditionAttribute);
	                        String cSheetNameAtt = configPro.getProperty("export.conf." + id + ".dataSource." + dataSourceName + ".child." + cID + ".sheetNameAtt");
	                        chDS.setSheetNameAtt(cSheetNameAtt);
	                        String cIsCollection = configPro.getProperty("export.conf." + id + ".dataSource." + dataSourceName + ".child." + cID + ".result.list");
	                        chDS.setCollection(Boolean.valueOf(cIsCollection));
	                        String addAttribute = configPro.getProperty("export.conf." + id + ".dataSource." + dataSourceName + ".child." + cID + ".addAttribute");
	                        chDS.setAddAttribute(addAttribute);
	                        cDSList.add(chDS);
	                    }
	                    ds.setChild(cDSList);
	                }
	                dataSources.put(dataSourceName, ds);
	            }
	            dataExportConfig.setDataSources(dataSources);
	            //
	            
	            this.configs.put(id, dataExportConfig);
	        }
	        StreamUtils.closeInStream(is);
    	}
    	}
//    	System.out.println(configs);
    }
    
    public static void main(String[] args) throws Exception {
		ConfigServiceImpl configServiceImpl = new ConfigServiceImpl();
		configServiceImpl.buildConfig("");
	}
    
}
