package com.system.web.common.component.module.query;

import java.util.List;

/**
 * json对应的过滤条件对象
 * 
 * @author lxxccc
 * 
 */
public class ConditionFilter {
    public String groupOp;

    List<ConditionRule> rules;

    public String getGroupOp() {
        return groupOp;
    }

    public void setGroupOp(String groupOp) {
        this.groupOp = groupOp;
    }

    public List<ConditionRule> getRules() {
        return rules;
    }

    public void setRules(List<ConditionRule> rules) {
        this.rules = rules;
    }
}
