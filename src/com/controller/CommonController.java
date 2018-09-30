package com.controller;

import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
