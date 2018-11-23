package com.niuxing.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.web.multipart.MultipartFile;

public class ExcelUtil {

	public static List<List<String>> getExcelData(MultipartFile filedata) throws Exception{
		List<List<String>> dataList = new ArrayList<List<String>>();
		Workbook workbook = WorkbookFactory.create(filedata.getInputStream());
		// 得到工作表
		Sheet sheet = workbook.getSheetAt(0);
		// 对应excel的行
		Row row = null;
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);
			List<String> data = new ArrayList<String>();
			for(int j=0;j<row.getLastCellNum();j++){
				String value = getCellValue(row.getCell(j), Boolean.TRUE);
				data.add(value);
			}
			dataList.add(data);
		}
		return dataList;
	}
	
	public static String getCellValue( Cell cell, Boolean hasNull) {  
        String ret = "";  
        if(cell != null){
	        switch (cell.getCellType()) {  
		        case XSSFCell.CELL_TYPE_BLANK:  
		            ret = "";  
		            break;  
		        case XSSFCell.CELL_TYPE_BOOLEAN:  
		            ret = String.valueOf(cell.getBooleanCellValue());  
		            break;  
		        case XSSFCell.CELL_TYPE_ERROR:  
		            ret = null;  
		            break;  
		        case XSSFCell.CELL_TYPE_STRING:  
		            ret = cell.getRichStringCellValue().getString();  
		            break; 
		        case XSSFCell.CELL_TYPE_NUMERIC:  
		        	DecimalFormat df = new DecimalFormat("0"); 
		        	ret = df.format(cell.getNumericCellValue()); 
//		            ret = String.valueOf(cell.getNumericCellValue());  
		            break; 
		        default:  
		            ret = null;  
	        }  
//	        if(StringUtils.isEmpty(ret) && hasNull){
//	        	throw new  FunctionException(result, "Excel 第"+row+"行数据有问题,执行失败");
//	        }
//	        if(ret.indexOf(".0") > 0){
//	        	ret = ret.substring(0,ret.length()-2);
//	        }
        }
        return ret; //有必要自行trim  
    }  
}
