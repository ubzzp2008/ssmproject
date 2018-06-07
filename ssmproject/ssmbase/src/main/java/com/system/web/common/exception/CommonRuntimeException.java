package com.system.web.common.exception;

/**
 * 通用runtime exception
 * @author pangzhenhua
 * @version created at 2016年2月24日 下午7:52:44
 */
public class CommonRuntimeException extends RuntimeException{

	/** 
	 * 
	 */
	private static final long serialVersionUID = -1506675617871002944L;

	public CommonRuntimeException(){
		super();
	}
	
	public CommonRuntimeException(String msg){
		super(msg);
	}
}
