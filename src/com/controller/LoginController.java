package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/login")
public class LoginController extends CommonController {

	/**
	 * 
	 * 功能描述：登陆路口
	 * @param request
	 * @return
	 * <p>
	 */
	@RequestMapping(params ="loginIndex")
	public String loginIndex(HttpServletRequest request) {
		// return "toplogin";
		//return "sceneInfoFind-index";
		return "home";
	}
	
	@RequestMapping(params ="loginLeftIndex")
	public String loginLeftIndex(HttpServletRequest request) {
		// return "toplogin";
		//return "sceneInfoFind-index";
		return "left-menu";
	}
	
	@RequestMapping(params ="corseUI")
	public String corseUI(HttpServletRequest request) {
		// return "toplogin";
		//return "sceneInfoFind-index";
		return "course/course-index";
	}
}
