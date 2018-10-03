package com.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.model.NoDoModer;
import com.service.NoDoSV;
import com.utils.flexigrid.FlexiGridResult;

@Controller
@RequestMapping("/Corrections")
public class NoDoController extends CommonController {
	@Autowired
	NoDoSV noDoSV;
	
	@RequestMapping(params ="index")
	public String index(HttpServletRequest request) {
		// return "toplogin";
		return "Corrections/show";
	}
	
     @RequestMapping(params = "selectFlexgridByExample") 
	 @ResponseBody
	 public FlexiGridResult selectFlexgridByExample(HttpServletRequest request,HttpServletResponse response,NoDoModer model){
	        /*
	         * 数据字典转换MAP
	         */
	        Map<String, String> colDictMap = new HashMap<String,String>();    
	       
	        return super.flexiGridJsonResult(request,response,noDoSV.selectFlexgridByExample(model,colDictMap));
	 } 

	

}
