package com.system.web.common.component.service.commonQuery.exporter;

import com.system.web.common.component.module.query.DataExportConfig;

/**  
 * @Title: ConfigService.java 
 * @Package com.yunhan.scc.trading.web.service.CommonQuery.exporter 
 * 配置处理器, 用于导出文件的基础配置信息的处理准备
 * @author wt
 * @date 2015-6-15 上午11:53:59 
 * @version V0.1  
 */
public interface ConfigService {

	 /**
     * 获取数据导出的主目录
     * 
     * @return
     * @throws Exception
     */
    public String getDataExportMainPath() throws Exception;

    /**
     * 
     * 描述：获取模板路径
     * @author hanbin
     * @return
     * @throws Exception
     */
	public String getTemplatePath() throws Exception;

    /**
     * 根据主目录检查是否存在对应的目录, 如果不存在就创建
     * 
     * @param mainPath
     */
    public void checkPath(String mainPath);

    /**
     * 
     * 描述：获取数据导出配置信息
     * @author hanbin
     * @param id
     * @param mainPath
     * @return
     * @throws Exception
     */
    public DataExportConfig getConfigWithIDandMainPath(String id, String mainPath) throws Exception;

    /**
     * 根据ID解析对应的配置信息
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public void buildConfig(String mainPath) throws Exception;
}
