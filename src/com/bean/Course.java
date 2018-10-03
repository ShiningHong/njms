package com.bean;

import com.utils.base.BaseEntity;

public class Course extends BaseEntity {

	private int courseid;//课程id
	private String coursename;//课程名称
	private String introduction;//课程简介
	private String teachernum;//职工号
	private int selectid;//选课id
	private int classid;//班级id
	private int recordid;//选课记录表编号
	private String studentnum;//学号
	public int getCourseid() {
		return courseid;
	}
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getTeachernum() {
		return teachernum;
	}
	public void setTeachernum(String teachernum) {
		this.teachernum = teachernum;
	}
	public int getSelectid() {
		return selectid;
	}
	public void setSelectid(int selectid) {
		this.selectid = selectid;
	}
	public int getClassid() {
		return classid;
	}
	public void setClassid(int classid) {
		this.classid = classid;
	}
	public int getRecordid() {
		return recordid;
	}
	public void setRecordid(int recordid) {
		this.recordid = recordid;
	}
	public String getStudentnum() {
		return studentnum;
	}
	public void setStudentnum(String studentnum) {
		this.studentnum = studentnum;
	}
	
	
}
