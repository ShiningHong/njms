package com.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.utils.flexigrid.FlexiGridResult;


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
		return gridResult;
		
	}
}
