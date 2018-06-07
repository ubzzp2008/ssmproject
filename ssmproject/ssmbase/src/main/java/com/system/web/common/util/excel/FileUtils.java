/*
 * @(#)FileUtils.java
 *
 * Copyright 2012 vision, Inc. All rights reserved.
 */

package com.system.web.common.util.excel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import com.system.web.common.util.StreamUtils;

/**
 * 文件操作工具类
 * @author Administrator
 *
 */
public class FileUtils {
	private static Logger logger = Logger.getLogger(FileUtils.class);

	/**
	 * 文件复制
	 * YangTao 于 2016-3-1 修改实现
	 * @param fileFrom 文件路径，这里这能是文件的全路径，不能复制文件夹
	 * @param fileTo 目标路径文件夹全路径
	 * @return  拷贝是否成功
	 */
	public static boolean copy(String fileFrom, String fileTo) {
		FileOutputStream out = null;
		File file = new File(fileTo);
		if(!file.exists()){
			file.getParentFile().mkdirs();
		}
		try {
			Files.copy(Paths.get(fileFrom), out);
			StreamUtils.closeOutStream(out);
			return true;
		} catch (IOException e1) {
			logger.error(e1);
			return false;
		}
		
	}
	

	public static boolean copyFileAndDir(String fileFrom, String fileTo) {
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(fileFrom);
			File temp = new File(fileTo);
			if (!temp.exists()) {
				File dir = new File(fileTo.substring(0, fileTo.lastIndexOf("/")));
				dir.mkdirs();
			}
			out = new FileOutputStream(fileTo);
			byte[] bt = new byte[1024];
			int count;
			while ((count = in.read(bt)) > 0) {
				out.write(bt, 0, count);
			}
			return true;
		} catch (IOException ex) {
			logger.error(ex);
			return false;
		} finally {
			StreamUtils.closeInStream(in);
			StreamUtils.closeOutStream(out);
		}
	}

	/**
	 * 删除文件(只能删除文件,不能删除目录)
	 * 
	 * @param filePath
	 *            文件路径
	 */
	public static void delteFile(String filePath) {
		File file = new File(filePath);
		if (file.isFile() && file.exists()) {
			file.delete();
		}
	}

	/**
	 * 移动文件
	 * 
	 * @param recourseDir
	 *            源文件所在的目录
	 * @param fileName
	 *            文件名
	 * @param targetDir
	 *            目标文件躲在的目录
	 * @param datePath 是否按时间显示
	 * @return 返回文件完整路径
	 */
	public static String moveFile(String recourseDir, String fileName, String targetDir, boolean datePath) {
		String targetPath = targetDir + fileName;
		if (datePath) {
			DateFormat format = new SimpleDateFormat("yyyyMMdd");
			String path = format.format(Calendar.getInstance().getTime());
			targetPath = targetDir + path + "/" + fileName;
		}
		copy(recourseDir + fileName, targetPath);
//		delteFile(recourseDir + fileName);
		int index_ = targetPath.indexOf("visionfile");
		if(index_ != -1){
			return targetPath.substring(index_+11);
		}
		return targetPath;
	}
	/**
	 * 文件打包工具，
	 * 说明：该工具只能对目录下的文件进行打包 （支持递归打包），支持对单个文件进行打包压缩
	 * @author YangTao 2016-2-29 下午5:34:04
	 * @param dir 输入文件路径
	 * @param outZipPathAndName 输出文件路径.请输入全路径
	 * @throws IOException
	 * @return void
	 */
	public static void GenzipFile(File dir,String outZipPathAndName) throws IOException {
		File outFile = new File(outZipPathAndName);
		if(!outFile.getParentFile().exists()){
			outFile.getParentFile().mkdirs();
		}
		
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFile));
		
		File[] files = new File[1];
		if(dir.isFile()){
			 files[0] = dir;
		}else{
			 files = dir.listFiles();
		}
		
		for (File file : files) {
			if(file.isDirectory()){
				continue;
			}
			out.putNextEntry(new ZipEntry(file.getName()));
			Files.copy(Paths.get(file.getAbsolutePath()), out);
			out.closeEntry();
		}
		StreamUtils.closeOutStream(out);
	}
	/**
	 * 删除文件及文件夹 递归删除
	 * @author YangTao 2016-2-29 下午5:45:47
	 * @param path 传入路径
	 */
	public static void delDirAndFile(String path){
		File file = new File(path);
		if(file.exists()){
			if(file.isDirectory()){
				File[] files = file.listFiles();
				for(File f : files){
					if(f.isFile()){
						f.delete();
					}else{
						delDirAndFile(f.getAbsolutePath());
					}
				}
				file.delete();
			}else{
				file.delete();
			}
		}
	}
	/**
	 * 读取文件中所有内容,最好文件中只有一行。多行的话，会连在一起。自己可能无法做分割
	 * @author YangTao 2016-3-8 上午9:18:31
	 * @param path 文件全路径，不能是文件夹路径
	 * @throws IOException
	 * @return String 文件中所有内容，文件不存在的情况返回null 在使用需要判断是否为null和"";
	 */
	public static String readStringFromFile(String path) throws IOException{
		File file = new File(path);
		if(file.exists() && file.isFile()){
			StringBuilder stringBuilder = new StringBuilder();
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String line = "";
			while((line = bufferedReader.readLine()) != null){
				stringBuilder.append(line);
			}
			bufferedReader.close();
			return stringBuilder.toString();
		}else{
			return null;
		}
	}
	/**
	 * 写字符串（JSON）到文件中
	 * @author YangTao 2016-3-8 上午9:27:32
	 * @param path 文件全路径
	 * @param context 要写入文件的内容
	 * @param append 是否追加 true 追加，false 不追加，注意不追加的情况会把文件中的所有内容清空.然后填入新内容
	 * @throws IOException 需要对异常情况进行处理
	 * @return boolean 好像没有什么卵用 
	 */
	public static boolean writeStringToFile(String path,String context,boolean append) throws IOException{
		File file = new File(path);
		if(!file.exists()){
			file.getParentFile().mkdirs();
		}
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,append));
		bufferedWriter.write(context);
		bufferedWriter.flush();
		bufferedWriter.close();
		return true;
	}
	
	public static void main(String[] args) {
		
	}
}
