package com.utils.excel;

import com.utils.codeModel.ResultModel;

import com.utils.general.AIStringUtil;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 区别于ExcelUtils之处：
 * 可获取表格小数点内容
 */
public class ExcelHandleUtils {

    /**
     * 日期格式.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final class FileType {
        public static final String XLS_FILE_TYPE = "xls";
        public static final String XLSX_FILE_TYPE = "xlsx";
    }

    /**
     * 创建excel文档，
     * @param list        数据
     * @param keys        list中map的key数组集合 （keys和columnNames至少设置一个）
     * @param columnNames excel的列名
     */
    public static Workbook createWorkBook(String sheetName, List<Map<String, Object>> list, String[] keys, List<String> columnNames) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名

        Sheet sheet = wb.createSheet(sheetName);
        if(keys==null&&columnNames==null){
        	return wb;
        }
        
        if(keys!=null){
        	// 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
            for (int i = 0; i < keys.length; i++) {
            	if(i==keys.length-1){
            		sheet.setColumnWidth((short) i, (short) (35.7 * 150*3));
            	}else{
                    sheet.setColumnWidth((short) i, (short) (35.7 * 150));
            	}
            }
        }else{
        	for (int i = 0; i < columnNames.size(); i++) {
        		sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        	}
        }
        
        int startIndexRow = 0;
        if(columnNames!=null){
        	// 创建第一行
            Row row = sheet.createRow(startIndexRow);

            // 创建两种单元格格式
            CellStyle cs = wb.createCellStyle();
            
            // 创建字体
            Font f = wb.createFont();

            // 创建第一种字体样式（用于列名）
            f.setFontHeightInPoints((short) 10);
            f.setColor(IndexedColors.BLACK.getIndex());
            f.setBoldweight(Font.BOLDWEIGHT_BOLD);           

            // 设置第一种单元格的样式（用于列名）
            cs.setFont(f);
            cs.setBorderLeft(CellStyle.BORDER_THIN);
            cs.setBorderRight(CellStyle.BORDER_THIN);
            cs.setBorderTop(CellStyle.BORDER_THIN);
            cs.setBorderBottom(CellStyle.BORDER_THIN);
            cs.setAlignment(CellStyle.ALIGN_CENTER);
           
            //设置列名
            for (int i = 0; i < columnNames.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(columnNames.get(i));
                cell.setCellStyle(cs);
            }
            startIndexRow ++;
        }
        
