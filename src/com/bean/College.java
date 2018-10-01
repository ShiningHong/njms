package com.bean;

import com.utils.base.BaseEntity;

public class College extends BaseEntity {

	private int collegeId;//学院id
	private String collegeName;//学院名称
	public int getCollegeId() {
		return collegeId;
	}
	public void setCollegeId(int collegeId) {
		this.collegeId = collegeId;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	
	
}
