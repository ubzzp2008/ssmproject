package com.system.web.common.component.module.query;

import java.util.Calendar;
import java.util.Date;

/**
 * 查询常数类
 * 
 * @author lxxccc
 * @version 2011-12-15
 */
public class QueryConstant {
    /** 请求的查询标识,对应iBatis的select节点的ID,同时iBatis的配置中需要一个以该ID开头,以"_qcount"结尾的总数统计查询配置. */
    public final static String QUERY_ID = "query_id";

    /** 请求的查询类型, 默认值为QUERY_WEB */
    public final static String QUERY_TYPE = "query_type";

    /** 是否进行查询优化,参数值yes/no,默认no.针对于大数量数据库的操作,需分页SQL的支持. */
    public final static String QUERY_EFFICIENCY = "query_efficiency";

    /** offset - 查询开始的条数 */
    public final static String OFFSET = "offset";

    /** limit - 查询结束的条数 */
    public final static String LIMIT = "limit";

    /** page - 需要查询的页数 */
    public final static String PAGE = "page";

    /** rows - 需要查询的条数 */
    public final static String ROWS = "rows";

    /** sortField - 需要排序的字段 */
    public final static String SORTFIELD = "sortField";

    /** sortRule - 排序方式 */
    public final static String SORTRULE = "sortRule";

    /**
     * 请求类型
     * 
     * @author lxxccc
     * 
     */
    public enum QueryType {
        JQGRID, WEB, GWT, EXPORT, UNKNOWN;
        public static QueryType getQueryType(String queryType) {
            for (QueryType type : QueryType.values()) {
                if (queryType.equals(type.toString())) {
                    return type;
                }
            }
            return QueryType.UNKNOWN;
        }
    }

    /**
     * 文件类型
     * 
     * @author lxxccc
     * 
     */
    public enum FileType {
        EXCEL, PDF, UNKNOWN;
        public static FileType getFileType(String fileType) {
            for (FileType type : FileType.values()) {
                if (fileType.equals(type.toString())) {
                    return type;
                }
            }
            return FileType.UNKNOWN;
        }
    }

    /**
     * 条件类型,包含三种,单条件/多条件/分组多条件
     * 
     * @author lxxccc
     * 
     */
    public enum ConditionType {
        SINGLE, MULTIPLE, GROUP, UNKNOWN;
        public static ConditionType getConditionType(String conditionType) {
            for (ConditionType type : ConditionType.values()) {
                if (conditionType.equals(type.toString())) {
                    return type;
                }
            }
            return ConditionType.UNKNOWN;
        }
    }

    /**
     * 分组操作类型
     * 
     * @author lxxccc
     * 
     */
    public enum QueryGroupOperation {
        AND, OR;
        public static QueryGroupOperation getQueryGroupOperation(String queryGroupOperation) {
            for (QueryGroupOperation type : QueryGroupOperation.values()) {
                if (queryGroupOperation.equals(type.toString())) {
                    return type;
                }
            }
            return QueryGroupOperation.AND;
        }
    }

    /**
     * 查询参数的提交解析类型
     * 
     * @author lxxccc
     * 
     */
    public enum QueryConditionParseType {
        dispersed, unified;
        public static QueryConditionParseType getQueryConditionSubmitType(String queryConditionParseType) {
            for (QueryConditionParseType type : QueryConditionParseType.values()) {
                if (queryConditionParseType.equals(type.toString())) {
                    return type;
                }
            }
            return QueryConditionParseType.unified;
        }
    }

