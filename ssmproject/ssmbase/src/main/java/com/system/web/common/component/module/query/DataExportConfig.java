package com.system.web.common.component.module.query;

import java.util.Map;

/**
 * 导出数据的配置对象
 * 
 * @author lxxccc
 * @version 2011-12-20
 */
public class DataExportConfig {
    /** 配置的ID */
    private String id;

    /** 需要导出的类型 */
    private String type;

    /** 支持的导出类型 */
    private String[] supportType;

    /** 模板配置, key对应于supportType */
    private Map<String, DataExportTemplateConfig> templates;

    /** 数据源名称 */
    private String[] dataSourceNames;

    /** 数据源配置, key对应dataSourceNames */
    private Map<String, DataExportDataSource> dataSources;

    /**
     * 根据需要到处的文件类型筛选对应的模板配置
     * 
     * @param type
     * @return
     */
    public DataExportTemplateConfig getDataExportTemplateConfigWithType(String type) {
        DataExportTemplateConfig result = null;
        for (String tempLateKey : this.templates.keySet()) {
            if (tempLateKey.toUpperCase().equals(type.toUpperCase())) {
                result = this.templates.get(tempLateKey);
                break;
            }
        }
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getSupportType() {
        return supportType;
    }

    public void setSupportType(String[] supportType) {
        this.supportType = supportType;
    }

    public Map<String, DataExportTemplateConfig> getTemplates() {
        return templates;
    }

    public void setTemplates(Map<String, DataExportTemplateConfig> templates) {
        this.templates = templates;
    }

    public String[] getDataSourceNames() {
        return dataSourceNames;
    }

    public void setDataSourceNames(String[] dataSourceNames) {
        this.dataSourceNames = dataSourceNames;
    }

    public Map<String, DataExportDataSource> getDataSources() {
        return dataSources;
    }

    public void setDataSources(Map<String, DataExportDataSource> dataSources) {
        this.dataSources = dataSources;
    }
}
