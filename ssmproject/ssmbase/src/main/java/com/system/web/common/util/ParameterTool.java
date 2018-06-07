package com.system.web.common.util;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.system.web.common.exception.ParameterException;

/**
 * 
 * @author pangzhenhua
 * @version created at 2015年11月27日 下午4:29:50
 */
public class ParameterTool{
	public static final Logger log = Logger.getLogger(ParameterTool.class);
	private static Resource[] resources;
	private static ConcurrentMap<String, String> constantMap = new ConcurrentHashMap<>();
	
	private static final String LOG001 = "开始加载参数配置";
	private static final String LOG002 = "文件={0}中的key值={1}未能加载，因为和已有的key值发生了冲突";
	private static final String LOG003 = "读取文件={0}时发生错误";
	private static final String Log004 = "参数配置加载完毕，共{0}个常量";
	
	public ParameterTool(){
		
	}
	
	public static void init() throws ParameterException{
		//edit by yangtao  2016-4-13;
//		if(null != constantMap && !constantMap.isEmpty()){
//			return;
//		}
//		constantMap = 
				loadParameter();
		
	}
	
	private static ConcurrentMap<String, String> loadParameter() throws ParameterException{
		String filePath = null;
		ConcurrentHashMap<String, String> tmpConstantsMap = new ConcurrentHashMap<>();
		
		log.info(LOG001);
		
		if( (null != resources) && (resources.length != 0) ){
			for(Resource resource : resources){
				try {
					filePath = resource.getURL().toString();
					Properties prop = PropertiesLoaderUtils.loadProperties(resource);
					for(Entry<Object, Object> entry : prop.entrySet()){
						if(tmpConstantsMap.containsKey(entry.getKey())){
							throw new ParameterException(MessageFormat.format(LOG002, filePath, entry.getKey()));
						}
						tmpConstantsMap.put((String)entry.getKey(), (String)entry.getValue());
					}
				} catch (IOException e) {
					log.error(MessageFormat.format(LOG003, filePath));
				}
			}
		}
		
		log.info(MessageFormat.format(Log004, tmpConstantsMap.size()));
		constantMap.putAll(tmpConstantsMap);
		return tmpConstantsMap;
	}
	
	public static String getParameter(String key){
		String result = null;
		if( null != key && key.length() > 0){
			String strValue = constantMap.get(key);
			if(null != strValue){
				result = strValue.trim();
			}
		}
		return result;
	}

	public static Resource[] getResources() {
		return resources;
	}

	public static void setResources(Resource[] resources) {
		ParameterTool.resources = resources;
	}

}
