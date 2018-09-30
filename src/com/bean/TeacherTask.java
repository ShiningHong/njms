package com.bean;

import java.util.Date;

import com.utils.base.BaseEntity;

public class TeacherTask extends BaseEntity {

	private int taskId;//作业id
	private int taskJurisdictionId;//权限id
	private int courseId;// 课程id
	private String creator;//创建者（职工号）
	private Date createTime; //创建时间
	private String title;//作业标题
	private String standard;//评分机制（百分制；自定义）
	private Date publishTime; //发布日期
	private Date startTime;//作业开始日期
	private Date endTime;//作业截止日期
	private String taskType;//作业类型（课堂作业、课后作业、小测作业、实验）
	private int passScore;//及格分数
	private int totalScore;//总分
	private String createStatus;//创建状态（创建完成，未创建完成）
	
	private int publishId;//发布记录id
	private int selectId;//选课id
	private int submitNum;//已提交人数
	private int examineNum;//已批改人数
	
	private int taskDetailId;//作业详情编号
	private String topicType;//题目类型（课后作业、课堂、小测、实验）
	private String topicContent;//题干
	private int options;//选项数目
	private String optionA;//选项A（判断题-正确）
	private String optionB;//选项B（判断题-错误）
	private String optionC;//选项C
	private String optionD;//选项D
	private String optionE;//选项E
	private String optionF;//选项F
	private String answer;//答案（除填空题答案存放在选项里面）
	private String detailAnswer;//答案解析
	private String complexity;//题目难易度（难中易）
	private double score;//该题分数--作业对应

	
	private String lookAnswer;//是否可以查看答案（是，否）
	private String lookGrade;//是否允许查看分数（是，否）
	private String topicRound;//是否题目乱序（是，否）
	
	private int topicId;//题号

	
	public int getTopicId() {
		return topicId;
	}
	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}
	
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getTaskJurisdictionId() {
		return taskJurisdictionId;
	}
	public void setTaskJurisdictionId(int taskJurisdictionId) {
		this.taskJurisdictionId = taskJurisdictionId;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public int getPassScore() {
		return passScore;
	}
	public void setPassScore(int passScore) {
		this.passScore = passScore;
	}
	public int getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	public String getCreateStatus() {
		return createStatus;
	}
	public void setCreateStatus(String createStatus) {
		this.createStatus = createStatus;
	}
	public int getPublishId() {
		return publishId;
	}
	public void setPublishId(int publishId) {
		this.publishId = publishId;
	}
	public int getSelectId() {
		return selectId;
	}
	public void setSelectId(int selectId) {
		this.selectId = selectId;
	}
	public int getSubmitNum() {
		return submitNum;
	}
	public void setSubmitNum(int submitNum) {
		this.submitNum = submitNum;
	}
	public int getExamineNum() {
		return examineNum;
	}
	public void setExamineNum(int examineNum) {
		this.examineNum = examineNum;
	}
	public int getTaskDetailId() {
		return taskDetailId;
	}
	public void setTaskDetailId(int taskDetailId) {
		this.taskDetailId = taskDetailId;
	}
	public String getTopicType() {
		return topicType;
	}
	public void setTopicType(String topicType) {
		this.topicType = topicType;
	}
	public String getTopicContent() {
		return topicContent;
	}
	public void setTopicContent(String topicContent) {
		this.topicContent = topicContent;
	}
	public int getOptions() {
		return options;
	}
	public void setOptions(int options) {
		this.options = options;
	}
	public String getOptionA() {
		return optionA;
	}
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	public String getOptionB() {
		return optionB;
	}
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	public String getOptionC() {
		return optionC;
	}
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}
	public String getOptionD() {
		return optionD;
	}
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}
	public String getOptionE() {
		return optionE;
	}
	public void setOptionE(String optionE) {
		this.optionE = optionE;
	}
	public String getOptionF() {
		return optionF;
	}
	public void setOptionF(String optionF) {
		this.optionF = optionF;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getDetailAnswer() {
		return detailAnswer;
	}
	public void setDetailAnswer(String detailAnswer) {
		this.detailAnswer = detailAnswer;
	}
	public String getComplexity() {
		return complexity;
	}
	public void setComplexity(String complexity) {
		this.complexity = complexity;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getLookAnswer() {
		return lookAnswer;
	}
	public void setLookAnswer(String lookAnswer) {
		this.lookAnswer = lookAnswer;
	}
	public String getLookGrade() {
		return lookGrade;
	}
	public void setLookGrade(String lookGrade) {
		this.lookGrade = lookGrade;
	}
	public String getTopicRound() {
		return topicRound;
	}
	public void setTopicRound(String topicRound) {
		this.topicRound = topicRound;
	}


	

}
