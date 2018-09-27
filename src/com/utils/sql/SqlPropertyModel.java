package com.utils.sql;

/**
 * 
 * @Title:配置属性对象
 * @Description:
 *
 */
public class SqlPropertyModel {

	private String propertyName;//重写字段名 
	
//	private String fieldName;//自定义MODEL为查询容器时用
	
	private SqlOperType operType;//属性sql处理方式 、逻辑运算方式
	
	private String filterValue;//属性过滤值，如DataContainer 的long型（默认=0）
	
	public SqlPropertyModel(SqlOperType operType){
		this.operType  = operType;
	}
	
	public SqlPropertyModel(String propertyName,SqlOperType operType,String filterValue){
		this.operType  = operType;
		this.propertyName  = propertyName;
		this.filterValue  = filterValue;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

//	public String getFieldName() {
//		return fieldName;
//	}
//
//	public void setFieldName(String fieldName) {
//		this.fieldName = fieldName;
//	}

	public SqlOperType getOperType() {
		return operType;
	}

	public void setOperType(SqlOperType operType) {
		this.operType = operType;
	}

	public String getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

	
	
	
}
