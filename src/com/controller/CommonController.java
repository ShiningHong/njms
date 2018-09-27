package com.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.utils.codeModel.ExcelResultModel;
import com.utils.flexigrid.FlexiGridJsonToFile;
import com.utils.flexigrid.FlexiGridResult;
import com.utils.general.AIStringUtil;

@Controller
public class CommonController extends com.controller.base.BaseController {

	// protected static final String view_path = formatPath("jsp\\");
	@RequestMapping("/toplogin")
	public String topLogin(HttpServletRequest request) {
		// return "toplogin";
		return "toplogin";
	}
	
	// ///////////////////////////////////flexigrid相关方法///////////////////////////////////////////////

	/**
	 * 
	 * 功能描述：返回flexigrid列表数据（FlexiGridResult类型，常用）
	 * 
	 * @param
	 * @return FlexiGridResult
	 */
	public FlexiGridResult flexiGridJsonResult(HttpServletRequest request,
			HttpServletResponse response, FlexiGridResult gridResult) {
		String flexiGridType = request.getParameter("flexiGridType");
		if (AIStringUtil.isEquals(flexiGridType, "FlexiGridExcelDown")) {// 导出
			String colModel = request.getParameter("flexiGirdColModel");
			flexiGridExcelDown(response, gridResult, colModel);
			return null;

		} else {
			return gridResult;
		}
	}

	private void flexiGridExcelDown(HttpServletResponse response,
			FlexiGridResult gridResult, String flexiGirdColModel) {
		InputStream inputStream = FlexiGridJsonToFile.flexGrid2Excel(
				gridResult, flexiGirdColModel);
		this.excelDown(response, inputStream);
	}

	private void excelDown(HttpServletResponse response, InputStream inputStream) {
		response.addHeader("pragma", "NO-cache");
		response.addHeader("Cache-Control", "no-cache");
		response.addDateHeader("Expries", 0);
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		String filename = "download.xls";
		try {
			filename = new String(filename.getBytes("UTF-8"), "ISO8859_1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.addHeader("Content-Disposition", "attachment;filename="
				+ filename);

		OutputStream out = null;
		try {
			out = response.getOutputStream();
			int length = 0;
			byte buffer[] = new byte[1024];
			while ((length = inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, length);
			}
		} catch (Exception e) {

		} finally {
			// close....
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@RequestMapping("/downLoadTempletFile")
	public void downLoadTempletFile(HttpServletResponse response,
			HttpServletRequest request, String fileName, String templetId) {

	}

	/**
	 * 
	 * 功能描述：返回公用导入结果错误页面
	 * 
	 * @param
	 * @return
	 */
	public ModelAndView returnToExcelResultError(String errorMsg) {
		ExcelResultModel rm = new ExcelResultModel();
		rm.setMessage(errorMsg);
		rm.setExportFile(false);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("excelResult", rm);
		return this.pageView(rm.getReturnViewStr(), map);
	}

	/**
	 * 
	 * 功能描述：返回公用导入结果页面（同时提供错误信息下载）
	 * 
	 * @param rm
	 * @return
	 *//*
	public ModelAndView returnToExcelResult(ExcelResultModel rm) {
		if (rm == null)
			rm = new ExcelResultModel();
		Map<String, Object> map = new HashMap<String, Object>();
		if (rm.getExcelErrorList() == null || rm.getFailNum() < 1
				|| rm.getColmnuHeaders() == null) {
			rm.setExportFile(false);
		}
		if (rm.isExportFile() && rm.getFailNum() > 0) {// 导出错误信息文件
			try {
				List excelErrorList = rm.getExcelErrorList();
				List<String> colmnuHeaders = rm.getColmnuHeaders();
				Map<String, String> colKeyMap = rm.getColKeyMap();
				int tcols = 0;
				if (colmnuHeaders != null) {
					tcols = colmnuHeaders.size();
				} else if (colKeyMap != null) {
					tcols = colKeyMap.size();
				}

				String[] keys = null;
				if (tcols > 0) {
					keys = new String[tcols];
					if (colKeyMap != null && colmnuHeaders != null) {
						for (int i = 0; i < colmnuHeaders.size(); i++) {
							keys[i] = colKeyMap.get(colmnuHeaders.get(i));
						}
					} else if (colKeyMap != null) {
						keys = colKeyMap.keySet().toArray(new String[] {});
					} else if (colmnuHeaders != null) {
						keys = colmnuHeaders.toArray(new String[] {});
					}
				}

				String sheetName = rm.getSheetName();
				if (AIStringUtil.isEmpty(sheetName)) {
					sheetName = rm.getFileName();
				}
				Workbook workBook = ExcelUtils.createWorkBook(sheetName,
						excelErrorList, keys, colmnuHeaders);
				ByteArrayOutputStream ot = new ByteArrayOutputStream();
				workBook.write(ot);
				ByteArrayInputStream in = new ByteArrayInputStream(
						ot.toByteArray());
				ot.flush();
				ISysFileSV iSysFileSV = (ISysFileSV) ApplicationContextBeanFactory
						.getBean("iSysFileSV");
				ResultModel uploadRm = iSysFileSV.uploadFile(in, null,
						rm.getFileName() + "."
								+ ExcelUtils.FileType.XLS_FILE_TYPE, "",
						UserCache.getUserId());

				if (uploadRm.isSuccess() && uploadRm.getObj() != null) {
					SysFile file = (SysFile) uploadRm.getObj();
					// rm.setFileBatchId(file.getBatchId()+"");
					rm.setFileBatchId(file.getFileUuid());
				}
				// map.put("import_fileBatchId", rm.getFileBatchId());
			} catch (Exception e) {
				// excelErrorList=null;
				e.getStackTrace();
				rm.setExportFile(false);
			}

		}

		map.put("excelResult", rm);
		
		 * map.put("import_message", rm.getMessage()); map.put("import_failNum",
		 * rm.getFailNum()); map.put("import_successNum", rm.getSuccessNum());
		 * map.put("import_exportFile", rm.isExportFile());
		 

		return this.pageView(rm.getReturnViewStr(), map);
	}*/
}
