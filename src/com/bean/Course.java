package com.bean;

import com.utils.base.BaseEntity;

public class Course extends BaseEntity {

	private int courseId;//课程id
	private String courseName;//课程名称
	private String introduction;//课程简介
	private String teacherNum;//职工号
	private int selectId;//选课id
	private int classId;//班级id
	private int recordId;//选课记录表编号
	private String studentNum;//学号
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getTeacherNum() {
		return teacherNum;
	}
	public void setTeacherNum(String teacherNum) {
		this.teacherNum = teacherNum;
	}
	public int getSelectId() {
		return selectId;
	}
	public void setSelectId(int selectId) {
		this.selectId = selectId;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public int getRecordId() {
		return recordId;
	}
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	public String getStudentNum() {
		return studentNum;
	}
	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}
	
	
}