        // 创建第二种字体样式（用于值）
        Font f2 = wb.createFont();
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());
        
        // 设置第二种单元格的样式（用于值）
        CellStyle cs2 = wb.createCellStyle();
        cs2.setFont(f2);
        //cs2.setBorderLeft(CellStyle.BORDER_THIN);
        //cs2.setBorderRight(CellStyle.BORDER_THIN);
        //cs2.setBorderTop(CellStyle.BORDER_THIN);
        //cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        
        //设置每行每列的值
        if(list!=null){
        	 for (int i = 0; i < list.size(); i++) {
                 // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
                 // 创建一行，在页sheet上
                 Row row1 = sheet.createRow(startIndexRow);
                 // 在row行上创建一个方格
                 if(keys!=null){
                	 for (short j = 0; j < keys.length; j++) {
                         Cell cell = row1.createCell(j);
                         if(AIStringUtil.isEmpty(keys[j])){
                         	cell.setCellValue(" ");
                         }else{
                             cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
                         }
                         cell.setCellStyle(cs2);
                     }
                 }else{
                	 for (short j = 0; j < columnNames.size(); j++) {
                         Cell cell = row1.createCell(j);
                         if(AIStringUtil.isEmpty(columnNames.get(0))){
                         	cell.setCellValue(" ");
                         }else{
                             cell.setCellValue(list.get(i).get(columnNames.get(0)) == null ? " " : list.get(i).get(columnNames.get(0)).toString());
                         }
                         cell.setCellStyle(cs2);
                     }
                 }
                 startIndexRow ++;
             }
        }
       
        return wb;
    }
    
    public static HSSFWorkbook createXLSWorkBook(String sheetName, List<List<String>> dataList, List<String> titleList) {
    	HSSFWorkbook workbook = new HSSFWorkbook();

        // 创建excel主标题样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workbook.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 20);//设置字体大
        cellStyle.setBorderTop((short) 1);
        cellStyle.setBorderLeft((short) 1);
        cellStyle.setBorderRight((short) 1);
        cellStyle.setBorderBottom((short) 1);
        cellStyle.setFont(font);
        HSSFSheet hssfSheet = workbook.createSheet(sheetName);

        // 创建标题样式
        HSSFCellStyle titleCellStyle = workbook.createCellStyle();
        titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont titleFont = workbook.createFont();
        titleFont.setFontName("黑体");
        titleFont.setFontHeightInPoints((short) 14);//设置字体大
        titleCellStyle.setBorderTop((short) 1);
        titleCellStyle.setBorderLeft((short) 1);
        titleCellStyle.setBorderRight((short) 1);
        titleCellStyle.setBorderBottom((short) 1);
        titleCellStyle.setFont(titleFont);

        // 创建内容标题样式
        HSSFCellStyle contentCellStyle = workbook.createCellStyle();
        HSSFFont contentFont = workbook.createFont();
        contentFont.setFontName("黑体");
        contentFont.setFontHeightInPoints((short) 12);//设置字体大
        contentCellStyle.setBorderTop((short) 1);
        contentCellStyle.setBorderLeft((short) 1);
        contentCellStyle.setBorderRight((short) 1);
        contentCellStyle.setBorderBottom((short) 1);
        contentCellStyle.setFont(contentFont);

        // 创建excel主标题
//      hssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnLength - 1));
//      HSSFRow hssfRow = hssfSheet.createRow(0);
//      HSSFCell hssfCell = hssfRow.createCell(0);
//      hssfCell.setCellValue(excelTitle);
//      hssfCell.setCellStyle(cellStyle);
        
        int columnLength = titleList.size();
//        // 生成列标题
        HSSFRow hssfRow = hssfSheet.createRow(0);
        for (int i = 0; i < columnLength; i++) {
            HSSFCell hssfCell = hssfRow.createCell(i);
            hssfCell.setCellValue(titleList.get(i));
            hssfCell.setCellStyle(contentCellStyle);
        }
//
        // 处理表格数据
        for (int i = 0; i < dataList.size(); i++) {
            int rowIndex =  1+i;
            hssfRow = hssfSheet.createRow(rowIndex);
            List<String> rowDataList = dataList.get(i);
            for (int i1 = 0; i1 < rowDataList.size(); i1++) {
                HSSFCell hssfCell = hssfRow.createCell(i1);
                hssfCell.setCellValue(rowDataList.get(i1));
                hssfCell.setCellStyle(contentCellStyle);
            }
        }
        return workbook;
    }
    

    /**
     * 解析excel文件.
     * @param file excel文件
     * @return 返回列表
     * @throws Exception 异常
     */
    /*public static List<Map<String, String>> parseExcel(final File file) throws Exception {
        if (null == file || !file.exists()) {
            throw new Exception("待解析的文件不存在或者为null");
        }
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        String fileExtendName = file.getName();
        if (fileExtendName.endsWith(".xls")) {
            list = parseXLSFile(file);
        } else if (fileExtendName.endsWith(".xlsx")) {
            list = parseXLSXFile(file);
        }
        return list;
    }*/

    /**
     * 创建excel文件.
     * @param os     输出流
     * @param excelTitle excel文件主标题
     * @param titleList  列标题
     * @param data       数据集
     * @throws Exception
     */
    public static void writeExcel(final OutputStream os, String fileType, String excelTitle, List<String> titleList, List<List<String>> data) throws Exception {
        if (null == os) {
            throw new Exception("导出excle不能为null");
        } else {
            if (FileType.XLS_FILE_TYPE.equalsIgnoreCase(fileType)) {
                writeXLSFile(os, excelTitle, titleList, data);
            } else if (FileType.XLSX_FILE_TYPE.equalsIgnoreCase(fileType)) {
                writeXLSXFile(os, excelTitle, titleList, data);
            }
        }
    }


    private static void writeXLSFile(final OutputStream os, String excelTitle, List<String> titleList, List<List<String>> dataList) throws Exception {
    	HSSFWorkbook workbook = createXLSWorkBook(excelTitle, dataList, titleList);

        workbook.write(os);
        os.flush();
        os.close();
    }

    /**
     * 创建xlsx文件
     * @param os     生成的excel文件
     * @param excelTitle excel主标题
     * @param titleList  列标题
     * @param dataList   数据集
     * @throws Exception
     */
    private static void writeXLSXFile(final OutputStream os, String excelTitle, List<String> titleList, List<List<String>> dataList) throws Exception {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(excelTitle);

        int columnLength = titleList.size();

        // 创建excel主标题样式
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        XSSFFont font = workbook.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 20);//设置字体大
        cellStyle.setBorderTop((short) 1);
        cellStyle.setBorderLeft((short) 1);
        cellStyle.setBorderRight((short) 1);
        cellStyle.setBorderBottom((short) 1);
        cellStyle.setFont(font);

        // 创建标题样式
        XSSFCellStyle titleCellStyle = workbook.createCellStyle();
        titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        XSSFFont titleFont = workbook.createFont();
        titleFont.setFontName("黑体");
        titleFont.setFontHeightInPoints((short) 14);//设置字体大
        titleCellStyle.setBorderTop((short) 1);
        titleCellStyle.setBorderLeft((short) 1);
        titleCellStyle.setBorderRight((short) 1);
        titleCellStyle.setBorderBottom((short) 1);
        titleCellStyle.setFont(titleFont);

        // 创建内容标题样式
        XSSFCellStyle contentCellStyle = workbook.createCellStyle();
        XSSFFont contentFont = workbook.createFont();
        contentFont.setFontName("黑体");
        contentFont.setFontHeightInPoints((short) 12);//设置字体大
        contentCellStyle.setBorderTop((short) 1);
        contentCellStyle.setBorderLeft((short) 1);
        contentCellStyle.setBorderRight((short) 1);
        contentCellStyle.setBorderBottom((short) 1);
        contentCellStyle.setFont(contentFont);

        // 创建excel主标题
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnLength - 1));
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue(excelTitle);
        cell.setCellStyle(cellStyle);

        // 生成列标题
        row = sheet.createRow(1);
        for (int i = 0; i < columnLength; i++) {
            cell = row.createCell(i);
            cell.setCellValue(titleList.get(i));
            cell.setCellStyle(titleCellStyle);
        }

        // 处理表格数据
        for (int i = 0; i < dataList.size(); i++) {
            int rowIndex = 2 + i;
            row = sheet.createRow(rowIndex);
            List<String> rowDataList = dataList.get(i);
            for (int i1 = 0; i1 < columnLength; i1++) {
                cell = row.createCell(i1);
                cell.setCellValue(rowDataList.get(i1));
                cell.setCellStyle(contentCellStyle);
            }
        }

        workbook.write(os);
        os.flush();
        os.close();
    }

    /**
     * 解析xls文件.
     * @param file excel文件
     * @return 返回列表
     * @throws Exception 解析异常
     */
