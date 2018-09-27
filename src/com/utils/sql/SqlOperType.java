package com.utils.sql;

import com.utils.general.AIStringUtil;

/**
 * 
 * @Title: sql属性处理方式 、逻辑运算方式
 * @Description:
 *
 * <p>
 * 修改历史 ：(修改人，修改时间，修改原因/内容)
 * </p>
 */
public enum SqlOperType {
	
	filter("filter","","过滤属性"),
	eq("eq","=", "等值判断"),
	notEq("notEq","!=", "不等值判断"),
	like("like","like", "like判断"),
	noLike("noLike","not like", "like取反"),
	in("in","in", "in区间"),
	notIn("notIn","not in", "not in区间"),
	lt("lt","<", "小于"),
	gt("gt",">", "大于"),
	lEt("lEt","<=", "小于等于"),
	gEt("gEt",">=", "大于等于"),
	isNull("isNull","is null", "空值判断"),
	isNotNull("isNotNull","is not null", "非空判断"),
	orderBy("orderBy","order by","排序"),
	orderByDesc("orderByDesc","order by","降序排序");
	
	SqlOperType(String key,String operator,String desc){
		this.key = key;
		this.operator = operator;
		this.desc = desc;
	}
	
	private String key;//
	private String operator;
	private String desc;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	//判断是否此操作类型
	public boolean equals(SqlOperType oper){
		if(oper==null){
			return false;
		}
//		for(SqlOperType obj :SqlOperType.values()){
//			if(AIStringUtil.isEquals(obj.getKey(), oper.getKey())){
//				return true;
//			}
//		}
		if(AIStringUtil.isEquals(this.key, oper.getKey())){
			return true;
		}
		return false;
	}
	
	
}
