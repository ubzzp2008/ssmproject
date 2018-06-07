package com.system.web.common.component.service.commonQuery.exporter;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title: ExportService.java
 * @Package com.yunhan.scc.trading.web.service.CommonQuery.exporter
 *          通查所属的文件导出管理器, 凡是界面上涉及到的导出,例如excel,pdf,txt导出,都交由该处理器处理.
 * @date 2015-6-15 上午11:58:56
 * @version V0.1
 */
public interface ExportService {

	public void export(String templateName);

	/**
	 * 描述：以传入的集合生成文件
	 * 
	 * @author lurf
	 * @param id
	 *            此导出的名字
	 * @param type
	 *            导出类型
	 * @param isPst
	 *            是否是持久文件
	 * @param templateName
	 *            模板名称
	 * @param fileName
	 *            生成的文件名，不加后缀
	 * @param beans
	 *            要生成文件的集合
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void export(String id, String type, boolean isPst,
			String templateName, String fileName, Map<String, Object> beans,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception;

	/**
	 * 描述：以传入的集合分页生成sheet文件。 传入的beans会按limit的值被分成多个sheet页。
	 * 在模板中通过map.get('list')代码获取被分在当前页的子集合，再对子集合迭代输出。
	 * sheetNameTemplate参数指定每页名字的开头部分
	 * ，如：sheetNameTemplate=data，那么每个sheet页的名字就是data1，data2，data3...
	 * 如果为null，那么每个sheet页的名字就是Sheet1，Sheet2，Sheet3...<br>
	 * <br>
	 * <i>注意：这个方法和{@link exportToMultipleSheets}方法不一样，此方法适用于一页是一个List集合的情况，
	 * {@link exportToMultipleSheets}适用于一页是一个对象的情况。</i>
	 * 
	 * @param id
	 *            此导出的名字
	 * @param type
	 *            导出类型
	 * @param isPst
	 *            是否是持久文件
	 * @param templateName
	 *            模板名称
	 * @param fileName
	 *            生成的文件名，不加后缀
	 * @param sheetNameTemplate
	 *            sheet的名字模板
	 * @param beans
	 *            要生成文件的集合
	 * @param limit
	 *            每页的记录数
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author lurf
	 * @version v3.1
	 * @since v3.1
	 */
	public void exportToPageingSheets(String id, String type, boolean isPst,
			String templateName, String fileName, String sheetNameTemplate,
			List<Object> beans, int limit, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * 描述：以传入的集合生成多sheet文件。在模板中通过map获取集合里的对象<br>
	 * <br>
	 * <i>注意：这个方法和{@link exportToPageingSheets}方法不一样，此方法适用于一页是一个对象的情况，
	 * {@link exportToPageingSheets}适用于一页是一个List集合的情况。</i>
	 * 
	 * @author lurf
	 * @param id
	 *            此导出的名字
	 * @param type
	 *            导出类型
	 * @param isPst
	 *            是否是持久文件
	 * @param templateName
	 *            模板名称
	 * @param fileName
	 *            生成的文件名，不加后缀
	 * @param sheetNames
	 *            sheet的名字
	 * @param beansList
	 *            要生成文件的集合
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void exportToMultipleSheets(String id, String type, boolean isPst,
			String templateName, String fileName, List<String> sheetNames,
			List<Map<String, Object>> beansList, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * 基本是拷贝上面的方法， 多一个Map，放入供excel模板文件使用的参数
	 * 
	 * @author lvjie
	 * @param id
	 *            此导出的名字
	 * @param type
	 *            导出类型
	 * @param isPst
	 *            是否是持久文件
	 * @param templateName
	 *            模板名称
	 * @param fileName
	 *            生成的文件名，不加后缀
	 * @param sheetNames
	 *            sheet的名字
	 * @param beansList
	 *            要生成文件的集合
	 * @param otherEntries
	 *            供excel模板文件使用的额外参数
	 * @param request
	 * @param response
	 * @throws Exception
	 * @version 3.2
	 */
	@SuppressWarnings("unchecked")
	public void exportToMultipleSheets(String id, String type, boolean isPst,
			String templateName, String fileName, List<String> sheetNames,
			List<Map<String, Object>> beansList, HttpServletRequest request,
			HttpServletResponse response, Map otherEntries) throws Exception;

	/**
	 * 导出数据, response不为空则表示需要下载
	 * 
	 * @param id
	 *            配置文件配置的ID
	 * @param type
	 *            导出类型：EXCEL或PDF等
	 * @param mainPath
	 *            导出功能的工作路径
	 * @param fileName
	 *            导出文件的名字
	 * @param requestMap
	 *            导出参数
	 * @throws Exception
	 * @author lurf
	 */
	@SuppressWarnings("unchecked")
	public void exportFormQuery(String id, String type, boolean isPst,
			String mainPath, String fileName, Map<String, String> requestMap,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception;

	/**
	 * 
	 * 已报需求查询_门店 excel
	 * 
	 * @param request
	 * @param response
	 * @param datas
	 *            传入需要导出的list
	 * @param excelName
	 *            导出的excel名字
	 * @param fileURL 导出的excel模板地址
	 * @param dataName 数据名称 （待导出数据的关键字）
	 * @throws Exception
	 * @author yxp
	 * @param <E>
	 * @date 2015-7-13 20:58:51
	 */
	public <E> void exportExcelAppendinfo(HttpServletRequest request,
			HttpServletResponse response, List<E> datas,
			String excelName,String fileURL,String dataName) throws Exception;

	/**
	 * 导出数据, response不为空则表示需要下载 Map方式
	 * @author wangtao
	 * @version created at 2016-1-19 下午2:07:28
	 * @param id  配置文件配置的ID
	 * @param type 导出类型：EXCEL或PDF等
	 * @param mainPath 导出功能的工作路径
	 * @param fileName  导出文件的名字
	 * @param requestMap  导出参数
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void exportFormQueryByMap(String id, String type, boolean isPst,
			String mainPath, String fileName, Map<String, String> requestMap,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception;
}
