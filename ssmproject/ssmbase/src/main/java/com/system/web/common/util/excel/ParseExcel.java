package com.system.web.common.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;


/**
 * 
*    
* 项目名称：yunhan_article   
* 类名称：ParseExcel   
* 类描述：   解析表头
* 创建人：fxl  
* 创建时间：2015-1-6 上午10:10:58   
* 修改人： zhangke
* 修改时间：  2015.01.13
* 修改备注：   补充按区域获取EXCEL数据方法
* @version    
*
 */
public class ParseExcel {
	
	public static final String STR_FIRST_ROW = "firstRow";
	public static final String STR_LAST_ROW = "lastRow";
	public static final String STR_FIRST_COLUMN = "firstColumn";
	public static final String STR_LAST_COLUMN = "lastColumn";
	public static final String STR_FIRST_ROW_CELL_NUM = "first_row_cell_num";
	
	/**
	 * 
	
	* @Title: AnalyticalExcel 
	
	* TODO(按列 按行解析表头字段入口) 
	
	* @param @param fileAddr
	* @param @param stata
	* @param @param start
	* @param @param end
	* @param @return    设定文件 
	
	* @return List    返回类型 
	
	* @throws
	 */
	public static List AnalyticalExcel(String fileAddr,Integer stata,String start ,String end) {
		 List list = new ArrayList();//创建一个返回list
		try {
			 //获取文件流
		      POIFSFileSystem pois = new POIFSFileSystem(new FileInputStream(new File(fileAddr)));
		      //新建WorkBook
		      HSSFWorkbook wb = new HSSFWorkbook(pois);
			  //获取Sheet（工作薄）总个数
			  int sheetNumber = wb.getNumberOfSheets();
		      //获取Sheet（工作薄）
	           HSSFSheet sheet = wb.getSheetAt(0);//此处只获取第一个，不管多少
	           //开始行数
	           int firstRow = sheet.getFirstRowNum();
	           //结束行数
	           int lastRow = sheet.getLastRowNum();
	           //判断该Sheet（工作薄)是否为空
	     /*      boolean isEmpty = false;
	           if(firstRow == lastRow){
	               isEmpty = true;
	              } 
	           if(!isEmpty){*/
	        	   if(stata==1){//按行解析出表头
	        		   list= rowAnalytical(sheet, start , end);	        		   
	        	   }if(stata==2){//按列解析出表头
	        		   list= columnAnalytical(sheet, start , end);
	        	   }
	          //    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 
	
	* @Title: rowAnalytical 
	
	* TODO(按行解析出表头字段) 
	
	* @param @return    设定文件 
	
	* @return String    返回类型 
	
	* @throws
	 */
	private static List rowAnalytical( HSSFSheet sheet,String strbegin,String end) {
		  CellReference crbegin = new CellReference(strbegin);//开始
		  CellReference crend = new CellReference(end);//结束
		/*  CellReference cre = new CellReference("B5");//结束
		  System.out.println(cre.getRow() 4+"----------------"+cre.getCol() 1);*/
     	  //获取一行
          HSSFRow row = sheet.getRow(crbegin.getRow());
          //开始列数 
          int firstCell = row.getFirstCellNum();
          //结束列数
          int lastCell = row.getLastCellNum();
          List list = new ArrayList();//创建个返回数据的list
          int beginjiexi=crbegin.getCol();
          int endjiexi = crend.getCol()+1;
          //判断该行是否为空
          if(firstCell != lastCell){
        		  for(int k=beginjiexi;k<endjiexi;k++){
       		   //获取一个单元格
                      HSSFCell cell = row.getCell(k);
                      // cell为空 判断。 add by zhangke on 2015.04.08
                      if(cell == null) break;
                      Object value = null;
                     //  类型说明
                      //  CELL_TYPE_NUMERIC 数值型 0
                      //  CELL_TYPE_STRING 字符串型 1
                      //  CELL_TYPE_FORMULA 公式型 2
                      //  CELL_TYPE_BLANK 空值 3
                      //  CELL_TYPE_BOOLEAN 布尔型 4
                      //  CELL_TYPE_ERROR 错误 5
                     //获取单元格，值的类型
                      int cellType = cell.getCellType();
                      if(cellType == 0){
                           value = cell.getNumericCellValue();
                         }else if(cellType == 1){
                           value = cell.getStringCellValue();
                         }else if(cellType == 2){
                           value = cell.getStringCellValue();
                         }else if(cellType == 4){
                           value = cell.getBooleanCellValue();
                          } ;
                      list.add(value);
       	   }
       	   }
		return list;
	}
	/**
	 * 
	
	* @Title: columnAnalytical 
	
	* TODO(按列解析出表头字段) 
	
	* @param @param sheet
	* @param @param strbegin
	* @param @param end
	* @param @return    设定文件 
	
	* @return List    返回类型 
	
	* @throws
	 */
	private static List columnAnalytical( HSSFSheet sheet,String strbegin,String end){
		  CellReference crbegin = new CellReference(strbegin);//开始
		  CellReference crend = new CellReference(end);//结束
          List list=new ArrayList();
          //判断该Sheet（工作薄)是否为空
        for (int i = crbegin.getRow(); i <= crend.getRow(); i++) {
        	 //获取一行
            HSSFRow row = sheet.getRow(i);
            	 //获取一个单元格
                HSSFCell cell = row.getCell(crbegin.getCol());//从每行中取得此列
                Object value = null;
                //  类型说明
                //  CELL_TYPE_NUMERIC 数值型 0
                //  CELL_TYPE_STRING 字符串型 1
                //  CELL_TYPE_FORMULA 公式型 2
                //  CELL_TYPE_BLANK 空值 3
                //  CELL_TYPE_BOOLEAN 布尔型 4
                //  CELL_TYPE_ERROR 错误 5
               //获取单元格，值的类型
                int cellType = cell.getCellType();
                if(cellType == 0){
                     value = cell.getNumericCellValue();
                   }else if(cellType == 1){
                     value = cell.getStringCellValue();
                   }else if(cellType == 2){
                     value = cell.getStringCellValue();
                   }else if(cellType == 4){
                     value = cell.getBooleanCellValue();
                    } 
                list.add(value);
        }
		return list;
	}
	
	/**
	 * 
	
	* @Title: parseExcel 
	
	* TODO(完整解析平台excel：根据平台路径获取及用户，取得字段行及区域关系的值) 
	
	* @param @param fileAddr    设定文件 
	
	* @return void    返回类型 
	
	* @throws
	 *//*
	public static List parseExcel(String fileAddr,String start ,String end,List<TDepartment> tDepartments) {
		 CellReference crbegin = new CellReference(start);//开始行
		  CellReference crend = new CellReference(end);//结束行
		  List list = new ArrayList();//返回list
		  try {
	      //获取文件流
	      POIFSFileSystem pois = new POIFSFileSystem(new FileInputStream(new File(fileAddr)));
	      //新建WorkBook
	      HSSFWorkbook wb = new HSSFWorkbook(pois);
	      //获取Sheet（工作薄）总个数
	      int sheetNumber = wb.getNumberOfSheets();
	      for (int i = 0; i < sheetNumber; i++) {
	           //获取Sheet（工作薄）
	           HSSFSheet sheet = wb.getSheetAt(i);
	           //开始行数
	           int firstRow = sheet.getFirstRowNum();
	           //结束行数
	           int lastRow = sheet.getLastRowNum();
	           //--------------获取需要解析的行数
	         //开始解析行
	           int kaishihang = crbegin.getRow();
	           //结束解析行
	           int jieshuhang = crend.getRow();
	           //-------------解析行数获取结束
	           
	           //判断该Sheet（工作薄)是否为空
	           boolean isEmpty = false;
	           if(firstRow == lastRow){
	               isEmpty = true;
	              } 
	           if(!isEmpty){
	               for (int j = kaishihang; j <= jieshuhang; j++) {
	               //获取一行
	               HSSFRow row = sheet.getRow(1);
	               //开始列数
	               int firstCell = row.getFirstCellNum();
	               //结束列数
	               int lastCell = row.getLastCellNum();
	               //----------------获取需要解析的列-----------
	               int kaishilie= crbegin.getCol();//开始列
	               int jieshulie = crend.getCol();//结束列
	               //----------------获取结束-----------
	               //判断该行是否为空
	               if(firstCell != lastCell){
	                  for (int k = kaishilie; k < lastCell; k++) {
	                	 CellReference charu = new CellReference("AK2");//需要带入区域关系，所以此处开始插入区域关系列为ak
	                	//获取一个单元格
	                	 HSSFCell cell = row.getCell(k);
	                      Object value = null;
	                	 if(k==charu.getCol()+1){
	                		 if(tDepartments!=null){
	                			 for(int y=0;y<tDepartments.size();y++){
		                    		 list.add("采购商折扣");
		                    		 list.add("建议订数");
	                			 } 
	                		 }
	                		 cell.setCellValue("");
	                	 }
	                	 if(k==charu.getCol()+2){
	                		 cell.setCellValue("");
	                	 }
	                	 //  类型说明
	                      //  CELL_TYPE_NUMERIC 数值型 0
	                      //  CELL_TYPE_STRING 字符串型 1
	                      //  CELL_TYPE_FORMULA 公式型 2
	                      //  CELL_TYPE_BLANK 空值 3
	                      //  CELL_TYPE_BOOLEAN 布尔型 4
	                      //  CELL_TYPE_ERROR 错误 5
	                      //获取单元格，值的类型
	                      int cellType = cell.getCellType();
	                      if(cellType == 0){
	                           value = cell.getNumericCellValue();
	                         }else if(cellType == 1){
	                           value = cell.getStringCellValue();
	                         }else if(cellType == 2){
	                           value = cell.getCellFormula();
	                         }else if(cellType == 4){
	                           value = cell.getBooleanCellValue();
	                          } 
	                      if(value!=""){
	                    	  list.add(value);
	                      }
	                      }      
	                   }
	                 }
	              }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	   }
		  return list;
	}  */
	/**
	 * 
	
	* @Title: parseExcel 
	
	* TODO(完整解析平台excel：根据上传路径获取及用户字段行的值) 
	
	* @param @param fileAddr    设定文件 
	
	* @return void    返回类型 
	
	* @throws
	 */
	public static List parseExcel2(String fileAddr,String start ,String end) {
		 CellReference crbegin = new CellReference(start);//开始行
		  CellReference crend = new CellReference(end);//结束行
		  List list = new ArrayList();//返回list
		  try {
	      //获取文件流
	      POIFSFileSystem pois = new POIFSFileSystem(new FileInputStream(new File(fileAddr)));
	      //新建WorkBook
	      HSSFWorkbook wb = new HSSFWorkbook(pois);
	      //获取Sheet（工作薄）总个数
	      int sheetNumber = wb.getNumberOfSheets();
	      for (int i = 0; i < sheetNumber; i++) {
	           //获取Sheet（工作薄）
	           HSSFSheet sheet = wb.getSheetAt(i);
	           //开始行数
	           int firstRow = sheet.getFirstRowNum();
	           //结束行数
	           int lastRow = sheet.getLastRowNum();
	           //--------------获取需要解析的行数
	       /*  //开始解析行
	           int kaishihang = crbegin.getRow();
	           //结束解析行
	           int jieshuhang = crend.getRow();
	           //-------------解析行数获取结束*/	           
	           //判断该Sheet（工作薄)是否为空
	           boolean isEmpty = false;
	           if(firstRow == lastRow){
	               isEmpty = true;
	              } 
	           if(!isEmpty){
	               /*for (int j = kaishihang; j <= jieshuhang; j++) {*/
	               //获取一行
	               HSSFRow row = sheet.getRow(1);
	               //开始列数
	               int firstCell = row.getFirstCellNum();
	               //结束列数
	               int lastCell = row.getLastCellNum();
	               //----------------获取需要解析的列-----------
	               int kaishilie= crbegin.getCol();//开始列
	               int jieshulie = crend.getCol();//结束列
	               //----------------获取结束-----------
	               //判断该行是否为空
	               if(firstCell != lastCell){
	                  for (int k = kaishilie; k < lastCell; k++) {
	                	//获取一个单元格
	                	 HSSFCell cell = row.getCell(k);
	                      Object value = null;
	                	 //  类型说明
	                      //  CELL_TYPE_NUMERIC 数值型 0
	                      //  CELL_TYPE_STRING 字符串型 1
	                      //  CELL_TYPE_FORMULA 公式型 2
	                      //  CELL_TYPE_BLANK 空值 3
	                      //  CELL_TYPE_BOOLEAN 布尔型 4
	                      //  CELL_TYPE_ERROR 错误 5
	                      //获取单元格，值的类型
	                      int cellType = cell.getCellType();
	                      if(cellType == 0){
	                           value = cell.getNumericCellValue();
	                         }else if(cellType == 1){
	                           value = cell.getStringCellValue();
	                         }else if(cellType == 2){
	                           value = cell.getCellFormula();
	                         }else if(cellType == 4){
	                           value = cell.getBooleanCellValue();
	                          } 
	                    	  list.add(value);
	                      }      
	                   }
	                /* }*/
	              }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	   }
		  return list;
	}  
	
	/**
	 * 新增按区域提取数据方法 V1580
	
	* @Title: getDatasFromSheet 
	
	* TODO(按区域提取数据,如 A1到H5,读取A1单元格到H5单元格的矩形区域的数据) 
	
	* @param @param excelPath    	execel 文件路径
	* @param @param sheetIndex		需要读取的sheet索引,从0开始起
	* @param @param rangStart		待读取区域范围如：A1，读取A1单元格到rangEnd单元格的矩形区域的数据
	* @param @param rangEnd			待读取区域范围如：H5，读取rangStart单元格到H5单元格的矩形区域的数据
	* @param @return
	* @param @throws FileNotFoundException
	* @param @throws IOException    设定文件 
	
	* @return List<List<String>>    返回每行数据[行[单元格数据...]...]
	
	* @throws
	 */
	public static List<List<String>> getDatasFromSheet(String excelPath,int sheetIndex,String rangStart,String rangEnd) throws FileNotFoundException, IOException{
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(excelPath));
		List<List<String>> resData = new ArrayList<List<String>>();
		
		for (int k = 0; k < wb.getNumberOfSheets(); k++) {
			HSSFSheet sheet = wb.getSheetAt(k);
			resData = getZoneData(rangStart,rangEnd,sheet);
			break;
		}
		return resData;
	}
	/**
	 * 
	
	* @Title: getZoneData 
	
	* TODO(在指定区域内读取cell数据，按string类型提取) 
	
	* @param @param startCell
	* @param @param endCell
	* @param @param sheet
	* @param @return    设定文件 
	
	* @return List<List<String>>    返回类型 [行[单元格数据...]...]
	
	* @throws
	 */
	public static List<List<String>> getZoneData(String startCell,String endCell,HSSFSheet sheet){
		List<List<String>> datas = new ArrayList<List<String>>();
		
//		//检查数据的第一行是否有数据
//		if(!isCorrectFirstRowData(startCell, endCell, sheet))return null;
		
		//提取数据区域的属性
		Map<String,Integer> rowCellProps = getRowCellProp(startCell, endCell, sheet);
		
		//提取区域内容放入列表中
		for(int i = rowCellProps.get(STR_FIRST_ROW); i <= rowCellProps.get(STR_LAST_ROW)+1;i++){ 		//行
			try{
			List<String> cellDatas = new ArrayList<String>();
			if(sheet.getRow(i) == null) continue ;
			for(int j=rowCellProps.get(STR_FIRST_COLUMN); j <= rowCellProps.get(STR_LAST_COLUMN);j++){ 	//列
				if(sheet.getRow(i).getCell(j) == null) 
					cellDatas.add("");
				else {
					
//					cellDatas.add(sheet.getRow(i).getCell(j).getStringCellValue());
					cellDatas.add(getCellDataByType(sheet.getRow(i).getCell(j)));
					
					
				}
				
			}
			datas.add(cellDatas);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return datas;		
	}
	
	/**
	 * 按cell类型提取cell的值，返回值统一为String
	 */
	public static String getCellDataByType(HSSFCell cell) {
		String res = null ;
		switch(cell.getCellType())
		{case Cell.CELL_TYPE_BLANK :
//        	res = String.valueOf(cell.getCellType());
			res = "";
        	break; 
        case Cell.CELL_TYPE_BOOLEAN :
        	res = String.valueOf(cell.getBooleanCellValue());
            break; 
        case Cell.CELL_TYPE_ERROR :
        	res = String.valueOf(cell.getErrorCellValue());
            break; 
        case Cell.CELL_TYPE_FORMULA :
        	res = cell.getCellFormula();
            break; 
        case Cell.CELL_TYPE_NUMERIC :
        	double d = cell.getNumericCellValue();
        	java.text.NumberFormat nf = java.text.NumberFormat.getInstance(); 
        	nf.setGroupingUsed(false);        	
        	res = nf.format(d);
            break; 
        case Cell.CELL_TYPE_STRING :
        	if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue().trim())) 
        		res = cell.getStringCellValue(); 
            break; 
    	default:
    		res = cell.getRichStringCellValue().getString();
    }
		return res;
		
	}
	
	
		/**
		 * 检查数据区域的值是否为空
		 */
		private static boolean isCorrectFirstRowData(String startCell, String endCell,
				HSSFSheet sheet) {		
			return getRowCellProp(startCell, endCell, sheet).get(STR_FIRST_ROW_CELL_NUM)>0;
		}

		
		/**
		 * 
		
		* @Title: getRowCellProp 
		
		* TODO(获取的列数数据属性) 
		
		* @param @param startCell
		* @param @param endCell
		* @param @param sheet
		* @param @return    设定文件 
		
		* @return Map<String,Integer>    返回类型 
		
		* @throws
		 */
		private static Map<String,Integer> getRowCellProp(String startCell, String endCell,
				HSSFSheet sheet) {
			HSSFRow  firstRow = sheet.getRow(sheet.getFirstRowNum());
//			if(null == endCell || endCell.trim().equals(""))
//				endCell = getCellName(sheet.getLastRowNum()-1,firstRow.getLastCellNum()-1);
			if(null == endCell || endCell.trim().equals(""))
				endCell = getCellName(sheet.getLastRowNum(),firstRow.getLastCellNum()-1).replaceAll("\\$", "");
			
			Map<String,Integer> res = new HashMap<String,Integer> ();
			
			CellRangeAddress address = null;
			endCell = getValidEndCell(startCell,endCell);
			address = CellRangeAddress.valueOf(startCell+":"+endCell);
			
			res.put(STR_FIRST_ROW, address.getFirstRow());
			res.put(STR_LAST_ROW, address.getLastRow());
			res.put(STR_FIRST_COLUMN, address.getFirstColumn());
			res.put(STR_LAST_COLUMN, address.getLastColumn());
			res.put(STR_FIRST_ROW_CELL_NUM, address.getLastColumn());
		
			return res ;
		}

		/**
		 * 检查endCell是否小于startCell ，如果是则用startCell对应值修正endCell
		 * @author zhangke
		 */
		private static String getValidEndCell(String startCell, String endCell) {
			String startRow = startCell.replaceAll("[A-Za-z]+", "");
			String endRow = endCell.replaceAll("[A-Za-z]+", "");
//			String startColumn = startCell.replaceAll("[0-9]+", "");
			String endColumn = endCell.replaceAll("[0-9]+", "");
			try{
				if(Integer.parseInt(startRow) > Integer.parseInt(endRow)){
					endRow = startRow;
				}
//				if(startColumn.compareTo(endColumn) > 0){
//					endColumn = startColumn;
//				}	
			}catch(Exception e){
				e.printStackTrace();
			}
			return endColumn + endRow;
		}
		/**
		 * 按行列编号对应cell的名称 如:1:1 A1
		 */
		public static String getCellName(int row, int col) {
			if (row < 0) {
				throw new IllegalArgumentException("row: " + row);
			}
			if (col < 0) {
				throw new IllegalArgumentException("col: " + col);
			}
			return new CellReference(row, col).formatAsString();
		}
	
}
