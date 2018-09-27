package com.utils.codeModel;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Title:导入文件返回结果
 * @Description:
 *
 */
public class ExcelResultModel {

	private int successNum;//成功个数
	private int failNum;//失败个数
	private boolean exportFile = true;//是否导出错误文件
	private String fileName = "失败记录";
	private String returnViewStr = "common/import-excel-result";//返回页面
	private List<Map<String,Object>> excelErrorList;//导入数据中错误记录
	private List<String> errorMsgList;//错误原因列表（与导入数据中错误记录对应）（为空则不添加错误信息列）
	private String sheetName;
	private List<String> colmnuHeaders;//表格头
	private Map<String/*header*/,String/*dataKey*/> colKeyMap;//数据map对应的key和标题(如不传按colmnuHeaders的值取数据)
	private String fileBatchId;
	private String message;//提示信息
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getSuccessNum() {
		return successNum;
	}
	public void setSuccessNum(int successNum) {
		this.successNum = successNum;
	}
	public int getFailNum() {
		return failNum;
	}
	public void setFailNum(int failNum) {
		this.failNum = failNum;
	}
	public boolean isExportFile() {
		return exportFile;
	}
	public void setExportFile(boolean exportFile) {
		this.exportFile = exportFile;
	}
	public String getReturnViewStr() {
		return returnViewStr;
	}
	public void setReturnViewStr(String returnViewStr) {
		this.returnViewStr = returnViewStr;
	}
	public List getExcelErrorList() {
		return excelErrorList;
	}
	public void setExcelErrorList(List excelErrorList) {
		this.excelErrorList = excelErrorList;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public List<String> getColmnuHeaders() {
		return colmnuHeaders;
	}
	public void setColmnuHeaders(List<String> colmnuHeaders) {
		this.colmnuHeaders = colmnuHeaders;
	}
	public String getFileBatchId() {
		return fileBatchId;
	}
	public void setFileBatchId(String fileBatchId) {
		this.fileBatchId = fileBatchId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<String> getErrorMsgList() {
		return errorMsgList;
	}
	public void setErrorMsgList(List<String> errorMsgList) {
		this.errorMsgList = errorMsgList;
	}
	public Map<String, String> getColKeyMap() {
		return colKeyMap;
	}
	public void setColKeyMap(Map<String, String> colKeyMap) {
		this.colKeyMap = colKeyMap;
	}

	
	
	
	
	
}
