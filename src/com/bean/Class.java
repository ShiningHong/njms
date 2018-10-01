package com.bean;

import com.utils.base.BaseEntity;

public class Class extends BaseEntity {

	private int classId;//班级id
	private String className;//班级名称
	private int collegeId;//学院id
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public int getCollegeId() {
		return collegeId;
	}
	public void setCollegeId(int collegeId) {
		this.collegeId = collegeId;
	}
	
	

}
