package com.bean;

import java.util.Date;

import com.utils.base.BaseEntity;

public class TeacherTask extends BaseEntity {

	private int taskid;//作业id
	private int taskjurisdictionid;//权限id
	private int courseid;// 课程id
	private String creator;//创建者（职工号）
	private Date createtime; //创建时间
	private String title;//作业标题
	private String standard;//评分机制（百分制；自定义）
	private Date publishtime; //发布日期
	private Date starttime;//作业开始日期
	private Date endtime;//作业截止日期
	private String tasktype;//作业类型（课堂作业、课后作业、小测作业、实验）
	private int passscore;//及格分数
	private int totalscore;//总分
	private String createstatus;//创建状态（创建完成，未创建完成）
	
	private int publishid;//发布记录id
	private int selectid;//选课id
	private int submitnum;//已提交人数
	private int examinenum;//已批改人数
	
	private int taskdetailid;//作业详情编号
	private String topictype;//题目类型（课后作业、课堂、小测、实验）
	private String topiccontent;//题干
	private int options;//选项数目
	private String optiona;//选项A（判断题-正确）
	private String optionb;//选项B（判断题-错误）
	private String optionc;//选项C
	private String optiond;//选项D
	private String optione;//选项E
	private String optionf;//选项F
	private String answer;//答案（除填空题答案存放在选项里面）
	private String detailanswer;//答案解析
	private String complexity;//题目难易度（难中易）
	private double score;//该题分数--作业对应

	
	private String lookanswer;//是否可以查看答案（是，否）
	private String lookgrade;//是否允许查看分数（是，否）
	private String topicround;//是否题目乱序（是，否）
	
	private int topicid;//题号

	public int getTaskid() {
		return taskid;
	}

	public void setTaskid(int taskid) {
		this.taskid = taskid;
	}

	public int getTaskjurisdictionid() {
		return taskjurisdictionid;
	}

	public void setTaskjurisdictionid(int taskjurisdictionid) {
		this.taskjurisdictionid = taskjurisdictionid;
	}

	public int getCourseid() {
		return courseid;
	}

	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
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

	public Date getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getTasktype() {
		return tasktype;
	}

	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}

	public int getPassscore() {
		return passscore;
	}

	public void setPassscore(int passscore) {
		this.passscore = passscore;
	}

	public int getTotalscore() {
		return totalscore;
	}

	public void setTotalscore(int totalscore) {
		this.totalscore = totalscore;
	}

	public String getCreatestatus() {
		return createstatus;
	}

	public void setCreatestatus(String createstatus) {
		this.createstatus = createstatus;
	}

	public int getPublishid() {
		return publishid;
	}

	public void setPublishid(int publishid) {
		this.publishid = publishid;
	}

	public int getSelectid() {
		return selectid;
	}

	public void setSelectid(int selectid) {
		this.selectid = selectid;
	}

	public int getSubmitnum() {
		return submitnum;
	}

	public void setSubmitnum(int submitnum) {
		this.submitnum = submitnum;
	}

	public int getExaminenum() {
		return examinenum;
	}

	public void setExaminenum(int examinenum) {
		this.examinenum = examinenum;
	}

	public int getTaskdetailid() {
		return taskdetailid;
	}

	public void setTaskdetailid(int taskdetailid) {
		this.taskdetailid = taskdetailid;
	}

	public String getTopictype() {
		return topictype;
	}

	public void setTopictype(String topictype) {
		this.topictype = topictype;
	}

	public String getTopiccontent() {
		return topiccontent;
	}

	public void setTopiccontent(String topiccontent) {
		this.topiccontent = topiccontent;
	}

	public int getOptions() {
		return options;
	}

	public void setOptions(int options) {
		this.options = options;
	}

	public String getOptiona() {
		return optiona;
	}

	public void setOptiona(String optiona) {
		this.optiona = optiona;
	}

	public String getOptionb() {
		return optionb;
	}

	public void setOptionb(String optionb) {
		this.optionb = optionb;
	}

	public String getOptionc() {
		return optionc;
	}

	public void setOptionc(String optionc) {
		this.optionc = optionc;
	}

	public String getOptiond() {
		return optiond;
	}

	public void setOptiond(String optiond) {
		this.optiond = optiond;
	}

	public String getOptione() {
		return optione;
	}

	public void setOptione(String optione) {
		this.optione = optione;
	}

	public String getOptionf() {
		return optionf;
	}

	public void setOptionf(String optionf) {
		this.optionf = optionf;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getDetailanswer() {
		return detailanswer;
	}

	public void setDetailanswer(String detailanswer) {
		this.detailanswer = detailanswer;
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

	public String getLookanswer() {
		return lookanswer;
	}

	public void setLookanswer(String lookanswer) {
		this.lookanswer = lookanswer;
	}

	public String getLookgrade() {
		return lookgrade;
	}

	public void setLookgrade(String lookgrade) {
		this.lookgrade = lookgrade;
	}

	public String getTopicround() {
		return topicround;
	}

	public void setTopicround(String topicround) {
		this.topicround = topicround;
	}

	public int getTopicid() {
		return topicid;
	}

	public void setTopicid(int topicid) {
		this.topicid = topicid;
	}

	
	

	

}