    /**
     * 查询规则
     * 
     * @author lxxccc
     */
    public enum QueryRule {
    	// 等于
        eq {
            public String getRule() {
                return " = ";
            }

            public String getDescription() {
                return "equal";
            }

            public String createSingelCondition(String columnName, Class<?> fieldType, String searchString) {
            	if (searchString.equals("")) return null;
            	if (String.class == fieldType || Date.class == fieldType || Calendar.class == fieldType) {
            		return columnName + this.getRule() + "'" + searchString + "'";
            	} else {
            		return columnName + this.getRule() + searchString;
            	}
            }
        },
        // 不等于
        ne {
            public String getRule() {
                return " <> ";
            }

            public String getDescription() {
                return "not equal";
            }

            public String createSingelCondition(String columnName, Class<?> fieldType, String searchString) {
            	if (searchString.equals("")) return null;
            	if (String.class == fieldType || Date.class == fieldType || Calendar.class == fieldType) {
            		return columnName + this.getRule() + "'" + searchString + "'";
            	} else {
            		return columnName + this.getRule() + searchString;
            	}
            }
        },
        // 小于
        lt {
            public String getRule() {
                return " < ";
            }

            public String getDescription() {
                return "less";
            }

            public String createSingelCondition(String columnName, Class<?> fieldType, String searchString) {
            	if (searchString.equals("")) return null;
            	if (String.class == fieldType || Date.class == fieldType || Calendar.class == fieldType) {
            		return columnName + this.getRule() + "'" + searchString + "'";
            	} else {
            		return columnName + this.getRule() + searchString;
            	}
            }
        },
        // 小于等于
        le {
            public String getRule() {
                return " <= ";
            }

            public String getDescription() {
                return "less or equal";
            }

            public String createSingelCondition(String columnName, Class<?> fieldType, String searchString) {
            	if (searchString.equals("")) return null;
            	if (String.class == fieldType || Date.class == fieldType || Calendar.class == fieldType) {
            		return columnName + this.getRule() + "'" + searchString + "'";
            	} else {
            		return columnName + this.getRule() + searchString;
            	}
            }
        },
        // 大于
        gt {
            public String getRule() {
                return " > ";
            }

            public String getDescription() {
                return "greater";
            }

            public String createSingelCondition(String columnName, Class<?> fieldType, String searchString) {
            	if (searchString.equals("")) return null;
            	if (String.class == fieldType || Date.class == fieldType || Calendar.class == fieldType) {
            		return columnName + this.getRule() + "'" + searchString + "'";
            	} else {
            		return columnName + this.getRule() + searchString;
            	}
            }
        },
        // 大于等于
        ge {
            public String getRule() {
                return " >= ";
            }

            public String getDescription() {
                return "greater or equal";
            }

            public String createSingelCondition(String columnName, Class<?> fieldType, String searchString) {
            	if (searchString.equals("")) return null;
            	if (String.class == fieldType || Date.class == fieldType || Calendar.class == fieldType) {
            		return columnName + this.getRule() + "'" + searchString + "'";
            	} else {
            		return columnName + this.getRule() + searchString;
            	}
            }
        },
        // 首部包含
        bw {
            public String getRule() {
                return " LIKE ";
            }

            public String getDescription() {
                return "bigins with";
            }

            public String createSingelCondition(String columnName, Class<?> fieldType, String searchString) {
            	if (searchString.equals("")) return null;
            	return columnName + this.getRule() + "'" + searchString + "%'";
            }
        },
        // 首部不包含
        bn {
            public String getRule() {
                return " NOT LIKE ";
            }

            public String getDescription() {
                return "does not begin with";
            }

            public String createSingelCondition(String columnName, Class<?> fieldType, String searchString) {
            	if (searchString.equals("")) return null;
            	return columnName + this.getRule() + "'" + searchString + "%'";
            }
        },
        in {
            public String getRule() {
                return " IN ";
            }

            public String getDescription() {
                return "in";
            }

            public String createSingelCondition(String columnName, Class<?> fieldType, String searchString) {
            	if (searchString.equals("")) return null;
            	searchString = searchString.startsWith(",") ? searchString.substring(1) : searchString;
            	searchString = searchString.endsWith(",") ? searchString.substring(0, searchString.length()-1) : searchString;
            	if (String.class == fieldType || Date.class == fieldType) {
            		searchString = searchString.replaceAll(",", "','");
            		return columnName + this.getRule() + "('" + searchString + "')";
            	} else {
            		return columnName + this.getRule() + "(" + searchString + ")";
            	}
            }
        },
        // not in
        ni {
            public String getRule() {
                return " NOT IN ";
            }

            public String getDescription() {
                return "not in";
            }

            public String createSingelCondition(String columnName, Class<?> fieldType, String searchString) {
            	if (searchString.equals("")) return null;
            	searchString = searchString.startsWith(",") ? searchString.substring(1) : searchString;
            	searchString = searchString.endsWith(",") ? searchString.substring(0, searchString.length()-1) : searchString;
            	if (String.class == fieldType || Date.class == fieldType) {
            		searchString = searchString.replaceAll(",", "','");
            		return columnName + this.getRule() + "('" + searchString + "')";
            	} else {
            		return columnName + this.getRule() + "(" + searchString + ")";
            	}
            }
        },
        //尾部包含
        ew {
            public String getRule() {
                return " LIKE ";
            }

            public String getDescription() {
                return "ends with";
            }

            public String createSingelCondition(String columnName, Class<?> fieldType, String searchString) {
            	if (searchString.equals("")) return null;
            	return columnName + this.getRule() + "'%" + searchString + "'";
            }
        },
        // 尾部不包含
        en {
            public String getRule() {
                return " NOT LIKE ";
            }

            public String getDescription() {
                return "does not end with";
            }

            public String createSingelCondition(String columnName, Class<?> fieldType, String searchString) {
            	if (searchString.equals("")) return null;
            	return columnName + this.getRule() + "'%" + searchString + "'";
            }
        },
        // 中间部分包含
        cn {
            public String getRule() {
                return " LIKE ";
            }

            public String getDescription() {
                return "contains";
            }

            public String createSingelCondition(String columnName, Class<?> fieldType, String searchString) {
            	if (searchString.equals("")) return null;
            	return columnName + this.getRule() + "'%" + searchString + "%'";
            }
        },
        // 不包含
        nc {
            public String getRule() {
                return " NOT LIKE ";
            }

            public String getDescription() {
                return "does not contain";
            }

            public String createSingelCondition(String columnName, Class<?> fieldType, String searchString) {
            	if (searchString.equals("")) return null;
            	return columnName + this.getRule() + "'%" + searchString + "%'";
            }
        },
        // 正则表达表like
        rc {
            public String getRule() {
                return " REGEXP_LIKE";
            }

            public String getDescription() {
                return "regexp contains";
            }

            public String createSingelCondition(String columnName, Class<?> fieldType, String searchString) {
            	if (searchString.equals("")) return null;
            	searchString = searchString.startsWith(",") ? searchString.substring(1) : searchString;
            	searchString = searchString.endsWith(",") ? searchString.substring(0, searchString.length()-1) : searchString;
        		searchString = searchString.replaceAll(",", "|");
            	return this.getRule() + "(" +columnName + ",'" + searchString + "')";
            }
        },
        // is null
        nu {
            public String getRule() {
                return " IS NULL";
            }

            public String getDescription() {
                return "is null";
            }

            public String createSingelCondition(String columnName, Class<?> fieldType, String searchString) {
            	return columnName + this.getRule();
            }
        },
        // not null
        nn {
            public String getRule() {
                return " IS NOT NULL";
            }

            public String getDescription() {
                return "is not null";
            }

            public String createSingelCondition(String columnName, Class<?> fieldType, String searchString) {
            	return columnName + this.getRule();
            }
        },
        UNKNOWN {
            public String getRule() {
                return "NOT SUPPORT";
            }

            public String getDescription() {
                return "NOT SUPPORT";
            }

            public String createSingelCondition(String columnName, Class<?> fieldType, String searchString) {
            	return "NOT SUPPORT";
            }
        };
        
        public abstract String getRule();

        public abstract String getDescription();

        public abstract String createSingelCondition(String columnName, Class<?> fieldType, String searchString);

        public static QueryRule getQueryRule(String queryRule) {
            for (QueryRule rule : QueryRule.values()) {
                if (queryRule.equals(rule.toString())) {
                    return rule;
                }
            }
            return QueryRule.UNKNOWN;
        }
    }
}
