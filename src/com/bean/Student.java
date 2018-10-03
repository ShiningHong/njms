package com.bean;

import com.utils.base.BaseEntity;

public class Student extends BaseEntity {

	private String studentnum;//学号
	private String studentname;//姓名
	private int classid;//班级id
	private String sex;//性别（男/女）
	private String assistant;//是否为助教（是；否）
	

	private String teacherNum;//职工号
	private String checkTask;//是否可以批改作业（是；否）
	private String assignTask;//是否可以布置作业（是；否）
	private String addTopic;//是否可以添加题目（是；否）
	
	public String getStudentNum() {
		return studentNum;
	}

	private String teachernum;//职工号
	private String checktask;//是否可以批改作业（是；否）
	private String assigntask;//是否可以布置作业（是；否）
	private String addtopic;//是否可以添加题目（是；否）

	
	public String getStudentnum() {
		return studentnum;
	}
	public void setStudentnum(String studentnum) {
		this.studentnum = studentnum;
	}
	public String getStudentname() {
		return studentname;
	}
	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}
	public int getClassid() {
		return classid;
	}
	public void setClassid(int classid) {
		this.classid = classid;
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
	public String getTeachernum() {
		return teachernum;
	}
	public void setTeachernum(String teachernum) {
		this.teachernum = teachernum;
	}
	public String getChecktask() {
		return checktask;
	}
	public void setChecktask(String checktask) {
		this.checktask = checktask;
	}
	public String getAssigntask() {
		return assigntask;
	}
	public void setAssigntask(String assigntask) {
		this.assigntask = assigntask;
	}
	public String getAddtopic() {
		return addtopic;
	}
	public void setAddtopic(String addtopic) {
		this.addtopic = addtopic;
	}

	
}
