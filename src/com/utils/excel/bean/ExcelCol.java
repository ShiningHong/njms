package com.utils.excel.bean;

/**
 * excel对应的列
 * @author 陈嘉瑛
 * @version 2015-12-30
 *
 */
public class ExcelCol {
	//列值
	private Object val;
	//列id
	private Long id;
	//列父id
	private Long pid;
	//字段名
	private String field;
	//表头对齐方式
	private String hAlign;
	//格式化
	private String pattern;
	private String align;
	/** 是否启用列合并（若同行相邻单元格都启用列合并，则这些列从左往右，从第一个单元格开始连续合并右边值为空的单元格） */
	private boolean colspan;
	/** 是否启用行合并（同列相邻单元格都启用行合并且内容相同时自动合并） */
	private boolean rowspan;
	
	public Object getVal() {
		return val;
	}
	public void setVal(Object val) {
		this.val = val;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String gethAlign() {
		return hAlign;
	}
	public void sethAlign(String hAlign) {
		this.hAlign = hAlign;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public boolean isColspan() {
		return colspan;
	}
	public void setColspan(boolean colspan) {
		this.colspan = colspan;
	}
	public boolean isRowspan() {
		return rowspan;
	}
	public void setRowspan(boolean rowspan) {
		this.rowspan = rowspan;
	}
	
	
}
