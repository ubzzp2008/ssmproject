package com.system.web.common.util.excel;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;



/**
 * 
*
* 类名称：PutMasterDataInExcel   
* 类描述：   
* 创建人：lm  
* 创建时间：2015-1-13 上午11:52:04   
* 修改人：
* 修改时间： 
* 修改备注：   
* @version V0.1 
*
 */
public class PutMasterDataInExcel {
	/**
	 * 
	 * 根据模板生成excel文件并下载
	 * @param @param fileUrl 模板文件路径
	 * @param @param beansData 需要写入的数据map bean
	 * @param String valueKey 待导出数据的关键字
	 * @return void  
	 * @throws
	 * @author lm
	 * @date 2015-1-13
	 */
	public static void createExcel(String fileUrl,Map<String,Object> beansData,OutputStream out,String valueKey) throws Exception{
		 try {
			 FileInputStream fiStream = new FileInputStream(fileUrl);
			 XLSTransformer transformer = new XLSTransformer();
//			 transformer.transformXLS(fiStream, beansData).write(out);
			 List masterDataSends = null;//(List)beansData.get("masterDataSends");
			 if(!org.apache.commons.lang.StringUtils.isEmpty(valueKey))
				 masterDataSends = (List)beansData.get(valueKey);
			 
			 
			 HSSFWorkbook workBook= (HSSFWorkbook)transformer.transformXLS(fiStream, beansData);
			 
			 if(masterDataSends != null && !masterDataSends.isEmpty()){
				 boolean multiFlag = masterDataSends.size() > 1;
				 setErrorCellColor(beansData,multiFlag);
			 }
			
			 workBook.write(out);
			 
			 fiStream.close();
		} catch (ParsePropertyException e) {
			throw e;
		}
	}
	/**
	 * 
	 * 根据模板生成excel文件并下载
	 * @param @param fileUrl 模板文件路径
	 * @param @param beansData 需要写入的数据map bean
	 * @return void  
	 * @throws
	 * @author lm
	 * @date 2015-1-13
	 */
	public static void createExcelUtil(String fileUrl,Map<String,Object> beansData,OutputStream out) throws Exception{
		 try {
			 FileInputStream fiStream = new FileInputStream(fileUrl);
			 XLSTransformer transformer = new XLSTransformer();
			 transformer.transformXLS(fiStream, beansData).write(out);
			 fiStream.close();
		} catch (ParsePropertyException e) {
			throw e;
		}
	}
	
	private static void setErrorCellColor(Map<String,Object> beansData,boolean multiFlag) {
		 HSSFWorkbook workBook = (HSSFWorkbook)beansData.get("workbook");
		 HSSFCellStyle cellStyle = workBook.createCellStyle();
		 HSSFCellStyle preCellStyle = null;
		 HSSFSheet sheet = workBook.getSheetAt(0);//(HSSFSheet)beansData.get("sheet");//workBook.getSheetAt(0);
//		 HSSFPatriarch p=sheet.createDrawingPatriarch();
		 String cellValue = "";
		 // 只有一条记录则直接提取row的数据 
		 if(!multiFlag){
			 HSSFRow row = (HSSFRow)beansData.get("hssfRow");
			 if(row != null
					 && modifyStyleByError((HSSFRow)beansData.get("hssfRow"),cellValue,preCellStyle,cellStyle)){				 
			     return ;
			 }
		 }
		 for(int i=0;i<sheet.getLastRowNum();i++){
			 HSSFRow  row = sheet.getRow(i);
			 modifyStyleByError(row,cellValue,preCellStyle,cellStyle);
		 }
		
	}

	private static boolean modifyStyleByError(HSSFRow row,String cellValue, HSSFCellStyle preCellStyle,HSSFCellStyle cellStyle) {
		boolean hasProcess = false ;
		for(int j=0;j<row.getLastCellNum();j++){
			 HSSFCell cell = row.getCell(j);
			 if(cell == null) continue ;
			 cellValue = ParseExcel.getCellDataByType(cell);
//			 if(!StringUtils.isEmpty(cellValue) 
			 if(cellValue !=null 
					 && cellValue.indexOf("【error：") > -1 
					 && cellValue.indexOf("】") > -1){
				 cellValue = cellValue.replaceAll("【error：","");
				 cellValue = cellValue.replaceAll("】","");
				 hasProcess = true;
			 }else{
				 continue ;
			 }
			
			 preCellStyle = row.getCell(j).getCellStyle();
			 cellStyle.cloneStyleFrom(preCellStyle);
			 cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);	
			 cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);					 
			 row.getCell(j).setCellStyle(cellStyle);
			 cell.setCellValue(cellValue);	
//			 System.out.print(ParseExcel.getCellDataByType(cell)+"\t");
			 
//				 HSSFComment comment=p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
//				 comment.setString(new HSSFRichTextString("插件批注成功!插件批注成功!")); 
//				 
//				 row.getCell(j).setCellComment(comment); 
			 
//			
		 }
		return hasProcess;
		
	}
	
	
	
}
