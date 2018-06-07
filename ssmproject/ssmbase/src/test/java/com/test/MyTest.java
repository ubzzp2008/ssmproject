/**
 * 文件名：Test.java 版本信息： 日期：2014-10-17 Copyright 足下 Corporation 2014 版权所有 BIZ
 */
package com.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.system.web.common.util.PasswordUtil;

/**
 * @项目名称：myssh
 * @类名称：Test
 * @类描述：
 * @创建人：Justin.zhou
 * @创建时间：2014-10-17 下午3:41:44
 * @类说明：
 */
public class MyTest {
	private static Log log = LogFactory.getLog(MyTest.class);

	@Test
	public void md5test() {
		String string = PasswordUtil.getMD5("111111");
		log.info(string);
	}
	
	/**
	 * main(这里用一句话描述这个方法的作用)
	 * 
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static void main(String[] args) {
		
	}
	
}
