package com.system.web.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * 流工具类 关闭输入输出流，简单封装
 * @Author YangTao
 * @Date 2016-1-30 下午5:35:26 
 * @Version 1.0
 * @Since
 */
public class StreamUtils {
	/**
	 * 关闭输入流
	 * @author YangTao 2016-1-30 下午5:36:25
	 * @param is
	 * @return void
	 */
	public static void closeInStream(InputStream is){
		if(is != null){
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 关闭输出流
	 * @author YangTao 2016-1-30 下午5:36:41
	 * @param os
	 * @return void
	 */
	public static void closeOutStream(OutputStream os){
		if(os != null){
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
