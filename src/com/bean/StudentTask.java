package com.bean;

import java.util.Date;

import com.utils.base.BaseEntity;

public class StudentTask extends BaseEntity {

	private int studenttaskid;//学生个人作业表编号
	private int taskid;//作业id
	private String studentnum;//学号
	private String submitstatus;//提交状态（已交，未交）
	private String examinestatus;//审批状态（已审批，未审批）
	private Date submittime;//提交时间
	private double grade;//总成绩
	private int count;//提交次数

	private int taskdetailid;//作业详情编号
	private String submitanswer;//提交的答案
	private String wordurl;//实验报告文档本地地址
	private String testurl;//工程压缩包地址
	private double score;//该题得分
	
	private int examineid;//批改记录表编号
	private String teachernum;//教师职工号
	private Date examinetime;//批改时间
	public int getStudenttaskid() {
		return studenttaskid;
	}
	public void setStudenttaskid(int studenttaskid) {
		this.studenttaskid = studenttaskid;
	}
	public int getTaskid() {
		return taskid;
	}
	public void setTaskid(int taskid) {
		this.taskid = taskid;
	}
	public String getStudentnum() {
		return studentnum;
	}
	public void setStudentnum(String studentnum) {
		this.studentnum = studentnum;
	}
	public String getSubmitstatus() {
		return submitstatus;
	}
	public void setSubmitstatus(String submitstatus) {
		this.submitstatus = submitstatus;
	}
	public String getExaminestatus() {
		return examinestatus;
	}
	public void setExaminestatus(String examinestatus) {
		this.examinestatus = examinestatus;
	}
	public Date getSubmittime() {
		return submittime;
	}
	public void setSubmittime(Date submittime) {
		this.submittime = submittime;
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTaskdetailid() {
		return taskdetailid;
	}
	public void setTaskdetailid(int taskdetailid) {
		this.taskdetailid = taskdetailid;
	}
	public String getSubmitanswer() {
		return submitanswer;
	}
	public void setSubmitanswer(String submitanswer) {
		this.submitanswer = submitanswer;
	}
	public String getWordurl() {
		return wordurl;
	}
	public void setWordurl(String wordurl) {
		this.wordurl = wordurl;
	}
	public String getTesturl() {
		return testurl;
	}
	public void setTesturl(String testurl) {
		this.testurl = testurl;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getExamineid() {
		return examineid;
	}
	public void setExamineid(int examineid) {
		this.examineid = examineid;
	}
	public String getTeachernum() {
		return teachernum;
	}
	public void setTeachernum(String teachernum) {
		this.teachernum = teachernum;
	}
	public Date getExaminetime() {
		return examinetime;
	}
	public void setExaminetime(Date examinetime) {
		this.examinetime = examinetime;
	}
	
	
	
	
}

