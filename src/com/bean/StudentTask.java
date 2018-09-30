package com.bean;

import java.util.Date;

import com.utils.base.BaseEntity;

public class StudentTask extends BaseEntity {

	private int studentTaskId;//学生个人作业表编号
	private int taskId;//作业id
	private String studentNum;//学号
	private String submitStatus;//提交状态（已交，未交）
	private String examineStatus;//审批状态（已审批，未审批）
	private Date submitTime;//提交时间
	private double grade;//总成绩
	private int count;//提交次数

	private int taskDetailId;//作业详情编号
	private String submitAnswer;//提交的答案
	private String wordUrl;//实验报告文档本地地址
	private String testUrl;//工程压缩包地址
	private double score;//该题得分
	
	private int examineId;//批改记录表编号
	private String teacherNum;//教师职工号
	private Date examineTime;//批改时间
	public int getStudentTaskId() {
		return studentTaskId;
	}
	public void setStudentTaskId(int studentTaskId) {
		this.studentTaskId = studentTaskId;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getStudentNum() {
		return studentNum;
	}
	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}
	public String getSubmitStatus() {
		return submitStatus;
	}
	public void setSubmitStatus(String submitStatus) {
		this.submitStatus = submitStatus;
	}
	public String getExamineStatus() {
		return examineStatus;
	}
	public void setExamineStatus(String examineStatus) {
		this.examineStatus = examineStatus;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
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
	public int getTaskDetailId() {
		return taskDetailId;
	}
	public void setTaskDetailId(int taskDetailId) {
		this.taskDetailId = taskDetailId;
	}
	public String getSubmitAnswer() {
		return submitAnswer;
	}
	public void setSubmitAnswer(String submitAnswer) {
		this.submitAnswer = submitAnswer;
	}
	public String getWordUrl() {
		return wordUrl;
	}
	public void setWordUrl(String wordUrl) {
		this.wordUrl = wordUrl;
	}
	public String getTestUrl() {
		return testUrl;
	}
	public void setTestUrl(String testUrl) {
		this.testUrl = testUrl;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getExamineId() {
		return examineId;
	}
	public void setExamineId(int examineId) {
		this.examineId = examineId;
	}
	public String getTeacherNum() {
		return teacherNum;
	}
	public void setTeacherNum(String teacherNum) {
		this.teacherNum = teacherNum;
	}
	public Date getExamineTime() {
		return examineTime;
	}
	public void setExamineTime(Date examineTime) {
		this.examineTime = examineTime;
	}

	

}

