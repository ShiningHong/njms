package com.bean;

import com.utils.base.BaseEntity;

public class Student extends BaseEntity {

	private String studentNum;//学号
	private String studentName;//姓名
	private int classId;//班级id
	private String sex;//性别（男/女）
	private String assistant;//是否为助教（是；否）
	
	private String teacherNum;//职工号
	private String checkTask;//是否可以批改作业（是；否）
	private String assignTask;//是否可以布置作业（是；否）
	private String addTopic;//是否可以添加题目（是；否）
	
	public String getStudentNum() {
		return studentNum;
	}
	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAssistant() {
		return assistant;
	}
	public void setAssistant(String assistant) {
		this.assistant = assistant;
	}
	public String getTeacherNum() {
		return teacherNum;
	}
	public void setTeacherNum(String teacherNum) {
		this.teacherNum = teacherNum;
	}
	public String getCheckTask() {
		return checkTask;
	}
	public void setCheckTask(String checkTask) {
		this.checkTask = checkTask;
	}
	public String getAssignTask() {
		return assignTask;
	}
	public void setAssignTask(String assignTask) {
		this.assignTask = assignTask;
	}
	public String getAddTopic() {
		return addTopic;
	}
	public void setAddTopic(String addTopic) {
		this.addTopic = addTopic;
	}
	
	
}
