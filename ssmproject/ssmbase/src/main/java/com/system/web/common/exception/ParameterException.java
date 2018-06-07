package com.system.web.common.exception;

/**
 * 
 * @author pangzhenhua
 * @version created at 2015年11月30日 下午2:38:05
 */
public class ParameterException extends CommonRuntimeException{
	/** 
	 * 
	 */
	private static final long serialVersionUID = 923730661837094484L;

	public ParameterException(){
		super();
	}
	
	public ParameterException(String msg){
		super(msg);
	}
}
