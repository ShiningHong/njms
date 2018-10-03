package com.bean;

import com.utils.base.BaseEntity;

public class Teacher extends BaseEntity{

	private String teachernum;//职工号
	private String teachername;//教师姓名
	private String position;//教师职称
	private String sex;//性别
	public String getTeachernum() {
		return teachernum;
	}
	public void setTeachernum(String teachernum) {
		this.teachernum = teachernum;
	}
	public String getTeachername() {
		return teachername;
	}
	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
}
