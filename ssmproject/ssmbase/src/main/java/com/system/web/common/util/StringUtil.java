/*
 * @(#)StringUtils.java
 *
 * Copyright 2013 vision, Inc. All rights reserved.
 */

package com.system.web.common.util;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * 字符串工具类
 * 
 * @author Administrator
 * 
 */
public class StringUtil extends StringUtils {

	/**
	 * 去除字符串中的html标签，返回指定的长度
	 */
	public static String htmlTagFilter(String inputStr, int len) {
		if (inputStr == null || "".equals(inputStr.trim())) {
			return "";
		}
		String outStr = inputStr.replaceAll("\\&[a-zA-Z]{1,10};", "") // 去除类似&lt;
				.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "") // 去除开始标签及没有结束标签的标签
				.replaceAll("</[a-zA-Z]+[1-9]?>", ""); // 去除结束标签
		if (outStr.length() > len) {
			outStr = outStr.substring(0, len);
			// outStr += "......";
		}
		return outStr;
	}

	/**
	 * 
	 * @Description:将对象转换为字符串
	 * @MethodName :parseString
	 * @Author :wt
	 * @param obj
	 * @param value
	 * @return
	 * 
	 */
	public static String parseString(Object obj, String value) {
		String str = value;
		try {
			if (obj == null) {
				return str;
			} else {
				str = String.valueOf(obj);
			}
		} catch (Exception e) {
			return str;
		}
		return str;
	}

	/**
	 * 
	 * @Description:将字符转换为Long
	 * @param @param value
	 * @param @param obj
	 * @param @return   
	 * @return Long  
	 * @throws
	 * @author zzp
	 * @date 2015-2-4 上午9:02:40
	 */
	public static Long parseToLong(String value, Long obj) {
		if (value == null) {
			return obj;
		} else {
			if (org.apache.commons.lang.StringUtils.isNotEmpty(value.trim())) {
				return Long.parseLong(value);
			} else {
				return obj;
			}
		}
	}

	/**
	 * 
	 * @Description: 将字符转换为Double
	 * @param @param value
	 * @param @param obj
	 * @param @return   
	 * @return Double  
	 * @throws
	 * @author zzp
	 * @date 2015-2-4 上午9:03:17
	 */
	public static Double parseToDouble(String value, Double obj) {
		if (value == null) {
			return obj;
		} else {
			if (org.apache.commons.lang.StringUtils.isNotEmpty(value.trim())) {
				return Double.parseDouble(value);
			} else {
				return obj;
			}
		}
	}

	/**
	 * 
	 * @Description: 将字符转换为BigDecimal
	 * @param @param value
	 * @param @param obj
	 * @param @return   
	 * @return BigDecimal  
	 * @throws
	 * @author zzp
	 * @date 2015-2-4 上午9:03:28
	 */
	public static BigDecimal parseToBigDecimal(String value, BigDecimal obj) {
		if (value == null) {
			return obj;
		} else {
			// if (org.apache.commons.lang.StringUtils.isNotEmpty(value.trim()))
			// {
			if (NumberUtils.isNumber(value.trim())) {
				return new BigDecimal(value);
			} else {
				return obj;
			}
		}
	}

	/**
	 * 
	
	* @Title: clobToString 
	
	* @Description: TODO(将数据库中clob类型转换为string类型) 
	
	* @param @param clob
	* @param @return    设定文件 
	
	* @return String    返回类型 
	
	* @throws
	 */
	public static String clobToString(Clob clob) {
		if (clob == null) {
			return null;
		}
		try {
			Reader inStreamDoc = clob.getCharacterStream();

			char[] tempDoc = new char[(int) clob.length()];
			inStreamDoc.read(tempDoc);
			inStreamDoc.close();
			return new String(tempDoc);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException es) {
			es.printStackTrace();
		}
		return null;
	}

	//首字母转小写
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	//首字母转大写
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}

}
