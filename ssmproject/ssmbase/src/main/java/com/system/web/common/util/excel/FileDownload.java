package com.system.web.common.util.excel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.omg.CORBA.SystemException;

/**
 * 描述: 文件下载                                          
 * @author： weichuandi                 
 * 创建日期： 2011-10-31 下午04:09:53 
 * @version: 2011-10-31 下午04:09:53
 */
public class FileDownload {
	private static Log log = LogFactory.getLog(FileDownload.class);

	/**
	 * 
	 * 描述：根据路径下载文件
	 * @author hanbin
	 * @param request
	 * @param response
	 * @param filePath
	 * @throws SystemException
	 */
	public static void download(HttpServletRequest request, 
			HttpServletResponse response,String filePath) throws SystemException{
		    String fileName=null;
		    if(filePath!=null){
		    	fileName=filePath.substring(filePath.lastIndexOf("/")+1);
		    }else {
				return;
			}
		    //设置响应头和保存文件名 
			response.setContentType("APPLICATION/OCTET-STREAM");   
			response.addHeader("Content-Disposition", "attachment;filename="+fileName);
			try {
				RequestDispatcher dis = request.getRequestDispatcher(filePath);
				if (dis != null) {
					dis.forward(request, response);
				}
				response.flushBuffer();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("下载文件时出现[" + e.toString() + "]异常");
			} finally {
				System.out.println(request.getRequestURI());
			}
	}
	
	/**
	 * 
	 * 描述: 根据路径下载文件
	 * @author hanbing
	 * @param request
	 * @param response
	 * @param filePath
	 * void
	 * @throws
	 * @version 3.0
	 */
	public static void downloadFile(HttpServletRequest request, 
			HttpServletResponse response,String filePath){
		InputStream fis = null;
		OutputStream toClient = null;
		try {
            // path是指欲下载的文件的路径。
            File file = new File(filePath);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
//            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            fis = new BufferedInputStream(new FileInputStream(filePath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            String fileName = new String(filename.getBytes("GBK"),"ISO8859_1");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Content-Length", "" + file.length());
            toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
			log.error("下载文件时出现[" + ex.toString() + "]异常");
        } finally {
    		try {
    			if (null != fis) {
					fis.close();
        		}
    			if (null != toClient) {
    				toClient.close();
    			}
			} catch (IOException e) {
			}
        }
	}
	
	/**
	 * 描述：指定下载文件名下载文件
	 * @param request
	 * @param response
	 * @param filePath
	 * @param downName
	 */
	public static void downloadFile(HttpServletRequest request, 
			HttpServletResponse response,String filePath,String downName){
		InputStream fis = null;
		OutputStream toClient = null;
		try {
            // path是指欲下载的文件的路径。
            File file = new File(filePath);
            // 取得文件名。
//            String filename = file.getName();
            // 取得文件的后缀名。
//            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            fis = new BufferedInputStream(new FileInputStream(filePath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            String fileName = new String(downName.getBytes("GBK"),"ISO8859_1");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Content-Length", "" + file.length());
            toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
			log.error("下载文件时出现[" + ex.toString() + "]异常");
        }finally{
    		try {
    			if (null != fis) {
					fis.close();
        		}
    			if (null != toClient) {
    				toClient.close();
    			}
			} catch (IOException e) {
			}
        }
	}
	
	/**
	 * 
	 * 描述: 根据路径删除文件
	 * @author hanbing
	 * @param filePath
	 * @return
	 * boolean
	 * @throws
	 * @version 3.0
	 */
	public static boolean deleteFile(String filePath){
		try {
			return Files.deleteIfExists(Paths.get(filePath));
		} catch (IOException e) {
			return false;
		}
    } 
}
