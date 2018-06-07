
package com.system.web.common.util;


/**
 *
 * 类名称：BusinessException   
 * 类描述：  业务异常 
 * 创建人：lhd  
 * 创建时间：2015-5-29 下午4:29:59   
 * 修改人：
 * 修改时间： 
 * 修改备注：   
 * @version V0.1 
 *    
 */
public class BusinessException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4678047654284966002L;

	public BusinessException(String msg) 
    { 
        super(msg); 
    } 

}