/*    private static List<Map<String, String>> parseXLSFile(final File file) throws Exception {
        List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();
        InputStream inputStream = new FileInputStream(file);
        return parseFromInputStream(inputStream);

    }*/

    public static List<Map<String, String>> parseFromInputStream(InputStream inputStream, int startRowNum, int startColumnNum) throws IOException {
        List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);

        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        HSSFRow hssfRow;
        HSSFCell hssfCell;

        // 处理表格头标题
        Map<Integer, String> titleKeys = new HashMap<Integer, String>();
        hssfRow = hssfSheet.getRow(startRowNum);
        if (null != hssfRow) {
            for (int i = hssfSheet.getFirstRowNum(); i < hssfRow.getPhysicalNumberOfCells(); i++) {
                HSSFCell cell = hssfRow.getCell(i);
                if (null != cell) {
                    String value = cell.toString();
                    if (null != value && value.trim().length() != 0) {
                        titleKeys.put(i, value);
                    } else {
                        titleKeys.put(i, "COL" + i);
                    }
                }
            }
        }

        // 处理表格内容数据
        for (int i = hssfSheet.getFirstRowNum() + startColumnNum+1; i < hssfSheet.getPhysicalNumberOfRows(); i++) {
            hssfRow = hssfSheet.getRow(i);
            if (null != hssfRow) {
                boolean isRowValid = false;
                Map<String, String> columnMap = new HashMap<String, String>();
                for (int j = startColumnNum; j < hssfRow.getPhysicalNumberOfCells(); j++) {
                    hssfCell = hssfRow.getCell(j);
                    if (null != hssfCell) {
                        String cellValue = getCellValue(hssfCell);
                        if (cellValue != null && cellValue.trim().length() == 0) {
                            cellValue = null;
                        }
                        columnMap.put(titleKeys.get(j), cellValue);
                        // 如果该行所有列至少一个存在值,则该行合法
                        if (!isRowValid && cellValue != null && cellValue.trim().length() > 0) {
                            isRowValid = true;
                        }
                    }
                }
                if (isRowValid) {
                    rowList.add(columnMap);
                }
            }
        }
        return rowList;
    }

    private static List<LinkedHashMap<String, String>> parseFromWorkbook(Workbook workbook,int startRow,List<String> titleKeys) throws Exception{
        List<LinkedHashMap<String, String>> rowList = new ArrayList<LinkedHashMap<String, String>>();
        Sheet sheet = workbook.getSheetAt(0);
        Row ssfRow;
        Cell ssfCell;
        // 处理表格头标题
        if(titleKeys==null){
        	titleKeys = parseHeaderFromSheet(sheet, startRow);
        	startRow ++;
        }
        

        // 处理表格内容数据
        for (int i = /*sheet.getFirstRowNum()*/startRow; i < sheet.getPhysicalNumberOfRows(); i++) {
            ssfRow = sheet.getRow(i);
            if (null != ssfRow) {
                boolean isRowValid = false;
                LinkedHashMap<String, String> columnMap = new LinkedHashMap<String, String>();
                columnMap.put("rowNum", i+"");//行号
                for (int j = ssfRow.getFirstCellNum(); j < /*ssfRow.getPhysicalNumberOfCells()*/ssfRow.getLastCellNum(); j++) {
                    ssfCell = ssfRow.getCell(j);
                    if (null != ssfCell) {
                        String cellValue = getCellValue(ssfCell);
                        if (AIStringUtil.isNotEmpty(cellValue)) {
                            //cellValue = null;
                        	if(AIStringUtil.isNotEmpty(titleKeys.get(j))){
                            	columnMap.put(titleKeys.get(j), cellValue.trim());
                            }
                        }
                        //columnMap.put(j, cellValue);
                        // 如果该行所有列至少一个存在值,则该行合法
                        if (!isRowValid && AIStringUtil.isNotEmpty(cellValue)) {
                            isRowValid = true;
                        }
                    }
                }
                if (isRowValid) {
                    rowList.add(columnMap);
                }
            }
        }
        return rowList;
    }

    private static List<Map<String, String>> parseFromWorkbook(HSSFWorkbook hssfWorkbook) throws IOException {
        List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();
        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        HSSFRow ssfRow;
        HSSFCell ssfCell;

        // 处理表格头标题
        Map<Integer, String> titleKeys = new HashMap<Integer, String>();
        ssfRow = sheet.getRow(0);
        if (null != ssfRow) {
            for (int i = sheet.getFirstRowNum(); i < ssfRow.getPhysicalNumberOfCells(); i++) {
                HSSFCell cell = ssfRow.getCell(i);
                if (null != cell) {
                    String value = cell.toString();
                    if (null != value && value.trim().length() != 0) {
                        titleKeys.put(i, value);
                    } else {
                        titleKeys.put(i, "COL" + i);
                    }
                }
            }
        }

        // 处理表格内容数据
        for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            ssfRow = sheet.getRow(i);
            if (null != ssfRow) {
                boolean isRowValid = false;
                Map<String, String> columnMap = new HashMap<String, String>();
                for (int j = ssfRow.getFirstCellNum(); j < ssfRow.getPhysicalNumberOfCells(); j++) {
                    ssfCell = ssfRow.getCell(j);
                    if (null != ssfCell) {
                        String cellValue = getCellValue(ssfCell);
                        if (cellValue != null && cellValue.trim().length() == 0) {
                            cellValue = null;
                        }
                        columnMap.put(titleKeys.get(j), cellValue);
                        // 如果该行所有列至少一个存在值,则该行合法
                        if (!isRowValid && cellValue != null && cellValue.trim().length() > 0) {
                            isRowValid = true;
                        }
                    }
                }
                if (isRowValid) {
                    rowList.add(columnMap);
                }
            }
        }
        return rowList;
    }

    private static List<Map<String, String>> parseFromWorkbook(XSSFWorkbook workbook) throws IOException {
        List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow ssfRow;
        XSSFCell ssfCell;

        // 处理表格头标题
        Map<Integer, String> titleKeys = new HashMap<Integer, String>();
        ssfRow = sheet.getRow(0);
        if (null != ssfRow) {
            for (int i = sheet.getFirstRowNum(); i < ssfRow.getPhysicalNumberOfCells(); i++) {
                XSSFCell cell = ssfRow.getCell(i);
                if (null != cell) {
                    String value = cell.toString();
                    if (null != value && value.trim().length() != 0) {
                        titleKeys.put(i, value);
                    } else {
                        titleKeys.put(i, "COL" + i);
                    }
                }
            }
        }

        // 处理表格内容数据
        for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            ssfRow = sheet.getRow(i);
            if (null != ssfRow) {
                boolean isRowValid = false;
                Map<String, String> columnMap = new HashMap<String, String>();
                for (int j = ssfRow.getFirstCellNum(); j < ssfRow.getPhysicalNumberOfCells(); j++) {
                    ssfCell = ssfRow.getCell(j);
                    if (null != ssfCell) {
                        String cellValue = getCellValue(ssfCell);
                        if (cellValue != null && cellValue.trim().length() == 0) {
                            cellValue = null;
                        }
                        columnMap.put(titleKeys.get(j), cellValue);
                        // 如果该行所有列至少一个存在值,则该行合法
                        if (!isRowValid && cellValue != null && cellValue.trim().length() > 0) {
                            isRowValid = true;
                        }
                    }
                }
                if (isRowValid) {
                    rowList.add(columnMap);
                }
            }
        }
        return rowList;
    }
    
    private static Workbook getWorkbook(InputStream inputStream)throws Exception{
    	Workbook workbook = null;
        /*
         * 判断文件类型（2003xls/2007xlsx）
         */
        if(!inputStream.markSupported()) {
            inputStream = new PushbackInputStream(inputStream, 8);
        }
        if(POIFSFileSystem.hasPOIFSHeader(inputStream)) {
            workbook = new HSSFWorkbook(inputStream);
        }else if(POIXMLDocument.hasOOXMLHeader(inputStream)) {
            workbook = new XSSFWorkbook(OPCPackage.open(inputStream));
        }
        return workbook;
    }

    /**
     *
     * 功能描述：解析excel文件
     * @param inputStream
     * @param startRow 从第几行开始读取（从0开始）
     * @return
     * @throws Exception
     */
    public static List<LinkedHashMap<String, String>> parseFromInputStream(InputStream inputStream,int startRow,List<String> headerKeys) throws Exception {
    	List<LinkedHashMap<String, String>> rowList = new ArrayList<LinkedHashMap<String, String>>();

        Workbook workbook = getWorkbook(inputStream);
        
        if(workbook!=null){
            rowList = parseFromWorkbook(workbook, startRow,headerKeys);
        }
        return rowList;
    }
    
    /**
     * 
     * 功能描述：取表头
     * @author
     * @param file
     * @param startRow
     * @return
     * @throws Exception
     */
    public static List<String> parseHeaderFromFile(MultipartFile file,int startRow) throws Exception {
    	Workbook workbook = getWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        return parseHeaderFromSheet(sheet,startRow);
    }
    
    /*public static Map<Integer, String> parseHeaderFromInputStream(InputStream inputStream,int startRow) throws Exception {
    	Workbook workbook = getWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        return parseHeaderFromSheet(sheet,startRow);
    }*/
    
    public static List<String>/*Map<Integer, String>*/ parseHeaderFromSheet(Sheet sheet,int startRow) throws Exception {
    	Row ssfRow;
        // 处理表格头标题
        Map<Integer, String> titleKeys = new LinkedHashMap<Integer, String>();
        List<String> header = new LinkedList<String>(); 
        ssfRow = sheet.getRow(startRow);
        if (null != ssfRow) {
            for (int i = ssfRow.getFirstCellNum(); i < ssfRow.getPhysicalNumberOfCells(); i++) {
                Cell cell = ssfRow.getCell(i);
                if (null != cell) {
                    String value = cell.toString();
                    if (AIStringUtil.isNotEmpty(value)) {
                        titleKeys.put(i, value.trim());
                        header.add(value.trim());
                    } else {
                        //titleKeys.put(i, "COL" + i);
                    	titleKeys.put(i, "");
                        header.add("");
                    }
                }
            }
        }
        return header;
    }
    
   
    
    /**
    *
    * 功能描述：解析excel文件
    * @param MultipartFile
    * @param startRow 从第几行开始读取（从0开始）
    * @return
    * @throws Exception
    */
   public static List<LinkedHashMap<String, String>> parseFromFile(MultipartFile file,int startRow,List<String> headerKeys) throws Exception {
	   return parseFromInputStream(file.getInputStream(), startRow,headerKeys);
   }


    public static List<Map<String, String>> parseFromInputStream(InputStream inputStream) throws IOException {
        List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);

        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        HSSFRow hssfRow;
        HSSFCell hssfCell;

        // 处理表格头标题
        Map<Integer, String> titleKeys = new HashMap<Integer, String>();
        hssfRow = hssfSheet.getRow(0);
        if (null != hssfRow) {
            for (int i = hssfSheet.getFirstRowNum(); i < hssfRow.getPhysicalNumberOfCells(); i++) {
                HSSFCell cell = hssfRow.getCell(i);
                if (null != cell) {
                    String value = cell.toString();
                    if (null != value && value.trim().length() != 0) {
                        titleKeys.put(i, value);
                    } else {
                        titleKeys.put(i, "COL" + i);
                    }
                }
            }
        }

        // 处理表格内容数据
        for (int i = hssfSheet.getFirstRowNum() + 1; i < hssfSheet.getPhysicalNumberOfRows(); i++) {
            hssfRow = hssfSheet.getRow(i);
            if (null != hssfRow) {
                boolean isRowValid = false;
                Map<String, String> columnMap = new HashMap<String, String>();
                for (int j = hssfRow.getFirstCellNum(); j < hssfRow.getPhysicalNumberOfCells(); j++) {
                    hssfCell = hssfRow.getCell(j);
                    if (null != hssfCell) {
                        String cellValue = getCellValue(hssfCell);
                        if (cellValue != null && cellValue.trim().length() == 0) {
                            cellValue = null;
                        }
                        columnMap.put(titleKeys.get(j), cellValue);
                        // 如果该行所有列至少一个存在值,则该行合法
                        if (!isRowValid && cellValue != null && cellValue.trim().length() > 0) {
                            isRowValid = true;
                        }
                    }
                }
                if (isRowValid) {
                    rowList.add(columnMap);
                }
            }
        }
        return rowList;
    }



    /**
     * 解析xlsx文件.
     * @param file 文件
     * @return 返回列表
     * @throws Exception 解析异常
     */
    private static List<Map<String, String>> parseXLSXFile(final File file) throws Exception {
        List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();
        InputStream inputStream = new FileInputStream(file);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);

        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        XSSFRow xssfRow;
        XSSFCell xssfCell;

        // 处理表格头标题
        Map<Integer, String> titleKeys = new HashMap<Integer, String>();
        xssfRow = xssfSheet.getRow(0);
        if (null != xssfRow) {
            for (int i = xssfSheet.getFirstRowNum(); i < xssfRow.getPhysicalNumberOfCells(); i++) {
                XSSFCell cell = xssfRow.getCell(i);
                if (null != cell) {
                    String value = cell.toString();
                    if (null != value && value.trim().length() != 0) {
                        titleKeys.put(i, value);
                    } else {
                        titleKeys.put(i, "COL" + i);
                    }
                }
            }
        }

        // 处理表格内容数据
        for (int i = xssfSheet.getFirstRowNum() + 1; i < xssfSheet.getPhysicalNumberOfRows(); i++) {
            xssfRow = xssfSheet.getRow(i);
            if (null != xssfRow) {
                boolean isRowValid = false;
                Map<String, String> columnMap = new HashMap<String, String>();
                for (int j = xssfRow.getFirstCellNum(); j < xssfRow.getPhysicalNumberOfCells(); j++) {
                    xssfCell = xssfRow.getCell(j);
                    if (null != xssfCell) {
                        String cellValue = getCellValue(xssfCell);
                        if (cellValue != null && cellValue.trim().length() == 0) {
                            cellValue = null;
                        }
                        columnMap.put(titleKeys.get(j), cellValue);
                        // 如果该行所有列至少一个存在值,则该行合法
                        if (!isRowValid && cellValue != null && cellValue.trim().length() > 0) {
                            isRowValid = true;
                        }
                    }
                }
                if (isRowValid) {
                    rowList.add(columnMap);
                }
            }
        }
        return rowList;
    }


    /**
     * 获取xls文件单元格内容.
     * @param cell 单元格
     * @return cellValue
     */
    private static String getCellValue(final Cell cell) {
        DecimalFormat df = new DecimalFormat("#");
        String cellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                        cellValue = sdf.format(DateUtil.getJavaDate(cell.getNumericCellValue()));
                        break;
                    }
                    Double value = cell.getNumericCellValue();
                    if(null!=value){
                    	//判断表格内容是否是.0结尾
                    	String cellstr = value.toString();
                    	int end = value.toString().lastIndexOf(".0");
                    	 if(end==cellstr.length()-2){//整数
                    		 cellValue = df.format(value);
                    	 }else{//小数
                         	cellValue = value.toString();
                         }
                         //判断是否为带ｅ的数值
                        if(String.valueOf(cell.getNumericCellValue()).indexOf("E")!=-1){
                            cellValue = new DecimalFormat("#").format(cell.getNumericCellValue());
                        }
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    cellValue = String.valueOf(cell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
//                    cellValue = String.valueOf(cell.getCellFormula());//取公式
                    try {
                        cellValue = cell.getStringCellValue();//取内容
                    } catch (IllegalStateException e) {
                        if(String.valueOf(cell.getNumericCellValue()).indexOf("E")==-1){
                            cellValue = String.valueOf(cell.getNumericCellValue());//不带ｅ的数值取公式计算后的值
                        }else {
                            cellValue = new DecimalFormat("#").format(cell.getNumericCellValue());//带ｅ的数值转化为正常形式
                        }
                    }
                    break;
                case Cell.CELL_TYPE_BLANK:
                    cellValue = null;
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_ERROR:
                    cellValue = String.valueOf(cell.getErrorCellValue());
                    break;
                default:
                    break;
            }
            if (cellValue != null && cellValue.trim().length() == 0) {
                cellValue = null;
            }
        }

        return cellValue;
    }


    /**
     * 获取xls文件单元格内容.
     * @param cell 单元格
     * @return cellValue
     */
    private static String getCellValue(final HSSFCell cell) {
        DecimalFormat df = new DecimalFormat("#");
        String cellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                        cellValue = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                        break;
                    }
                    Double value = cell.getNumericCellValue();
                    if(null!=value){
                    	//判断表格内容是否是.0结尾
                    	String cellstr = value.toString();
                    	int end = value.toString().lastIndexOf(".0");
                    	 if(end==cellstr.length()-2){//整数
                    		 cellValue = df.format(value);
                    	 }else{//小数
                         	cellValue = value.toString();
                         }
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    cellValue = String.valueOf(cell.getStringCellValue());
                    break;
                case HSSFCell.CELL_TYPE_FORMULA:
                    cellValue = String.valueOf(cell.getCellFormula());
                    break;
                case HSSFCell.CELL_TYPE_BLANK:
                    cellValue = null;
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case HSSFCell.CELL_TYPE_ERROR:
                    cellValue = String.valueOf(cell.getErrorCellValue());
                    break;
                default:
                    break;
            }
            if (cellValue != null && cellValue.trim().length() == 0) {
                cellValue = null;
            }
        }

        return cellValue;
    }

    /**
     * 获取xlsx单元格内容.
     * @param cell 单元格
     * @return cellValue
     */
    private static String getCellValue(final XSSFCell cell) {
        DecimalFormat df = new DecimalFormat("#");
        String cellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        cellValue = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                        break;
                    }
                    Double value = cell.getNumericCellValue();
                    if(null!=value){
                    	//判断表格内容是否是.0结尾
                    	String cellstr = value.toString();
                    	int end = value.toString().lastIndexOf(".0");
                    	 if(end==cellstr.length()-2){//整数
                    		 cellValue = df.format(value);
                    	 }else{//小数
                         	cellValue = value.toString();
                         }
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    cellValue = String.valueOf(cell.getStringCellValue());
                    break;
                case HSSFCell.CELL_TYPE_FORMULA:
                    cellValue = String.valueOf(cell.getCellFormula());
                    break;
                case HSSFCell.CELL_TYPE_BLANK:
                    cellValue = null;
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case HSSFCell.CELL_TYPE_ERROR:
                    cellValue = String.valueOf(cell.getErrorCellValue());
                    break;
                default:
                    break;
            }
            if (cellValue != null && cellValue.trim().length() <= 0) {
                cellValue = null;
            }
        }
        return cellValue;
    }

    /**
     *
     * 功能描述：检验文件
     * @author
     * @param importFile
     * @param fileMaxSize 文件大小上限(KB)
     * @param fileTypes 文件类型[xls,doc,jpg,gif](默认为xls\xlsx)
     * @return
     */
    public static ResultModel checkFile(MultipartFile importFile,Long fileMaxSize,String...fileTypes){
        ResultModel rm = new ResultModel();
        if(importFile==null||importFile.isEmpty()){
            rm.setMessage("上传文件不存在");
            return rm;
        }
        if(fileMaxSize!=null){
            long filesize = importFile.getSize();//文件大小（字节）
            if(filesize>(fileMaxSize*1024)){
                rm.setMessage("上传文件大小不能超过"+fileMaxSize/1024+"M");
                return rm;
            }
        }
        if(fileTypes==null){
        	fileTypes = new String[]{FileType.XLS_FILE_TYPE,FileType.XLSX_FILE_TYPE};
        }
        if(fileTypes!=null&&fileTypes.length>0){
            String fileName = importFile.getOriginalFilename();
            // 获取上传文件扩展名
            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            // 对扩展名进行小写转换
            fileExt = fileExt.toLowerCase();

            boolean flag = false;
            for(String fileType :fileTypes){
                if(fileType.toLowerCase().equalsIgnoreCase(fileExt)){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                String str = "";
                for(String fileType :fileTypes){
                    str += "、"+fileType;
                }
                str = str.substring(1);
                rm.setMessage("请上传"+str+"格式的文件");
            }

        }

        rm.setSuccess(true);
        return rm;
    }

    public static void main(String[] args) throws Exception {
        String filePath = "/Users/sushiting/IdeaProjects/newnsmp/fjcrm/newnsmp/web/newnsmp/target/a.xls";
        String title = "网格指标导入";
        List<String> rowTitle = new ArrayList<>();
        rowTitle.add("网格名称");
        rowTitle.add("网格编码");
        rowTitle.add("指标一");
        rowTitle.add("指标二");
        rowTitle.add("指标三");

        List<List<String>> data = new ArrayList<>();

        List<String> titleData = Arrays.asList("网格名称", "网格编码", "ZB1", "ZB2", "ZB3");
        data.add(titleData);
        List<String> fstRowData = Arrays.asList("A", "AGRID", "1", "2", "3");

        data.add(fstRowData);
        for(int i=0;i<10;i++) {
            List d = new ArrayList();
            d.add("网格" + i);
            data.add(d);
        }

//        ExcelUtils.writeExcel(new FileOutputStream(new File(filePath)),"xls", title,rowTitle, data);
    }

}
