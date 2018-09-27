package com.controller;

import javax.servlet.http.HttpServletRequest;


import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.utils.flexigrid.FlexiGridResult;

import com.bean.Category;
import com.service.ICategorySV;

import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.model.CategoryModel;


@Controller
@RequestMapping("/category") 
public class CategoryController extends com.controller.CommonController{
	@Autowired
	ICategorySV iCategorySV;
	
	//接口入口
	@RequestMapping(params ="index")
	public String index(HttpServletRequest request) {
		// return "toplogin";
		return "sceneInfoFind-index";
	}
	
	//按条件分页查询
	@RequestMapping(params = "selectFlexgridByExample") 
    @ResponseBody
    public FlexiGridResult selectFlexgridByExample(HttpServletRequest request,HttpServletResponse response,CategoryModel model){
		Map<String, String> colDictMap = new HashMap<String,String>();   
		return super.flexiGridJsonResult(request,response,iCategorySV.selectFlexgridByExample(model,colDictMap));
    }
	
	@ResponseBody
	@RequestMapping("/selectById")
	public Category toIndex(HttpServletRequest request){
		int userId = Integer.parseInt(request.getParameter("id"));
		Category category= iCategorySV.getNameById(userId);
		//model.addAttribute("c", category);
		return category;
	}
	@ResponseBody
	@RequestMapping("/insert")
	public int toAdd(HttpServletRequest request){
		Category category=new Category();
		category.setName(request.getParameter("name"));
		int record=iCategorySV.addCategory(category);
		//model.addAttribute("category", record);
		return record;
	}
	@ResponseBody
	@RequestMapping("/update")
	public int toUpdate(HttpServletRequest request){
		Category category=new Category();
		category.setId(Integer.parseInt(request.getParameter("id")));
		category.setName(request.getParameter("name"));
		int record=iCategorySV.update(category);
		//model.addAttribute("list", record);
		return record;
	}
	@ResponseBody
	@RequestMapping("/all")
	public List<Category> toAll(HttpServletRequest request){
		List<Category> category= iCategorySV.list();
		//model.addAttribute("c", category);
		return category;
	}
	
	@ResponseBody
	@RequestMapping("/del")
	public void toDel(HttpServletRequest request){
		int userId = Integer.parseInt(request.getParameter("id"));
		iCategorySV.del(userId);
		//model.addAttribute("c", category);
		return ;
	}
}