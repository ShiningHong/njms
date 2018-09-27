/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: WebUtils.java,v 1.1 2017/08/09 06:34:01 chenyn3 Exp $
 */
package com.utils;

import java.io.IOException;

import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.utils.ContextHolderUtils;


/**
 * Http与Servlet工具类.
 * 
 * @author calvin,fangll
 */
public class WebUtils {

	public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

	/**
	 * 设置客户端缓存过期时间 Header.
	 */
	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
		//Http 1.0 header
		response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000);
		//Http 1.1 header
		response.setHeader("Cache-Control", "max-age=" + expiresSeconds);
	}

	/**
	 * 设置客户端无缓存Header.
	 */
	public static void setNoCacheHeader(HttpServletResponse response) {
		//Http 1.0 header
		response.setDateHeader("Expires", 0);
		//Http 1.1 header
		response.setHeader("Cache-Control", "no-cache");
	}

	/**
	 * 设置LastModified Header.
	 */
	public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
		response.setDateHeader("Last-Modified", lastModifiedDate);
	}

	/**
	 * 设置Etag Header.
	 */
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader("ETag", etag);
	}

	/**
	 * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
	 * 
	 * 如果无修改, checkIfModify返回false ,设置304 not modify status.
	 * 
	 * @param lastModified 内容的最后修改时间.
	 */
	public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response,
			long lastModified) {
		long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		return true;
	}

	/**
	 * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
	 * 
	 * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
	 * 
	 * @param etag 内容的ETag.
	 */
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
		String headerValue = request.getHeader("If-None-Match");
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!"*".equals(headerValue)) {
				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag)) {
						conditionSatisfied = true;
					}
				}
			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader("ETag", etag);
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查浏览器客户端是否支持gzip编码.
	 */
	public static boolean checkAccetptGzip(HttpServletRequest request) {
		//Http1.1 header
		String acceptEncoding = request.getHeader("Accept-Encoding");

		if (StringUtils.contains(acceptEncoding, "gzip")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 设置Gzip Header并返回GZIPOutputStream.
	 */
	public static OutputStream buildGzipOutputStream(HttpServletResponse response) throws IOException {
		response.setHeader("Content-Encoding", "gzip");
		response.setHeader("Vary", "Accept-Encoding");
		return new GZIPOutputStream(response.getOutputStream());
	}

	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 * 
	 * @param fileName 下载后的文件名.
	 */
	public static void setDownloadableHeader(HttpServletResponse response, String fileName) {
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> getParametersStartingWith(HttpServletRequest request, String prefix) {
		Enumeration paramNames = request.getParameterNames();
		Map params = new TreeMap();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {
					// Do nothing, no values found at all.
				}
				else if (values.length > 1) {
					params.put(unprefixed, values);
				}
				else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}

	public static String getBasePath(HttpServletRequest request){   
		/*
		String path	= request.getContextPath();
		String basePath	= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		return basePath;
		*/
		String path = "";
		String basePath	= "";
		if(request==null){
			request = ContextHolderUtils.getRequest();
		}
		if(request!=null){
			path  = request.getContextPath();
			basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		}
        return basePath;
	}    
	
	public static String getBasePath(){   
		return getBasePath(null);
	}

//	public static Integer getPageSize(){
//		return Constants.pageSize;
//	}
	/**
	 * put request param to HashMap 
	 * param must be single String 
	 * form enctype != multipart/form-dat 
	 * @param request       
	 * @return              HashMap key=value, name=StringValue
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String,String> getNormalMap(HttpServletRequest request) {
		HashMap<String,String> map = new HashMap<String,String>();
		Enumeration names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String key = (String) names.nextElement();
			String value = request.getParameter(key);
			value = value == null ? "" : value.trim();
			value = value.equals("") ? null : value;
			map.put(key, value);
		}
		return map;
	}

//	@SuppressWarnings("unchecked")
//	public static String getParameterStr(HttpServletRequest request){
//		StringBuffer paramStr = new StringBuffer("");
//		Enumeration<String> parameterNames = request.getParameterNames();
//		while (parameterNames.hasMoreElements()) {
//			String paramName = parameterNames.nextElement();
//			String[] values = request.getParameterValues(paramName);
//			if(values!=null){
//				for(int i = 0; i < values.length; i++){
//					paramStr.append("&");
//					paramStr.append(paramName);
//					paramStr.append("=");
//					paramStr.append(EncodeUtils.urlEncode(values[i].trim()));
//				}
//			}
//		}
//		return paramStr.substring(1).toString();
//	}

	/**
	 * 将查询串转为ibatis sqlMap要求的格式特别适用于ibatis中有in条件查询
	 * @param request
	 * @return map
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Map getIbatisMap(HttpServletRequest request){
		Map<String,String> map = WebUtils.getNormalMap(request);
		Set<Map.Entry<String, String>> entrySet = (Set<Map.Entry<String, String>>)map.entrySet();
		HashMap<String,Object> paramMap = new HashMap<String,Object>();
		if(entrySet != null) {
			//解析每一个参数值，如果某个参数值带有,号，则认为该参数为数组参数， 将其转化为数组
			for(Map.Entry<String, String> entry : entrySet) {
				String key = entry.getKey().replaceAll("params.", "");
				String value = entry.getValue();

				//	System.out.println(key+":"+value);
				if(value == null || value.indexOf(",")<0) {
					paramMap.put(key, value);
				}
				if(StringUtils.isNotEmpty(value) && value.indexOf(",")>=0) {
					String[] strArr = value.split(",");
					Pattern pattern = Pattern.compile("[0-9]*");
					Matcher isNum = null;
					List<Object> list = new LinkedList<Object>();
					for(String str : strArr) {
						if(str == null || "".equals(str.trim()) ) {
							continue;
						}
						isNum = pattern.matcher(str);
						if(isNum.matches()) {
							list.add(Long.parseLong(str));
						} else {
							list.add(str.trim());
						}
					}
					paramMap.put(key, list);
				}
			}
		}
		return paramMap;		
	}

	/**
	 * weblogic集群获取客户端IP
	 * @param request
	 * @return
	 */
	public static String getRemoteAddr(HttpServletRequest request){
		String ip=request.getHeader("X-Forwarded-For");
		if(ip == null || ip.length() == 0) {
			ip=request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0) {
			ip=request.getRemoteAddr();
		}
		return ip;
	}		

	/**
	 * 取得HttpSession的简化函数.
	 */
	public static HttpSession getSession() {
		//return ServletActionContext.getRequest().getSession();
		return getRequest().getSession();
	}

	/**
	 * 取得HttpRequest的简化函数.
	 */
	public static HttpServletRequest getRequest() {
		//return ServletActionContext.getRequest();
		return ContextHolderUtils.getRequest();
	}
//
//	/**
//	 * 取得HttpResponse的简化函数.
//	 */
//	public static HttpServletResponse getResponse() {
//		return ServletActionContext.getResponse();
//	}
//
//	/**
//	 * 取得Request Parameter的简化方法.
//	 */
//	public static String getParameter(String name) {
//		return getRequest().getParameter(name);
//	}

	public static String NVL(Object obj){
		if(obj==null){
			return "";
		}else{
			return obj.toString();
		}
	}
}
