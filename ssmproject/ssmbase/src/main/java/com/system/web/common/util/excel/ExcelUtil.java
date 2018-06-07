package com.system.web.common.util.excel;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtil implements ExeclHandleData{

	/**
	 * 默认处理数据类,简单打印。
	 */
	@Override
	public ExeclBeanInterface handleData(ExeclBeanInterface beanInterface,ExeclBeanInterface oldbean) {
//		if(beanInterface instanceof EduMasterdataMarkeinfoDO){
//			//也可以设置前置条件，比如Execl中没有的属性，我们又需要的属性，可以用oldbean获取再设置
//			EduMasterdataMarkeinfoDO p =(EduMasterdataMarkeinfoDO)beanInterface;
////			System.out.println(p.getName());
//			//这里对数据做验证-然后处理
//			
//			
//		}else{
//			System.out.println("输入bean和强转bean出错");
//			return ExeclErrorCode.BEANERROR;
//		}
		return beanInterface;
	}
	
	/** 
	*  
	* 重新设置单元格计算公式 
	*  
	* */  
	public static void resetCellFormula(Workbook wb) {  
		FormulaEvaluator e = null;
		if(wb instanceof XSSFWorkbook){
			e = new XSSFFormulaEvaluator((XSSFWorkbook) wb);  
		}else if(wb instanceof HSSFWorkbook){
			e = new HSSFFormulaEvaluator((HSSFWorkbook) wb);  
		}
		int sheetNum = wb.getNumberOfSheets();  
		for (int i = 0; i < sheetNum; i++) {  
		    Sheet sheet = wb.getSheetAt(i);  
		    int rows = sheet.getLastRowNum() + 1;  
		    for (int j = 0; j < rows; j++) {  
		        Row row = sheet.getRow(j);  
		        if (row == null)  
		            continue;  
		        int cols = row.getLastCellNum();  
		        for (int k = 0; k < cols; k++) {  
		        	Cell cell = row.getCell(k);  
//		            if(cell!=null)  
//		                System.out.println("cell["+j+","+k+"]=:"+cell.getCellType());  
		            if (cell == null)  
		                continue;  
		            if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {  
		                cell.setCellFormula(cell.getCellFormula());  
		                cell=e.evaluateInCell(cell);  
		            }  
		        }  
		    }  
		}  
	}
}
