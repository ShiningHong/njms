package com.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 
 * @Title:
 * @Description:
 *
 * @author 池文杉
 * <p>
 * </p>
 */
public class ContextHolderUtils {
	
//	private static HttpServletRequest request;
//	private static HttpServletResponse response;
//	
//
//	public static void setRequest(HttpServletRequest request_) {
//		if(request==null){
//			request = request_;
//		}
//	}
//	public static void setResponse(HttpServletResponse response_) {
//		if(response==null){
//			response = response_;
//		}
//	}
//	
//	public static void clearRequest(){
//		request=null;
//	}
//	
//	public static void clearResponse(){
//		response=null;
//	}
//	
	
	
	/**
	 * SpringMvc�»�ȡrequest
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		if(request==null){
//			request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		}
		return request;
	}
	/**
	 * SpringMvc�»�ȡresponse
	 * @return
	 */
	public static HttpServletResponse getResponse(){
		HttpServletResponse response = ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
//		if(response==null){
//			response = ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
//		}
		return response;
	}
	/**
	 * SpringMvc�»�ȡsession
	 * 
	 * @return
	 */
	public static HttpSession getSession() {
		HttpSession session = getRequest().getSession();
		return session;
	}
	
	/**
	 * �������·��
	 * @param request
	 * @return
	 */
	public static String getRequestPath2(HttpServletRequest request) {
		String requestPath = request.getRequestURI() + "?" + request.getQueryString();
		if (requestPath.indexOf("&") > -1) {// ȥ���������
			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
		}
		requestPath = requestPath.substring(request.getContextPath().length() + 1);// ȥ����Ŀ·��
		return requestPath;
	}
	
	/**
	 * @param request
	 * @return
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String requestPath = request.getRequestURI() + "?" + request.getQueryString();
		if (requestPath.indexOf("&",2) > -1) {
			requestPath = requestPath.substring(0, requestPath.indexOf("&",2));
		}
		requestPath = requestPath.substring(request.getContextPath().length() + 1);// ȥ����Ŀ·��
		return requestPath;
	}
	
	/**
	 * @param request
	 * @return
	 */
	public static String getQueryStr(HttpServletRequest request){
		String queryStr = request.getQueryString();
		if(!StringUtils.isEmpty(queryStr)&&queryStr.indexOf("&") > -1){
			queryStr = queryStr.substring(queryStr.indexOf("&"));
			return queryStr;
		}
		return "";
	}
	
}
