package com.system.web.common.util.excel;

import java.util.Map;

public interface ExeclBeanInterface {
	/**
	 * 从map中根据key获取value 在实现该接口的同时，
	 * 需要初始化用静态代码块来初始化map。参考 {@link Persion}
	 * @author YangTao 2016年1月30日 下午5:57:10
	 * @param key
	 * @return
	 * @return String
	 */
	public String getPerties(String key);
	/**
	 * 获取实体类的Map 该Map中保存属性和Execl中的标题 参考 {@link Persion}
	 * @author YangTao 2016年1月30日 下午5:59:47
	 * @return
	 * @return Map<String,String>
	 */
	public Map<String,String> getTileMap();
	
	public ExeclDataStatus hasError();
}
