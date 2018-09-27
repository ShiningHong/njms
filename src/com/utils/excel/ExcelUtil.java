package com.utils.excel;

import java.io.ByteArrayOutputStream;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.utils.CommUtil;
import com.utils.excel.bean.ExcelCol;
import com.utils.excel.bean.ExcelRow;

/**
 * excel文件操作
 * @author 陈嘉瑛
 * @version 2015-04-25
 *
 */
@SuppressWarnings("deprecation")
public class ExcelUtil {

	public static void exportExcel(String title, List<ExcelCol> header, 
			List<ExcelRow> dataLs, String userName, HttpServletResponse response,
			String conditionStr,List<String> alignLs,List<String> patterns,String userAgent) {
		List<List<ExcelCol>> headLs = new ArrayList<List<ExcelCol>>();
		headLs.add(header);
		List<List<ExcelRow>> dataRowLs = new ArrayList<List<ExcelRow>>();
		dataRowLs.add(dataLs);
		List<String> sheetNameLs = new ArrayList<String>();
		sheetNameLs.add(title);
		exportExcelOfSheetLs(title, headLs, dataRowLs, sheetNameLs, userName, response,conditionStr,alignLs,patterns,userAgent);
	}
	
	public static void exportExcelOfSheetLs(String title, List<List<ExcelCol>> headerColLs, 
			List<List<ExcelRow>> dataRowLs, List<String> sheetNameLs, String userName, 
			HttpServletResponse response,String conditionStr,List<String> alignLs,List<String> patterns,String userAgent) {
		OutputStream  out = null;

		try {
			//String fileName = "attachment; filename="+ new String(title.getBytes("UTF-8"), "ISO-8859-1");
			String fileName = "";
            fileName = "attachment; filename="+ URLEncoder.encode(title,"UTF8");
			out = response.getOutputStream();
			if(dataRowLs == null || headerColLs == null || headerColLs.size() == 0) {
				response.setContentType("text/plain;charset=utf-8");
				response.setHeader("Content-Disposition",fileName + "-log.txt"); 
				String msg = "导出出错!";
				byte[] b = msg.getBytes();
				out.write(b, 0, b.length);
				out.flush();
				return;
			}
			//设置文件名称
			response.setContentType("application/vnd.ms-excel;charset=UTF-8"); 
			//文件名改为带xlsx
			//response.setHeader("Content-Disposition", fileName + ".xls"); 
			response.setHeader("Content-Disposition", fileName + ".xlsx"); 
			//将工作簿换成2007版的
			//HSSFWorkbook workbook = coustructHSSFWorkbook(title, headerColLs,dataRowLs, sheetNameLs, userName,conditionStr,alignLs,patterns);
			if(dataRowLs.size() < 10000){
				XSSFWorkbook workbook = coustructXSSFWorkbook(title, headerColLs,dataRowLs, sheetNameLs, userName,conditionStr,alignLs,patterns);
				workbook.write(out);
			}else{
				SXSSFWorkbook workbook = coustructSXSSFWorkbook(title, headerColLs,dataRowLs, sheetNameLs, userName,conditionStr,alignLs,patterns);
				workbook.write(out);
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void outPutExportExcel(String title, List<ExcelCol> header, String conditionStr,
			List<String> aligns,List<String> patterns,List<ExcelRow> dataLs, String userName, String fileNamePath) throws ParseException {
		List<List<ExcelCol>> headLs = new ArrayList<List<ExcelCol>>();
		headLs.add(header);
		List<List<ExcelRow>> dataRowLs = new ArrayList<List<ExcelRow>>();
		dataRowLs.add(dataLs);
		List<String> sheetNameLs = new ArrayList<String>();
		sheetNameLs.add(title);
		outPutExcelOfSheetLs(title, headLs, conditionStr,aligns,patterns,dataRowLs, sheetNameLs, userName, fileNamePath);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void exportExcel(String fileName, String headerStr,String conditionStr,List<String> alignLs,List<String> patterns
			,String headerKey, List ls, String personName, HttpServletResponse response, Class cls,String userAgent) {
		try {
			String[] headerLs = headerStr.split(",");
			List<ExcelCol> header = new ArrayList<ExcelCol>();
			for(int i=0; i<headerLs.length; i++){
				String h = headerLs[i];
				if(StringUtils.isEmpty(h)) continue;
				ExcelCol c = new ExcelCol();
				c.setId(Long.valueOf(i+1));
				c.setVal(h);
				c.setPid(0L);
				header.add(c);
			}
			List<ExcelRow> dataLs = new ArrayList<ExcelRow>();
			for(Object t : ls){
				List<ExcelCol> cols = new ArrayList<ExcelCol>();
				int i = 0;
				for(String key : headerKey.split(",")){
					if(StringUtils.isEmpty(key)) continue;
					Object obj = null;
					if(t instanceof Map){
						String f = "get"+key.substring(0,1).toUpperCase();
						if(key.length() > 1) f = f+key.substring(1);
						Method m = cls.getMethod(f);
						obj = m.invoke(t);
					}else{
						obj = ((Map<String,Object>)t).get(key);
					}
					if(obj == null) obj = "";
					ExcelCol c = new ExcelCol();
					c.setId(Long.valueOf(i+1));
					c.setPid(0L);
					/*if(obj instanceof Date)
						c.setVal(DateUtils.formatDate((Date)obj, "yyyy-MM-dd HH:mm:ss"));*/
					/*else
						c.setVal(obj.toString());*/
					c.setVal(obj.toString());
					cols.add(c);
					i ++;
				}
				ExcelRow r = new ExcelRow();
				r.setCols(cols);
				dataLs.add(r);
			}
			exportExcel(fileName, header, dataLs, personName , response, conditionStr,alignLs,patterns,userAgent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 写出报表
	 * 作者：陈嘉瑛
	 * 时间：2015-07-31
	 * @param title
	 *            表格标题名
	 * @param headerColLs
	 *            表格属性列名数组
	 * @param dataRowLs
	 *            需要显示的数据集合
	 * @param sheetNameLs
	 * 			 对应excel的sheet
	 * @param userName
	 *            操作者名称
	 * @param fileNamePath
	 *           excel名称全路径
	 * @throws ParseException 
	 * @throws FileNotFoundException 
	 */
	public static void outPutExcelOfSheetLs(String title, List<List<ExcelCol>> headerColLs,String conditionStr,List<String> aligns,List<String> patterns,
			List<List<ExcelRow>> dataRowLs, List<String> sheetNameLs, String userName, String fileNamePath) throws ParseException {
		//OutputStream out = null;
		ByteArrayOutputStream out = null;
		try {
			/*
			//写出到指定的目录
			File file = new File(fileNamePath);
			if(!file.exists()) file.createNewFile();
			out = new FileOutputStream(file);*/
			out = new ByteArrayOutputStream();
			HSSFWorkbook workbook = coustructHSSFWorkbook(title, headerColLs, dataRowLs, sheetNameLs, userName,conditionStr,aligns,patterns);
			workbook.write(out);
			out.flush();
			//写入mongodb中
			//MongoDBFileUtil.saveFile(new ByteArrayInputStream(out.toByteArray()), fileNamePath);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	private static HSSFWorkbook coustructHSSFWorkbook(String title, List<List<ExcelCol>> headerColLs,
			List<List<ExcelRow>> dataRowLs, List<String> sheetNameLs, String userName,String conditionStr,List<String> aligns,List<String> patterns) throws ParseException{
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		for(int k = 0; k<sheetNameLs.size(); k++){
			List<ExcelCol> header = headerColLs.get(k);
			// 生成一个表格
			String sheetName = sheetNameLs.get(k);
			HSSFSheet sheet = workbook.createSheet(sheetName.trim());
			//HSSFSheet sheet = workbook.createSheet();
			// 设置表格默认列宽度为15个字节
			sheet.setDefaultColumnWidth(15);
			// 生成一个字体
			HSSFFont headFont = workbook.createFont();
			headFont.setFontHeightInPoints((short) 12);
			headFont.setBoldweight((short) 1000);
			HSSFCellStyle headerStyle = workbook.createCellStyle();
			headerStyle.setFillForegroundColor(HSSFColor.DARK_TEAL.index);
			/*headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBottomBorderColor(HSSFColor.GREY_40_PERCENT.index);
			headerStyle.setTopBorderColor(HSSFColor.GREY_40_PERCENT.index);
			headerStyle.setLeftBorderColor(HSSFColor.GREY_40_PERCENT.index);
			headerStyle.setRightBorderColor(HSSFColor.GREY_40_PERCENT.index);
			headerStyle.setBottomBorderColor(HSSFColor.GREY_40_PERCENT.index);
			headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);*/
			headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
			headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
			// 把字体应用到当前的样式
			headerStyle.setFont(headFont);
			
			// title header
			HSSFFont titleFont = workbook.createFont();
			titleFont.setFontHeightInPoints((short) 14);
			titleFont.setBoldweight((short) 1000);
			HSSFCellStyle titleStyle = workbook.createCellStyle();
			titleStyle.setFillForegroundColor(HSSFColor.DARK_TEAL.index);
			titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
			titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
			titleStyle.setFont(titleFont);
			
			// param header
			HSSFFont pamFont = workbook.createFont();
			titleFont.setFontHeightInPoints((short) 12);
			HSSFCellStyle pamStyle = workbook.createCellStyle();
			pamStyle.setFillForegroundColor(HSSFColor.DARK_TEAL.index);
			pamStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
			pamStyle.setFont(pamFont);
			
			//行字体样式
			HSSFFont rowFont = workbook.createFont();
			rowFont.setFontHeightInPoints((short) 12);
			
			/*// 声明一个画图的顶级管理器
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			// 定义注释的大小和位置,详见文档
			HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
			// 设置注释内容
			comment.setString(new HSSFRichTextString(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + "由"+userName+"生成!"));
			// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
			comment.setAuthor(userName);*/

			// 产生表格标题行
			List<List<Map<String, Object>>>  headerLs = new ArrayList<List<Map<String, Object>>>();
			constructHeader(header, headerLs);
			//实例化一个二维数组用于标记已经填充的单元格
			List<List<String>> flagCellFull = new ArrayList<List<String>>(headerLs.size());
			
			//定义开始行，开始列，结束行，结束列
			int flagTitle = 0, titleEndR = 5, beginC = 0, beginR = 0, endC = 0, endR = 0;
			for(int hRowIndex=0; hRowIndex<headerLs.size(); hRowIndex++){
				//从第hRowIndex行开始
				beginR = hRowIndex;
				//定义一个excel行
				HSSFRow row = sheet.createRow(hRowIndex + titleEndR);
				//得到要写入的第hRowIndex行表头信息
				List<Map<String, Object>> hCols = headerLs.get(hRowIndex);
				//遍历第hRowIndex行表头信息
				for(int hColIndex=0; hColIndex<hCols.size(); hColIndex++){
					Map<String, Object> c = hCols.get(hColIndex);
					Object hAlign = c.get("hAlign");
					String hAlignStr = hAlign == null || StringUtils.isEmpty(hAlign.toString()) ? "left" : hAlign.toString();
					//起始列
					beginC = getBeginC(flagCellFull, hRowIndex);
					//获取该列表头值
					String val = (String)c.get("val");
					//获取该表头是否有下级表头标记，subNum
					Long subNum = (Long)c.get("subNum");
					//如果没有子表头，则跨headerLs.size()-hRowIndex行，1列,否则跨1行，subNum列
					if(subNum == null || subNum == 0){
						endR = (headerLs.size()-hRowIndex) + beginR - 1;
						endC = beginC;
					}else{
						endC = subNum.intValue() + beginC - 1;
						endR = hRowIndex;
					}
					//创建该单元格
					sheet.addMergedRegion(new Region((short)beginR+titleEndR, (short)beginC, (short)endR+titleEndR, (short)endC));
					HSSFCell cell = row.createCell(beginC);
					HSSFCellStyle headerStyleTmp = workbook.createCellStyle();
					headerStyleTmp.setFillForegroundColor(HSSFColor.DARK_TEAL.index);
					headerStyleTmp.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
					headerStyleTmp.setAlignment(getPoiAlign(hAlignStr));// 水平
					headerStyleTmp.setFont(headFont);
					if(val.indexOf("<br>") != -1){
						headerStyleTmp.setWrapText(true); //设置强制换行
					}
					cell.setCellStyle(headerStyleTmp);
					cell.setCellValue(val.replaceAll("<br>","\r\n"));
					//标记已经填充的单元格
					flagCell(beginC, endC, beginR, endR, flagCellFull);
				}
				//扩展头部信息-查询条件
				if(flagTitle != -1) {
					//扩展头部
					HSSFRow rowTitle = sheet.createRow(0);
					sheet.addMergedRegion(new Region((short)0, (short)0, (short)titleEndR-3, (short)endC));
					HSSFCell cell = rowTitle.createCell(0);
					cell.setCellStyle(titleStyle);
					cell.setCellValue(title);
					//扩展查询条件
					HSSFRow rowPam = sheet.createRow(titleEndR-2);
					sheet.addMergedRegion(new Region((short)(titleEndR-2), (short)0, (short)titleEndR-1, (short)endC));
					HSSFCell cell2 = rowPam.createCell(0);
					cell2.setCellStyle(pamStyle);
					cell2.setCellValue("查询条件："+conditionStr);
					flagTitle = -1;
				}
			}
			
			// 遍历集合数据，产生数据行
			int rowIndex = headerLs.size() + titleEndR;
			List<ExcelRow> dataLs = dataRowLs.get(k);
			
			HSSFCellStyle rowDataStyle1 = workbook.createCellStyle(); //左对齐
			rowDataStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			rowDataStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			rowDataStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);
			rowDataStyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);
			rowDataStyle1.setBottomBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle1.setTopBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle1.setLeftBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle1.setRightBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle1.setFont(rowFont);
			
			HSSFCellStyle rowDataStyle2 = workbook.createCellStyle(); //居中
			rowDataStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			rowDataStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			rowDataStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			rowDataStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			rowDataStyle2.setBottomBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle2.setTopBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle2.setLeftBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle2.setRightBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle2.setFont(rowFont);
			
			HSSFCellStyle rowDataStyle3 = workbook.createCellStyle(); //右对齐
			rowDataStyle3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			rowDataStyle3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			rowDataStyle3.setBorderRight(HSSFCellStyle.BORDER_THIN);
			rowDataStyle3.setBorderTop(HSSFCellStyle.BORDER_THIN);
			rowDataStyle3.setBottomBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle3.setTopBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle3.setLeftBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle3.setRightBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle3.setFont(rowFont);
			
			//构造excel数据列格式化样式
			List<CellStyle> cellStyleLs = new ArrayList<CellStyle>();
			if(patterns != null && patterns.size() > 0){
				for(int i=0;i<patterns.size();i++){
					if(patterns.get(i) == null) cellStyleLs.add(null);
					else{
						CellStyle cellStyle = workbook.createCellStyle();
						HSSFDataFormat format = workbook.createDataFormat();
			            cellStyle.setDataFormat(format.getFormat(patterns.get(i)));
			            cellStyle.setFont(rowFont);
			            cellStyleLs.add(cellStyle);
					}
				}
			}
			for(int i=0; i<dataLs.size(); i++, rowIndex ++){
				HSSFRow row = sheet.createRow(rowIndex);
				List<ExcelCol> colLs = dataLs.get(i).getCols();
				for(int j=0; j<colLs.size(); j++){
					HSSFCell cell = row.createCell(j);
					String align = aligns.get(j);
					if(align.equals("left")){
						rowDataStyle1.setAlignment(getPoiAlign(align));
						cell.setCellStyle(rowDataStyle1);
					}else if(align.equals("right")){
						rowDataStyle2.setAlignment(getPoiAlign(align));
						cell.setCellStyle(rowDataStyle2);
					}else if(align.equals("center")){
						rowDataStyle3.setAlignment(getPoiAlign(align));
						cell.setCellStyle(rowDataStyle3);
					}
					Object obj = colLs.get(j).getVal();
					if(obj == null) {
						continue;
					}
					Class<?> cls = obj.getClass();
					if(cls == BigDecimal.class) {
						BigDecimal b = (BigDecimal)obj;
						Double val = b.doubleValue();
						cell.setCellValue(val);
						if(cellStyleLs.get(j) != null){
							cell.setCellStyle(cellStyleLs.get(j));
						}
		                //cell.setCellStyle(cellStyle);
						//cell.setCellValue(b.doubleValue());
					} else if(cls == int.class || cls == Integer.class || cls == short.class) {
						cell.setCellValue((int)obj);
						if(cellStyleLs.get(j) != null){
							cell.setCellStyle(cellStyleLs.get(j));
						}
					} else if(cls == long.class || cls == Long.class) {
						cell.setCellValue((long)obj);
						if(cellStyleLs.get(j) != null){
							cell.setCellStyle(cellStyleLs.get(j));
						}
					} else if(cls == float.class || cls == Float.class) {
						cell.setCellValue((float)obj);
						if(cellStyleLs.get(j) != null){
							cell.setCellStyle(cellStyleLs.get(j));
						}
					} else if(cls == double.class || cls == Double.class) {
						cell.setCellValue((double)obj);
						if(cellStyleLs.get(j) != null){
							cell.setCellStyle(cellStyleLs.get(j));
						}
					} else {
						/*String temp = obj.toString();
						if(temp.contains(",")){
							temp = temp.replaceAll(",","");
					        if (isNum(temp)) {
					        	cell.setCellValue(DecimalFormat.getIntegerInstance(Locale.getDefault()).parse(obj.toString()).intValue());
					        }else{
					        	cell.setCellValue(obj.toString());
					        }
						}else if(isNum(temp)){
							cell.setCellValue(DecimalFormat.getIntegerInstance(Locale.getDefault()).parse(obj.toString()).intValue());
						}else{
							cell.setCellValue(obj.toString());
						}*/
						cell.setCellValue(obj.toString());
					}
				}
			}
		}
		return workbook;
	}

	/**
	 * 若存在行合并，就移除列合并
	 * 
	 * @author zhengw3
	 * @date 2018年8月21日
	 */
	private static void rowOrColSpan(XSSFSheet sheet, Map<String, Object> tmpValueMap, List<ExcelRow> dataLs, List<ExcelCol> colLs, ExcelCol col,
			int rowIndex, int i, int j) {
		Object obj = col.getVal();
		if (col.isRowspan()) {
			Object tmpval = tmpValueMap.get(j + "Value");
			Object tmpIndex = tmpValueMap.get(j + "Index");
			boolean flag = true;
			if (tmpval != null) {
				if (tmpval.equals(obj)) {
					flag = false;
					if (i == dataLs.size() - 1) // 尾行
						sheet.addMergedRegion(new CellRangeAddress((Integer) tmpIndex, rowIndex, j, j));
				} else {
					sheet.addMergedRegion(new CellRangeAddress((Integer) tmpIndex, rowIndex - 1, j, j));
				}
			}
			if (flag) {
				tmpValueMap.put(j + "Index", rowIndex);
				tmpValueMap.put(j + "Value", obj);
			}
		} else if (col.isColspan() && (j <= 0 || !colLs.get(j - 1).isColspan())) { // 仅从同行的列合并的连续单元格的最左边一个开始合并
			int l = j + 1;
			while (l < colLs.size()) {
				ExcelCol c = colLs.get(l);
				if (!c.isColspan() || c.isRowspan() || c.getVal() != null || l >= colLs.size()) // 单元格行合并时，移除列合并
					break;

				++l;
			}
			if (--l > j)
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, j, l));
		}
	}

	/**
	 * 优先列合并，仅在无法进行列合并的情况下考虑行合并
	 * 
	 * @author zhengw3
	 * @date 2018年8月21日
	 */
	private static void colThenRowSpan(XSSFSheet sheet, Map<String, Object> tmpValueMap, List<ExcelRow> dataLs, List<ExcelCol> colLs, ExcelCol col,
			int rowIndex, int i, int j) {
		Object obj = col.getVal();
		boolean colspanDone = false; // 是否进行了列合并
		if (col.isColspan() && (j <= 0 || !colLs.get(j - 1).isColspan())) { // 仅从同行的列合并的连续单元格的最左边一个开始合并
			int l = j + 1;
			while (l < colLs.size()) {
				ExcelCol c = colLs.get(l);
				if (!c.isColspan() || c.getVal() != null || l >= colLs.size())
					break;

				++l;
			}
			if (--l > j) {
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, j, l));
				colspanDone = true;
			}
		}
		if (col.isRowspan()) {
			Object tmpval = tmpValueMap.get(j + "Value");
			Object tmpIndex = tmpValueMap.get(j + "Index");
			if (colspanDone) { // 若能够进行列合并，就不进行行合并
				if (tmpval != null) {
					sheet.addMergedRegion(new CellRangeAddress((Integer) tmpIndex, rowIndex - 1, j, j));
					tmpValueMap.remove(j + "Index");
					tmpValueMap.remove(j + "Value");
				}
				return;
			}
			boolean flag = true;
			if (tmpval != null) {
				if (tmpval.equals(obj)) {
					flag = false;
					if (i == dataLs.size() - 1) // 尾行
						sheet.addMergedRegion(new CellRangeAddress((Integer) tmpIndex, rowIndex, j, j));
				} else {
					sheet.addMergedRegion(new CellRangeAddress((Integer) tmpIndex, rowIndex - 1, j, j));
				}
			}
			if (flag) {
				tmpValueMap.put(j + "Index", rowIndex);
				tmpValueMap.put(j + "Value", obj);
			}
		}
	}
	
	//创建2007版工作簿
	private static XSSFWorkbook coustructXSSFWorkbook(String title, List<List<ExcelCol>> headerColLs,
			List<List<ExcelRow>> dataRowLs, List<String> sheetNameLs, String userName,String conditionStr,List<String> aligns,List<String> patterns) throws ParseException{
		// 声明一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		for(int k = 0; k<sheetNameLs.size(); k++){
			List<ExcelCol> header = headerColLs.get(k);
			// 生成一个表格
			String sheetName = sheetNameLs.get(k);
			XSSFSheet sheet = workbook.createSheet(sheetName.trim());
			//HSSFSheet sheet = workbook.createSheet();
			// 设置表格默认列宽度为15个字节
			sheet.setDefaultColumnWidth(15);
			// 生成一个字体
			XSSFFont headFont = workbook.createFont();
			headFont.setFontHeightInPoints((short) 12);
			headFont.setBoldweight((short) 1000);
			XSSFCellStyle headerStyle = workbook.createCellStyle();
			headerStyle.setFillForegroundColor(HSSFColor.DARK_TEAL.index);
			/*headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBottomBorderColor(HSSFColor.GREY_40_PERCENT.index);
			headerStyle.setTopBorderColor(HSSFColor.GREY_40_PERCENT.index);
			headerStyle.setLeftBorderColor(HSSFColor.GREY_40_PERCENT.index);
			headerStyle.setRightBorderColor(HSSFColor.GREY_40_PERCENT.index);
			headerStyle.setBottomBorderColor(HSSFColor.GREY_40_PERCENT.index);
			headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);*/
			headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
			headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
			// 把字体应用到当前的样式
			headerStyle.setFont(headFont);
			
			// title header
			XSSFFont titleFont = workbook.createFont();
			titleFont.setFontHeightInPoints((short) 14);
			titleFont.setBoldweight((short) 1000);
			XSSFCellStyle titleStyle = workbook.createCellStyle();
			titleStyle.setFillForegroundColor(HSSFColor.DARK_TEAL.index);
			titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
			titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
			titleStyle.setFont(titleFont);
			
			// param header
			XSSFFont pamFont = workbook.createFont();
			titleFont.setFontHeightInPoints((short) 12);
			XSSFCellStyle pamStyle = workbook.createCellStyle();
			pamStyle.setFillForegroundColor(HSSFColor.DARK_TEAL.index);
			pamStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
			pamStyle.setFont(pamFont);
			
			//行字体样式
			XSSFFont rowFont = workbook.createFont();
			rowFont.setFontHeightInPoints((short) 12);
			
			/*// 声明一个画图的顶级管理器
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			// 定义注释的大小和位置,详见文档
			HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
			// 设置注释内容
			comment.setString(new HSSFRichTextString(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + "由"+userName+"生成!"));
			// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
			comment.setAuthor(userName);*/

			// 产生表格标题行
			List<List<Map<String, Object>>>  headerLs = new ArrayList<List<Map<String, Object>>>();
			constructHeader(header, headerLs);
			//实例化一个二维数组用于标记已经填充的单元格
			List<List<String>> flagCellFull = new ArrayList<List<String>>(headerLs.size());
			
			//定义开始行，开始列，结束行，结束列
			int flagTitle = 0, titleEndR = 5, beginC = 0, beginR = 0, endC = 0, endR = 0;
			for(int hRowIndex=0; hRowIndex<headerLs.size(); hRowIndex++){
				//从第hRowIndex行开始
				beginR = hRowIndex;
				//定义一个excel行
				XSSFRow row = sheet.createRow(hRowIndex + titleEndR);
				//得到要写入的第hRowIndex行表头信息
				List<Map<String, Object>> hCols = headerLs.get(hRowIndex);
				//遍历第hRowIndex行表头信息
				for(int hColIndex=0; hColIndex<hCols.size(); hColIndex++){
					Map<String, Object> c = hCols.get(hColIndex);
					Object hAlign = c.get("hAlign");
					String hAlignStr = hAlign == null || StringUtils.isEmpty(hAlign.toString()) ? "left" : hAlign.toString();
					//起始列
					beginC = getBeginC(flagCellFull, hRowIndex);
					//获取该列表头值
					String val = (String)c.get("val");
					//获取该表头是否有下级表头标记，subNum
					Long subNum = (Long)c.get("subNum");
					//如果没有子表头，则跨headerLs.size()-hRowIndex行，1列,否则跨1行，subNum列
					if(subNum == null || subNum == 0){
						endR = (headerLs.size()-hRowIndex) + beginR - 1;
						endC = beginC;
					}else{
						endC = subNum.intValue() + beginC - 1;
						endR = hRowIndex;
					}
					//创建该单元格
					sheet.addMergedRegion(new CellRangeAddress((short)beginR+titleEndR, (short)endR+titleEndR, (short)beginC, (short)endC));
					XSSFCell cell = row.createCell(beginC);
					XSSFCellStyle headerStyleTmp = workbook.createCellStyle();
					headerStyleTmp.setFillForegroundColor(HSSFColor.DARK_TEAL.index);
					headerStyleTmp.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
					headerStyleTmp.setAlignment(getPoiAlign(hAlignStr));// 水平
					headerStyleTmp.setFont(headFont);
					if(val.indexOf("<br>") != -1){
						headerStyleTmp.setWrapText(true); //设置强制换行
					}
					cell.setCellStyle(headerStyleTmp);
					cell.setCellValue(val.replaceAll("<br>","\r\n"));
					//标记已经填充的单元格
					flagCell(beginC, endC, beginR, endR, flagCellFull);
				}
				//扩展头部信息-查询条件
				if(flagTitle != -1) {
					//扩展头部
					XSSFRow rowTitle = sheet.createRow(0);
					sheet.addMergedRegion(new CellRangeAddress((short)0,(short)titleEndR-3, (short)0, (short)endC));
					XSSFCell cell = rowTitle.createCell(0);
					cell.setCellStyle(titleStyle);
					cell.setCellValue(title);
					//扩展查询条件
					XSSFRow rowPam = sheet.createRow(titleEndR-2);
					sheet.addMergedRegion(new CellRangeAddress((short)(titleEndR-2),(short)titleEndR-1, (short)0, (short)endC));
					XSSFCell cell2 = rowPam.createCell(0);
					cell2.setCellStyle(pamStyle);
					cell2.setCellValue("查询条件："+conditionStr);
					flagTitle = -1;
				}
			}
			
			// 遍历集合数据，产生数据行
			int rowIndex = headerLs.size() + titleEndR;
			List<ExcelRow> dataLs = dataRowLs.get(k);
			
			XSSFCellStyle rowDataStyle1 = workbook.createCellStyle(); //左对齐
			rowDataStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			rowDataStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			rowDataStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);
			rowDataStyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);
			rowDataStyle1.setBottomBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle1.setTopBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle1.setLeftBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle1.setRightBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle1.setFont(rowFont);
			rowDataStyle1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			
			XSSFCellStyle rowDataStyle2 = workbook.createCellStyle(); //居中
			rowDataStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			rowDataStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			rowDataStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			rowDataStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			rowDataStyle2.setBottomBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle2.setTopBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle2.setLeftBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle2.setRightBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle2.setFont(rowFont);
			rowDataStyle2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			
			XSSFCellStyle rowDataStyle3 = workbook.createCellStyle(); //右对齐
			rowDataStyle3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			rowDataStyle3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			rowDataStyle3.setBorderRight(HSSFCellStyle.BORDER_THIN);
			rowDataStyle3.setBorderTop(HSSFCellStyle.BORDER_THIN);
			rowDataStyle3.setBottomBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle3.setTopBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle3.setLeftBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle3.setRightBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle3.setFont(rowFont);
			rowDataStyle3.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			//构造excel数据列格式化样式
			List<CellStyle> cellStyleLs = new ArrayList<CellStyle>();
			if(patterns != null && patterns.size() > 0){
				for(int i=0;i<patterns.size();i++){
					if(patterns.get(i) == null) cellStyleLs.add(null);
					else{
						CellStyle cellStyle = workbook.createCellStyle();
						XSSFDataFormat format = workbook.createDataFormat();
			            cellStyle.setDataFormat(format.getFormat(patterns.get(i)));
			            cellStyle.setFont(rowFont);
                        String align = aligns.get(i);
                        if(align.equals("left")){
                            cellStyle.setAlignment(getPoiAlign(align));
                        }else if(align.equals("right")){
                            cellStyle.setAlignment(getPoiAlign(align));
                        }else if(align.equals("center")){
                            cellStyle.setAlignment(getPoiAlign(align));
                        }
//			            cellStyle.setAlignment(CellStyle.VERTICAL_CENTER);
			            cellStyleLs.add(cellStyle);
					}
				}
			}
			Map<String,Object> tmpValueMap = new HashMap<String, Object>();
			for(int i=0; i<dataLs.size(); i++, rowIndex ++){
				XSSFRow row = sheet.createRow(rowIndex);
				List<ExcelCol> colLs = dataLs.get(i).getCols();
				for(int j=0; j<colLs.size(); j++){
                    XSSFCell cell = row.createCell(j);
                    String align = aligns.get(j);
                    if(align.equals("left")){
                        rowDataStyle1.setAlignment(getPoiAlign(align));
                        cell.setCellStyle(rowDataStyle1);
                    }else if(align.equals("right")){
                        rowDataStyle2.setAlignment(getPoiAlign(align));
                        cell.setCellStyle(rowDataStyle2);
                    }else if(align.equals("center")){
                        rowDataStyle3.setAlignment(getPoiAlign(align));
                        cell.setCellStyle(rowDataStyle3);
                    }
                    ExcelCol col = colLs.get(j);
                    colThenRowSpan(sheet, tmpValueMap, dataLs, colLs, col, rowIndex, i, j);
					Object obj = col.getVal();
					if(obj == null) {
						continue;
					}
					Class<?> cls = obj.getClass();
					if(cls == BigDecimal.class) {
						BigDecimal b = (BigDecimal)obj;
						Double val = b.doubleValue();
						cell.setCellValue(val);
						if(cellStyleLs.get(j) != null){
							cell.setCellStyle(cellStyleLs.get(j));
						}
		                //cell.setCellStyle(cellStyle);
						//cell.setCellValue(b.doubleValue());
					} else if(cls == int.class || cls == Integer.class || cls == short.class) {
						cell.setCellValue((int)obj);
						if(cellStyleLs.get(j) != null){
							cell.setCellStyle(cellStyleLs.get(j));
						}
					} else if(cls == long.class || cls == Long.class) {
						cell.setCellValue((long)obj);
						if(cellStyleLs.get(j) != null){
							cell.setCellStyle(cellStyleLs.get(j));
						}
					} else if(cls == float.class || cls == Float.class) {
						cell.setCellValue((float)obj);
						if(cellStyleLs.get(j) != null){
							cell.setCellStyle(cellStyleLs.get(j));
						}
					} else if(cls == double.class || cls == Double.class) {
						cell.setCellValue((double)obj);
						if(cellStyleLs.get(j) != null){
							cell.setCellStyle(cellStyleLs.get(j));
						}
					} else {
						/*String temp = obj.toString();
						if(temp.contains(",")){
							temp = temp.replaceAll(",","");
					        if (isNum(temp)) {
					        	cell.setCellValue(DecimalFormat.getIntegerInstance(Locale.getDefault()).parse(obj.toString()).intValue());
					        }else{
					        	cell.setCellValue(obj.toString());
					        }
						}else if(isNum(temp)){
							cell.setCellValue(DecimalFormat.getIntegerInstance(Locale.getDefault()).parse(obj.toString()).intValue());
						}else{
							cell.setCellValue(obj.toString());
						}*/
						if(CommUtil.isFloat(obj.toString()) && cellStyleLs.get(j) != null){
							cell.setCellValue(Float.valueOf(obj.toString()));
							cell.setCellStyle(cellStyleLs.get(j));
						}else{
							cell.setCellValue(obj.toString().replaceAll("&nbsp;"," "));
						}
					}
				}
			}
		}
		return workbook;
	}
	
	//创建2007版工作簿   大数据量的
	private static SXSSFWorkbook coustructSXSSFWorkbook(String title, List<List<ExcelCol>> headerColLs,
			List<List<ExcelRow>> dataRowLs, List<String> sheetNameLs, String userName,String conditionStr,List<String> aligns,List<String> patterns) throws ParseException{
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
		for(int k = 0; k<sheetNameLs.size(); k++){
			List<ExcelCol> header = headerColLs.get(k);
			// 生成一个表格
			String sheetName = sheetNameLs.get(k);
			Sheet sheet = workbook.createSheet(sheetName.trim());
			//HSSFSheet sheet = workbook.createSheet();
			// 设置表格默认列宽度为15个字节
			sheet.setDefaultColumnWidth(15);
			// 生成一个字体
			Font headFont = workbook.createFont();
			headFont.setFontHeightInPoints((short) 12);
			headFont.setBoldweight((short) 1000);
			CellStyle headerStyle = workbook.createCellStyle();
			headerStyle.setFillForegroundColor(HSSFColor.DARK_TEAL.index);
			/*headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			headerStyle.setBottomBorderColor(HSSFColor.GREY_40_PERCENT.index);
			headerStyle.setTopBorderColor(HSSFColor.GREY_40_PERCENT.index);
			headerStyle.setLeftBorderColor(HSSFColor.GREY_40_PERCENT.index);
			headerStyle.setRightBorderColor(HSSFColor.GREY_40_PERCENT.index);
			headerStyle.setBottomBorderColor(HSSFColor.GREY_40_PERCENT.index);
			headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);*/
			headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
			headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
			// 把字体应用到当前的样式
			headerStyle.setFont(headFont);
			
			// title header
			Font titleFont = workbook.createFont();
			titleFont.setFontHeightInPoints((short) 14);
			titleFont.setBoldweight((short) 1000);
			CellStyle titleStyle = workbook.createCellStyle();
			titleStyle.setFillForegroundColor(HSSFColor.DARK_TEAL.index);
			titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
			titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
			titleStyle.setFont(titleFont);
			
			// param header
			Font pamFont = workbook.createFont();
			titleFont.setFontHeightInPoints((short) 12);
			CellStyle pamStyle = workbook.createCellStyle();
			pamStyle.setFillForegroundColor(HSSFColor.DARK_TEAL.index);
			pamStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
			pamStyle.setFont(pamFont);
			
			//行字体样式
			Font rowFont = workbook.createFont();
			rowFont.setFontHeightInPoints((short) 12);
			
			/*// 声明一个画图的顶级管理器
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			// 定义注释的大小和位置,详见文档
			HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
			// 设置注释内容
			comment.setString(new HSSFRichTextString(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + "由"+userName+"生成!"));
			// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
			comment.setAuthor(userName);*/

			// 产生表格标题行
			List<List<Map<String, Object>>>  headerLs = new ArrayList<List<Map<String, Object>>>();
			constructHeader(header, headerLs);
			//实例化一个二维数组用于标记已经填充的单元格
			List<List<String>> flagCellFull = new ArrayList<List<String>>(headerLs.size());
			
			//定义开始行，开始列，结束行，结束列
			int flagTitle = 0, titleEndR = 5, beginC = 0, beginR = 0, endC = 0, endR = 0;
			for(int hRowIndex=0; hRowIndex<headerLs.size(); hRowIndex++){
				//从第hRowIndex行开始
				beginR = hRowIndex;
				//定义一个excel行
				Row row = sheet.createRow(hRowIndex + titleEndR);
				//得到要写入的第hRowIndex行表头信息
				List<Map<String, Object>> hCols = headerLs.get(hRowIndex);
				//遍历第hRowIndex行表头信息
				for(int hColIndex=0; hColIndex<hCols.size(); hColIndex++){
					Map<String, Object> c = hCols.get(hColIndex);
					Object hAlign = c.get("hAlign");
					String hAlignStr = hAlign == null || StringUtils.isEmpty(hAlign.toString()) ? "left" : hAlign.toString();
					//起始列
					beginC = getBeginC(flagCellFull, hRowIndex);
					//获取该列表头值
					String val = (String)c.get("val");
					//获取该表头是否有下级表头标记，subNum
					Long subNum = (Long)c.get("subNum");
					//如果没有子表头，则跨headerLs.size()-hRowIndex行，1列,否则跨1行，subNum列
					if(subNum == null || subNum == 0){
						endR = (headerLs.size()-hRowIndex) + beginR - 1;
						endC = beginC;
					}else{
						endC = subNum.intValue() + beginC - 1;
						endR = hRowIndex;
					}
					//创建该单元格
					sheet.addMergedRegion(new CellRangeAddress((short)beginR+titleEndR, (short)endR+titleEndR, (short)beginC, (short)endC));
				    Cell cell = row.createCell(beginC);
					CellStyle headerStyleTmp = workbook.createCellStyle();
					headerStyleTmp.setFillForegroundColor(HSSFColor.DARK_TEAL.index);
					headerStyleTmp.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
					headerStyleTmp.setAlignment(getPoiAlign(hAlignStr));// 水平
					headerStyleTmp.setFont(headFont);
					if(val.indexOf("<br>") != -1){
						headerStyleTmp.setWrapText(true); //设置强制换行
					}
					cell.setCellStyle(headerStyleTmp);
					cell.setCellValue(val.replaceAll("<br>","\r\n"));
					//标记已经填充的单元格
					flagCell(beginC, endC, beginR, endR, flagCellFull);
				}
				//扩展头部信息-查询条件
				if(flagTitle != -1) {
					//扩展头部
					Row rowTitle = sheet.createRow(0);
					sheet.addMergedRegion(new CellRangeAddress((short)0,(short)titleEndR-3, (short)0, (short)endC));
					Cell cell = rowTitle.createCell(0);
					cell.setCellStyle(titleStyle);
					cell.setCellValue(title);
					//扩展查询条件
					Row rowPam = sheet.createRow(titleEndR-2);
					sheet.addMergedRegion(new CellRangeAddress((short)(titleEndR-2),(short)titleEndR-1, (short)0, (short)endC));
					Cell cell2 = rowPam.createCell(0);
					cell2.setCellStyle(pamStyle);
					cell2.setCellValue("查询条件："+conditionStr);
					flagTitle = -1;
				}
			}
			
			// 遍历集合数据，产生数据行
			int rowIndex = headerLs.size() + titleEndR;
			List<ExcelRow> dataLs = dataRowLs.get(k);
			
			CellStyle rowDataStyle1 = workbook.createCellStyle(); //左对齐
			rowDataStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			rowDataStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			rowDataStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);
			rowDataStyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);
			rowDataStyle1.setBottomBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle1.setTopBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle1.setLeftBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle1.setRightBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle1.setFont(rowFont);
			rowDataStyle1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			
			CellStyle rowDataStyle2 = workbook.createCellStyle(); //居中
			rowDataStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			rowDataStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			rowDataStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			rowDataStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			rowDataStyle2.setBottomBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle2.setTopBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle2.setLeftBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle2.setRightBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle2.setFont(rowFont);
			rowDataStyle2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			
			CellStyle rowDataStyle3 = workbook.createCellStyle(); //右对齐
			rowDataStyle3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			rowDataStyle3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			rowDataStyle3.setBorderRight(HSSFCellStyle.BORDER_THIN);
			rowDataStyle3.setBorderTop(HSSFCellStyle.BORDER_THIN);
			rowDataStyle3.setBottomBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle3.setTopBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle3.setLeftBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle3.setRightBorderColor(HSSFColor.GREY_40_PERCENT.index);
			rowDataStyle3.setFont(rowFont);
			rowDataStyle3.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			
			//构造excel数据列格式化样式
			List<CellStyle> cellStyleLs = new ArrayList<CellStyle>();
			if(patterns != null && patterns.size() > 0){
				for(int i=0;i<patterns.size();i++){
					if(patterns.get(i) == null) cellStyleLs.add(null);
					else{
						CellStyle cellStyle = workbook.createCellStyle();
						DataFormat format = workbook.createDataFormat();
			            cellStyle.setDataFormat(format.getFormat(patterns.get(i)));
			            cellStyle.setFont(rowFont);
			            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			            cellStyleLs.add(cellStyle);
					}
				}
			}
			Map<String,Object> tmpValueMap = new HashMap<String, Object>();
			for(int i=0; i<dataLs.size(); i++, rowIndex ++){
				Row row = sheet.createRow(rowIndex);
				List<ExcelCol> colLs = dataLs.get(i).getCols();
				for(int j=0; j<colLs.size(); j++){
					Cell cell = row.createCell(j);
					String align = aligns.get(j);
					if(align.equals("left")){
						rowDataStyle1.setAlignment(getPoiAlign(align));
						cell.setCellStyle(rowDataStyle1);
					}else if(align.equals("right")){
						rowDataStyle2.setAlignment(getPoiAlign(align));
						cell.setCellStyle(rowDataStyle2);
					}else if(align.equals("center")){
						rowDataStyle3.setAlignment(getPoiAlign(align));
						cell.setCellStyle(rowDataStyle3);
					}
					Object obj = colLs.get(j).getVal();
					//合并单元格
					boolean rowspan =  colLs.get(j).isRowspan();
					if(rowspan){
						Object tmpval = tmpValueMap.get(j+"Value");
						Object tmpIndex = tmpValueMap.get(j+"Index");
						if(tmpval!=null){
							if(tmpval.equals(obj)&&i==dataLs.size()-1){
								//尾行
								sheet.addMergedRegion(new CellRangeAddress((Integer) tmpIndex,rowIndex,j, j));
							}else if(!tmpval.equals(obj)) {
								sheet.addMergedRegion(new CellRangeAddress((Integer) tmpIndex,rowIndex-1,j, j));
							    tmpValueMap.put(j+"Index",rowIndex);
							    tmpValueMap.put(j+"Value",obj);
							}
						}else{
							tmpValueMap.put(j+"Value",obj);
							tmpValueMap.put(j+"Index",rowIndex);
						}
					}
					if(obj == null) {
						continue;
					}
					Class<?> cls = obj.getClass();
					if(cls == BigDecimal.class) {
						BigDecimal b = (BigDecimal)obj;
						Double val = b.doubleValue();
						cell.setCellValue(val);
						if(cellStyleLs.get(j) != null){
							cell.setCellStyle(cellStyleLs.get(j));
						}
		                //cell.setCellStyle(cellStyle);
						//cell.setCellValue(b.doubleValue());
					} else if(cls == int.class || cls == Integer.class || cls == short.class) {
						cell.setCellValue((int)obj);
						if(cellStyleLs.get(j) != null){
							cell.setCellStyle(cellStyleLs.get(j));
						}
					} else if(cls == long.class || cls == Long.class) {
						cell.setCellValue((long)obj);
						if(cellStyleLs.get(j) != null){
							cell.setCellStyle(cellStyleLs.get(j));
						}
					} else if(cls == float.class || cls == Float.class) {
						cell.setCellValue((float)obj);
						if(cellStyleLs.get(j) != null){
							cell.setCellStyle(cellStyleLs.get(j));
						}
					} else if(cls == double.class || cls == Double.class) {
						cell.setCellValue((double)obj);
						if(cellStyleLs.get(j) != null){
							cell.setCellStyle(cellStyleLs.get(j));
						}
					} else {
						/*String temp = obj.toString();
						if(temp.contains(",")){
							temp = temp.replaceAll(",","");
					        if (isNum(temp)) {
					        	cell.setCellValue(DecimalFormat.getIntegerInstance(Locale.getDefault()).parse(obj.toString()).intValue());
					        }else{
					        	cell.setCellValue(obj.toString());
					        }
						}else if(isNum(temp)){
							cell.setCellValue(DecimalFormat.getIntegerInstance(Locale.getDefault()).parse(obj.toString()).intValue());
						}else{
							cell.setCellValue(obj.toString());
						}*/
						if(CommUtil.isFloat(obj.toString())&&cellStyleLs.get(j) != null){
							cell.setCellValue(Float.valueOf(obj.toString()));
							cell.setCellStyle(cellStyleLs.get(j));
						}else{
							cell.setCellValue(obj.toString());
						}
					}
				}
			}
		}
		return workbook;
	}
	/**
	 * 把配置中的对齐方式转成poi的对齐方式
	 * @param align
	 * @return
	 */
	private static short getPoiAlign(String align){
		short salign = 0;
		if(align.equals("center")){
			salign = HSSFCellStyle.ALIGN_CENTER;
		}else if(align.equals("left")){
			salign = HSSFCellStyle.ALIGN_LEFT;
		}else if(align.equals("right")){
			salign = HSSFCellStyle.ALIGN_RIGHT;
		}else{
			salign = HSSFCellStyle.ALIGN_LEFT;
		}
		return salign;
	}

	/**
	 * 标记已经填充的单元格
	 * 作者：陈嘉瑛
	 * 时间：2015-12-30
	 * @param beginC
	 * @param endC
	 * @param beginR
	 * @param endR
	 * @param flagCellFull
	 */
	private static void flagCell(int beginC, int endC, int beginR, int endR, List<List<String>> flagCellFull){
		String flag = "flag", unFlag = "unFlag";
		for(int j=beginR; j<=endR; j++){
			if(flagCellFull.size() <= j)
				flagCellFull.add(j, new ArrayList<String>());
			List<String> flagR = flagCellFull.get(j);
			for(int i=0; i<=endC; i++){
				if(i>=beginC && i<= endC){
					if(i < flagR.size() && unFlag.equals(flagR.get(i)))
						flagR.set(i, flag);
					else
						flagR.add(i, flag);
					continue;
				}
				if(i < flagR.size() && flagR.get(i) != null) continue;
				flagR.add(i, unFlag);
			}
		}
	}

	/**
	 * 获取开始列
	 * 作者：陈嘉瑛
	 * 时间：2015-12-30
	 * @param flagHasFull
	 * @param rowIndex
	 * @return
	 */
	private static int getBeginC(List<List<String>> flagHasFull, int rowIndex){
		if(rowIndex >= flagHasFull.size()) return 0;
		List<String> flagR = flagHasFull.get(rowIndex);
		String unFlag = "unFlag";
		for(int i=0; flagR != null && i<flagR.size(); i++){
			if(flagR.get(i).equals(unFlag)) return i;
		}
		return flagR == null ? 0 : flagR.size();
	}

	/**
	 * 构造一个表头开始
	 * 作者：陈嘉瑛
	 * 时间：2015-12-30
	 */
	private static void constructHeader(List<ExcelCol> header, List<List<Map<String, Object>>> rsHeaderLs){
		List<Map<String, Object>> newHeaderLs = new ArrayList<Map<String, Object>>();
		if(rsHeaderLs.size() == 0) {
			doConstructHeader(header, newHeaderLs, null);
		}else{
			List<Map<String, Object>> pHeaderLs = rsHeaderLs.get(rsHeaderLs.size() - 1);
			for(Map<String, Object> hM : pHeaderLs){
				doConstructHeader(header, newHeaderLs, (Long)hM.get("id"));
			}
		}
		if(newHeaderLs.size() == 0) return;
		rsHeaderLs.add(newHeaderLs);
		constructHeader(header, rsHeaderLs);
	}

	/**
	 * 执行构造表头
	 * 作者：陈嘉瑛
	 * 时间：2015-12-30
	 * @param header
	 * @param newHeaderLs
	 * @param pid
	 */
	private static void doConstructHeader(List<ExcelCol> header, List<Map<String, Object>> newHeaderLs, Long pid){
		for(ExcelCol c : header){
			Map<String, Object> headerMap = new HashMap<String, Object>();
			if(pid != null){
				if(c.getPid().longValue() != pid.longValue()) continue;
			}else{
				//获取根类表头
				if(c.getPid() != null && c.getPid() != 0) continue;
				if(c.getId() == null || c.getId() == 0) continue;
			}
			Long subId = c.getId();
			Long subNum = getSubNum(header, subId);
			//表头id
			headerMap.put("id", subId);
			//表头值
			headerMap.put("val", c.getVal());
			//表头下子表头个数
			headerMap.put("subNum", subNum);
			//对齐
			headerMap.put("hAlign", c.gethAlign());
			//字段名
			headerMap.put("field", c.getField());
			newHeaderLs.add(headerMap);
		}
	}
	
	private static Long getSubNum(List<ExcelCol> header, Long pid) {
		Long num = 0L;
		for(ExcelCol c : header) {
			Long pidTmp = c.getPid() == null ? 0 : c.getPid();
			if(pidTmp.longValue() == 0) continue;
			if(pidTmp.longValue() != pid.longValue()) {
				continue;
			}else{
				num ++;
				Long numTmp = getSubNum(header, c.getId());
				if(numTmp.longValue() > 0) num --;
				num += numTmp.longValue();
			}
		}
		return num;
	}
}
