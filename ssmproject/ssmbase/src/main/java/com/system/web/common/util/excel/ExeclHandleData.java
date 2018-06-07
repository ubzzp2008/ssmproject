package com.system.web.common.util.excel;

/**
 * 重要说明： 处理Execl接口，因为我们这里处理的Execl不只一个，
 * 文件要求的模板不一样，我们需要根据内容生成相应的bean。然后Java其他地方
 * 可以直接使用的bean的方式使用数据。之所以要这样处理，我们有这个接口的话，
 * 我们可以少用一次循环。而且处理数据的方式也被封装位方法。有其他需求实现这个接口就行了。
 * 实现例子参考{@link ExcelUtil}
 * @Author YangTao
 * @Date 2016年1月29日 上午10:00:45 
 * @return 返回错误代码
 * @Version
 * @Since
 */
public interface ExeclHandleData {
	
	public ExeclBeanInterface handleData(ExeclBeanInterface beanInterface,ExeclBeanInterface oldbean) throws Exception;
	
	
}
