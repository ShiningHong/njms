package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bean.User;
import com.model.UserModel;
import com.service.ICategorySV;
import com.service.IUserSV;
import com.utils.WebUtils;


import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/login")
public class LoginController extends CommonController {

	@Autowired
	IUserSV iUserSV;
	/**
	 * 
	 * 功能描述：主页接口
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
	/**
	 * 
	 * 功能描述：检验密码是否正确
	 * <p>
	 */
	@RequestMapping("/checkLogin")
	@ResponseBody 
	public boolean checkLogin(HttpServletRequest request,String userLoginName,String userPwd){
		UserModel userModel=new UserModel();
		userModel.setUsername(userLoginName);
		//查找出该账户的密码及角色
		List<User> users=iUserSV.selectByExample(userModel);
		if(users.size()==0)
			return false;
		//获取加密后的HD5字符串
		boolean checkLogin=iUserSV.checkPsw(userPwd, users.get(0).getPassword());
		//将登陆号和角色存到session中
		if(true==checkLogin){
			WebUtils.getSession().setAttribute("UserSession",users.get(0));
		}
		return checkLogin;
	}
}
