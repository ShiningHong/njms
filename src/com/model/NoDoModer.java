package com.model;

import com.utils.sql.SqlAttr;

public class NoDoModer extends com.utils.base.BaseSearchModel  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SqlAttr(column="STUDENTNUM",dataType="String")
	private java.lang.String studentNum;//   流水号
	@SqlAttr(column="STUDENTNAME",dataType="String")
	private java.lang.String studentName;//   流水号
	@SqlAttr(column="CLASSID",dataType="Long")
    private java.lang.String classId;//   null
	@SqlAttr(column="SUBMITSTATUS",dataType="String")
    private java.lang.String submitstatus;//   null
	public java.lang.String getStudentNum() {
		return studentNum;
	}
	public void setStudentNum(java.lang.String studentNum) {
		this.studentNum = studentNum;
	}
	public java.lang.String getClassId() {
		return classId;
	}
	public void setClassId(java.lang.String classId) {
		this.classId = classId;
	}
	public java.lang.String getStudentName() {
		return studentName;
	}
	public void setStudentName(java.lang.String studentName) {
		this.studentName = studentName;
	}
	public java.lang.String getSubmitstatus() {
		return submitstatus;
	}
	public void setSubmitstatus(java.lang.String submitstatus) {
		this.submitstatus = submitstatus;
	}
	
}
