package com.system.web.common.component.module.query;
/**
 * 
 * 描述：数据导出配置
 * @author hanbin
 * @date 2012-3-29 下午02:36:39
 * @version v3.0
 */
public class DataExportTemplateConfig {
	  /** 是否为多excel, 默认false */
	private Boolean multipleExcels = false;

    /** 是否为多sheet, 默认false */
    private Boolean multipleSheets = false;

    /** 模板类型 */
    private String templateType;
    
    /**最终导出的文件名*/
    private String outFileName;

    /** 模板的相对路径 */
    private String relativePath;

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public Boolean getMultipleSheets() {
        return multipleSheets;
    }

    public void setMultipleSheets(Boolean multipleSheets) {
        this.multipleSheets = multipleSheets;
    }

    public String getOutFileName() {
        return outFileName;
    }

    public void setOutFileName(String outFileName) {
        this.outFileName = outFileName;
    }

	public Boolean getMultipleExcels() {
		return multipleExcels;
	}

	public void setMultipleExcels(Boolean multipleExcels) {
		this.multipleExcels = multipleExcels;
	}
    
}
