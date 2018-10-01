package com.controller.base;

import java.io.File;


import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


public class BaseController {

	public void setRequestAttribute(HttpServletRequest request,String attrName,Object attrObj){
		request.setAttribute(attrName, attrObj);
	}
	
	public void setRequestAttributes(HttpServletRequest request,String[] attrName,Object[] attrObj){
		if(attrName==null||attrObj==null)return ;
		if(attrName.length==attrObj.length){
			for(int i=0;i<attrName.length;i++){
				setRequestAttribute(request, attrName[i], attrObj[i]);
			}
		}
	}
	
	public void setRequestAttributes(HttpServletRequest request,Map<String,Object> attrMap){
		if(attrMap!=null&&attrMap.size()>0){
			for(String attrName : attrMap.keySet()){
				setRequestAttribute(request, attrName, attrMap.get(attrName));
			}
		}
	}
	
	public void setResponseContent(Object o,HttpServletResponse response,String contentType){
		
		try {
			if(response!=null){
				
				if(StringUtils.isEmpty(contentType))contentType = "text/xml; charset=UTF-8";
				
				response.setContentType(contentType);
				
				if(o.getClass().getName().indexOf("String")>-1){
					
					response.getWriter().print(o);
					
				}else{
					
					StringBuffer sb = new StringBuffer();
					sb.append(o);
					response.getWriter().print(sb);
				}
				
			}
			
		} catch (IOException e) { 
			
			e.printStackTrace();
			
		}
	}
	
	public void setTextResponseContent(Object o,HttpServletResponse response){
		this.setResponseContent(o, response, "text/plain; charset=GBK");
	}
	
	public void renderJson(JSONObject jsonObj,HttpServletResponse response) {
		this.setResponseContent(jsonObj, response, null);
	}
	
	
	/**
	 * 
	 * 功能描述：重定向返回(带参�?（跳转到另一个controller/重定向到页面�?
	 * @param viewStr 重定向地�?
	 *        1.重定向到页面，带完整jsp路径(.jsp)  2.跳转到另�?��controller，类mapping.do?方法mappingParam
	 *        
	 * @param modelMap 参数Map<String参数�?Object参数�?//attributeName,attributeValue
	 * 
	 * @return ModelAndView
	 * <p>
	 * 修改历史 �?修改人，修改时间，修改原�?内容)
	 * </p>
	 */
	public ModelAndView redirectView(String viewStr,Map<String,Object> modelMap){
		ModelAndView modelAndView = null;
		modelAndView = new ModelAndView(new RedirectView(viewStr));
		// 重定向还有一种简单写�?
        // modelAndView = new ModelAndView("redirect:index.jsp");
		
		if(modelMap!=null)modelAndView.addAllObjects(modelMap);
		return modelAndView;
	}
	
	/**
	 * 
	 * 功能描述：重定向返回(带一个参�?（跳转到另一个controller/重定向到页面�?
	 * @param viewStr 重定向地�?
	 *        1.重定向到页面，带完整jsp路径(.jsp)  2.跳转到另�?��controller，类mapping.do?方法mappingParam
	 *        
	 * @param attributeName 参数�?
	 * @param attributeValue 参数�?
	 * @return ModelAndView
	 * 
	 */
	public ModelAndView redirectView(String viewStr,String attributeName,Object attributeValue){
		
		Map<String,Object> modelMap = new LinkedHashMap<String, Object>();
		modelMap.put(attributeName, attributeValue);
		
		return redirectView(viewStr, modelMap);
	}
	
	public ModelAndView redirectView(String viewStr){
		return redirectView(viewStr, null);
	}
	
	/**
	 * 
	 * 功能描述：返回ModelAndView(带参�?(跳转到页面，等同于返回string)
	 * @param viewStr 返回jsp路径，截取从WEB-INF/jsp/之后�?jsp之前的字符串
	 * @param modelMap 参数Map<String参数�?Object参数�? //attributeName,attributeValue
	 * @return ModelAndView
	 * 
	 */
	public ModelAndView pageView(String viewStr,Map<String,Object> modelMap){
		ModelAndView modelAndView = new ModelAndView(this.formatPath(viewStr));
		if(modelMap!=null)modelAndView.addAllObjects(modelMap);
		return modelAndView;
	}
	
	/**
	 * 
	 * 功能描述：返回ModelAndView(带一个参�?(跳转到页面，等同于返回string)
	 * @param viewStr 返回jsp路径，截取从WEB-INF/jsp/之后�?jsp之前的字符串
	 * @param attributeName 参数�?
	 * @param attributeValue 参数�?
	 * @return
	 * <p>
	 * 修改历史 �?修改人，修改时间，修改原�?内容)
	 * </p>
	 */
	public ModelAndView pageView(String viewStr,String attributeName,Object attributeValue){
		Map<String,Object> modelMap = new LinkedHashMap<String, Object>();
		modelMap.put(attributeName, attributeValue);
		return pageView(viewStr, modelMap);
	}
	
	/**
	 * 
	 * 功能描述：返回ModelAndView(不带参数)(跳转到页面，等同于返回string)
	 * @param viewStr 返回jsp路径，截取从WEB-INF/jsp/之后�?jsp之前的字符串
	 * @return ModelAndView
	 * <p>
	 * 修改历史 �?修改人，修改时间，修改原�?内容)
	 * </p>
	 */
	public ModelAndView pageView(String viewStr){
		return pageView(viewStr, null);
	}
	
	protected static String formatPath(String path){
		return path.replace("\\", File.separator);
	}

}
