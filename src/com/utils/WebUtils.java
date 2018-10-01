/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: WebUtils.java,v 1.1 2017/08/09 06:34:01 chenyn3 Exp $
 */
package com.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


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


	public static String getBasePath(HttpServletRequest request){   
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


	public static String NVL(Object obj){
		if(obj==null){
			return "";
		}else{
			return obj.toString();
		}
	}
}
