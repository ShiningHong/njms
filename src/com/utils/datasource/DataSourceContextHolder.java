package com.utils.datasource;
public class DataSourceContextHolder {

	private static final ThreadLocal contextHolder=new ThreadLocal();
	
//	public static void setDataSourceType(String dataSourceType){
//		contextHolder.set(dataSourceType);
//	}
	
	public static void setDataSourceType(DataSourceType dataSourceType){
		contextHolder.set(dataSourceType);
	}
	
//	public static String getDataSourceType(){
//		return (String) contextHolder.get();
//	}
	
	public static DataSourceType getDataSourceType(){
		return (DataSourceType) contextHolder.get();
	}
	
	public static void clearDataSourceType(){
		contextHolder.remove();
	}
	
}
