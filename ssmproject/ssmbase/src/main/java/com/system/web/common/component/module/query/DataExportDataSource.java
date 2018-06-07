package com.system.web.common.component.module.query;

import java.util.List;

/**
 * 导出文件的数据源配置
 * 
 * @author lxxccc
 * 
 */
public class DataExportDataSource {

    private String id;

    private String selectID;

    // private String paramClass;

    private boolean isCollection;

    /** 作为sheetName的属性 */
    private String sheetNameAtt;
    
    /** 当数据源为子数据源的时候使用该属性作为与父数据对象的关联属性 */
    private String conditionAttribute;

    /** 当数据源为子数据源的时候使用该属性作为将子数据源查询出来的数据填充到父类的属性中 */
    private String addAttribute;

    private List<DataExportDataSource> child;

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean isCollection) {
        this.isCollection = isCollection;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSelectID() {
        return selectID;
    }

    public void setSelectID(String selectID) {
        this.selectID = selectID;
    }

    public List<DataExportDataSource> getChild() {
        return child;
    }

    public void setChild(List<DataExportDataSource> child) {
        this.child = child;
    }

    public String getConditionAttribute() {
        return conditionAttribute;
    }

    public void setConditionAttribute(String conditionAttribute) {
        this.conditionAttribute = conditionAttribute;
    }

    public String getAddAttribute() {
        return addAttribute;
    }

    public void setAddAttribute(String addAttribute) {
        this.addAttribute = addAttribute;
    }

    public String getSheetNameAtt() {
        return sheetNameAtt;
    }

    public void setSheetNameAtt(String sheetNameAtt) {
        this.sheetNameAtt = sheetNameAtt;
    }

}
