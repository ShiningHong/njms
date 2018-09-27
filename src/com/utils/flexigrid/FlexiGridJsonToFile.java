/**
 * FlexiGridJsonToFile.java	  V1.0   Feb 24, 2012 4:45:51 PM
 *
 * Copyright 2011 AsiaInfo Linkage, All rights reserved.
 *
 * Modification history(By    Time    Reason):
 * 
 * Description:
 */

package com.utils.flexigrid;

import java.io.InputStream;


import java.io.OutputStream;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utils.general.TempFileManager;


/**
 * 功能描述：FlexiGrid的JSON数据转Excel
 *
 * @author  池文杉
 *
 * <p>修改历史：(修改人，修改时间，修改原因/内容)</p>
 */
public class FlexiGridJsonToFile {
    
    //日志信息
    private static Logger logger = LoggerFactory.getLogger(FlexiGridJsonToFile.class);
            
    
    /**
     * 
     * 功能描述：将FlexGrids所要求格式的json数据，根据列的要求，写入Excel文件中；
     *
     * @author  池文杉
     * <p>创建日期 ：Feb 27, 2012 4:15:16 PM</p>
     *
     * @param gridData flexgrids要求的数据json格式；对应JavaBean：FlexiGridResult;
     * @param colModel flexgrids要求的列模型；多个列，之间采用 , 分割；关注cloModel 中的 display ,name 两个属性；
     *          display:显示的名称， name   用于和gridData 做映射；
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public static InputStream flexGrid2Excel(String gridData,String colModel){
        
        ///将参数，统一专为对应的JavaBean 进行相关处理
    	FlexiGridResult gridResult = FlexiGridResult.parseGridDataJson(gridData);
    	return flexGrid2Excel(gridResult, colModel);
    }
    
    /**
     * 
     * 功能描述：将FlexGrids所要求格式的数据，根据列的要求，写入Excel文件中；
     * @param gridResult 
     * @param colModel flexgrids要求的列模型；多个列，之间采用 , 分割；关注cloModel 中的 display ,name 两个属性；
     *          display:显示的名称， name   用于和gridData 做映射；
     * @return
     */
    public static InputStream flexGrid2Excel(FlexiGridResult gridResult,String colModel){
    	 ///将参数，统一专为对应的JavaBean 进行相关处理
        List<FlexiGridColModel> colModelList = FlexiGridColModel.parseColModelList(colModel);
        InputStream inputStream = null; 
        try{
            HSSFWorkbook workBook =  new HSSFWorkbook();
            if(gridResult == null){
                exportExcel(workBook,colModelList , null);
            } else {
                exportExcel(workBook,colModelList , gridResult.getRows());
            }
            
            OutputStream os = TempFileManager.getTempFileOutputStream();
            if (workBook != null) {
                workBook.write(os);
            }
            os.flush();
            os.close();
            os = null;
            workBook = null;
            inputStream = TempFileManager.getTempFileInputStream();
        } catch(Exception err){
            logger.error("查询数据列表生成Excel信息异常；描述："+err.getMessage());
        } finally{
            gridResult = null;
            colModelList.clear();
            colModelList = null;
        }
        return inputStream;
    }
    
    
    private static void exportExcel(HSSFWorkbook workbook,List<FlexiGridColModel> colModelList,List<FlexiGridRow> gridDataList){
        
        // 创建工作簿实例
        //HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建工作表实例
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        
        int rowNums = 0;
        if(colModelList!=null && colModelList.size()>0){
            //输出表头，而后根据表头信息，输出数据列；
            exportExcelHeaderWithColModel(sheet,colModelList);
            rowNums ++;
            exportExcelDataWithColModel(sheet,colModelList,gridDataList,rowNums);
        } else {
            //如果提供的列模型为空，表示没有设定表头信息，那么以第一行的列名称作为表头信息；输出；
            if(gridDataList == null || gridDataList.size()==0){
                //木有数据，直接输出"查无资料"，而后结束；
                createCell(sheet.createRow(1), 0, null, HSSFCell.CELL_TYPE_STRING, "查无资料");
                //return workbook;
            } else {
                //获取列名信息；
                Set<String> keySet = gridDataList.get(0).getCell().keySet();
                exportExcelHeaderWithKeySet(sheet,keySet);
                rowNums ++;
                exportExcelDataWithKeySet(sheet,keySet,gridDataList,rowNums);
            }
        }
        
       // return workbook;
    }
    
    
    /**
     * 
     * 功能描述：通过列模型输出表头
     *
     * @author  池文杉
     * <p>创建日期 ：Feb 28, 2012 4:57:27 PM</p>
     *
     * @param sheet
     * @param colModelList
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    private static void exportExcelHeaderWithColModel(HSSFSheet sheet,List<FlexiGridColModel> colModelList){
        HSSFRow row = sheet.createRow(0);// 建立新行
        for (int i = 0; i < colModelList.size(); i++) {
            createCell(row, i, null, HSSFCell.CELL_TYPE_STRING, colModelList.get(i).getDisplay());
        }
    }
    
    /**
     * 
     * 功能描述：在没有设置列模型的基础上，按照列的Key域设置表头
     *
     * @author  池文杉
     * <p>创建日期 ：Feb 28, 2012 5:33:02 PM</p>
     *
     * @param sheet
     * @param keySet
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    private static void exportExcelHeaderWithKeySet(HSSFSheet sheet,Set<String> keySet){
        HSSFRow row = sheet.createRow(0);// 建立新行
        int i = 0;
        for(String key : keySet){
            createCell(row, i, null, HSSFCell.CELL_TYPE_STRING, key);
            i++;
        }
    }
    
    /**
     * 
     * 功能描述：根据列模型，输出数据部分；
     *
     * @author  池文杉
     * <p>创建日期 ：Feb 28, 2012 5:20:01 PM</p>
     *
     * @param sheet
     * @param colModelList
     * @param gridDataList
     * @param startRow
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    private static void exportExcelDataWithColModel(HSSFSheet sheet,List<FlexiGridColModel> colModelList,List<FlexiGridRow> gridDataList,int startRow){
        //如果没有数据列，那么直接输出查无资料；
        if(gridDataList == null || gridDataList.size()==0){
            createCell(sheet.createRow(startRow), 0, null, HSSFCell.CELL_TYPE_STRING, "查无资料");
            return;
        }
        
        for(int i=0;i<gridDataList.size();i++){
            FlexiGridRow gridRow = gridDataList.get(i);
            HSSFRow row = sheet.createRow(startRow+i);
            for(int colIndex = 0 ;colIndex <colModelList.size();colIndex++){
                FlexiGridColModel colModel = colModelList.get(colIndex);
                createCell(row, colIndex, null, HSSFCell.CELL_TYPE_STRING, gridRow.getCell().get(colModel.getName()));
            }
        }
    }
    
    /**
     * 
     * 功能描述：在没有设置列模型的基础上，按照列的Key域，以及值的情况生活列信息；
     *
     * @author  池文杉
     * <p>创建日期 ：Feb 28, 2012 7:29:42 PM</p>
     *
     * @param sheet
     * @param keySet
     * @param gridDataList
     * @param startRow
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    private static void exportExcelDataWithKeySet(HSSFSheet sheet,Set<String> keySet,List<FlexiGridRow> gridDataList,int startRow){
      //如果没有数据列，那么直接输出查无资料；
        if(gridDataList == null || gridDataList.size()==0){
            createCell(sheet.createRow(startRow), 0, null, HSSFCell.CELL_TYPE_STRING, "查无资料");
            return;
        }
        
        for(int i=0;i<gridDataList.size();i++){
            FlexiGridRow gridRow = gridDataList.get(i);
            HSSFRow row = sheet.createRow(startRow+i);
            int colIndex = 0;
            for(String key : keySet){
                createCell(row, colIndex, null, HSSFCell.CELL_TYPE_STRING, gridRow.getCell().get(key));
                colIndex++;
            }
        }
    }
    
    /**
     * 
     * 功能描述：创建Excel单元格
     *
     * @author  池文杉
     * <p>创建日期 ：Feb 28, 2012 4:56:48 PM</p>
     *
     * @param row 单元格所在行
     * @param column 单元格所在列数
     * @param style 单元格 样式
     * @param cellType 单元格值类型
     * @param value 单元格值
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    private static void createCell(HSSFRow row, int column, HSSFCellStyle style, int cellType, Object value) {
        HSSFCell cell = row.createCell(column);
        if (style != null) {
            cell.setCellStyle(style);
        }
        switch (cellType) {
        case HSSFCell.CELL_TYPE_BLANK:
            break;
        case HSSFCell.CELL_TYPE_STRING: {
            cell.setCellValue(value == null ? "" : value.toString());
        }
            break;
        case HSSFCell.CELL_TYPE_NUMERIC: {
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue(Double.parseDouble(value.toString()));
        }
            break;
        default:
            break;
        }
    }
}