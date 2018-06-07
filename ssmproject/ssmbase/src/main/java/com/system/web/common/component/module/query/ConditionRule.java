package com.system.web.common.component.module.query;
/**
 * 
 * 描述：条件规则
 * @author hanbin
 * @date 2012-3-29 下午02:36:00
 * @version v3.0
 */
public class ConditionRule {
    public String field;

    public String op;

    public String data;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
