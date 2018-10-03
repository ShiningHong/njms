package com.bean;

import com.utils.base.BaseEntity;

public class Class extends BaseEntity {

	private int classid;//班级id
	private String classname;//班级名称
	private int collegeid;//学院id
	public int getClassid() {
		return classid;
	}
	public void setClassid(int classid) {
		this.classid = classid;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public int getCollegeid() {
		return collegeid;
	}
	public void setCollegeid(int collegeid) {
		this.collegeid = collegeid;
	}
	
	
	

}
