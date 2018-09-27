/**
 * BaseSearchModel.java	  V1.0   Feb 20, 2012 11:26:26 AM
 *
 * Copyright 2011 AsiaInfo Linkage, All rights reserved.
 *
 * Modification history(By    Time    Reason):
 * 
 * Description:
 */

package com.utils.base;





/**
 * 功能描述：所有查询模型的基类；针对flexigrid.js设置的查询模型；
 *
 * @author  池文杉
 *
 * <p>修改历史：(修改人，修改时间，修改原因/内容)</p>
 */
public class BaseSearchModel extends BaseModel{
    
    /**
     * 当前页
     */
    private int page;
    /**
     * 返回记录数，即：页的记录数
     */
    private int rp;
    
    private int total;//总数
    /**
     * 排序字段
     */
    private String sortname;
    /**
     * 排序顺序 默认值为 升序
     */
    private String sortorder = "asc";
    /**
     * 使用 flexigrid的search功能
     */
    private String query;
    /**
     * 使用 flexigrid的search功能
     */
    private String qtype;
        
    
    
//    /**
//     * FlexiGrid的操作类型
//     */
//    private String flexiGridType;
//    
//    /**
//     * flexiGrid的列模型
//     */
//    private String flexiGirdColModel;
    
    /**
     * @return the page
     */
    public int getPage() {
    
        return page;
    }
    
    /**
     * @param page the page to set
     */
    public void setPage(int page) {
    
        this.page = page;
    }
    
    /**
     * @return the rp
     */
    public int getRp() {
    
        return rp;
    }
    
    /**
     * @param rp the rp to set
     */
    public void setRp(int rp) {
    
        this.rp = rp;
    }
    
    /**
     * @return the sortname
     */
    public String getSortname() {
    
        return sortname;
    }
    
    /**
     * @param sortname the sortname to set
     */
    public void setSortname(String sortname) {
    
        this.sortname = sortname;
    }
    
    /**
     * @return the sortorder
     */
    public String getSortorder() {
    
        return sortorder;
    }
    
    /**
     * @param sortorder the sortorder to set
     */
    public void setSortorder(String sortorder) {
    
        this.sortorder = sortorder;
    }
    
    /**
     * @return the query
     */
    public String getQuery() {
    
        return query;
    }
    
    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
    
        this.query = query;
    }
    
    /**
     * @return the qtype
     */
    public String getQtype() {
    
        return qtype;
    }
    
    /**
     * @param qtype the qtype to set
     */
    public void setQtype(String qtype) {
    
        this.qtype = qtype;
    }
    
    /**
     * 
     * 功能描述：第一个分页的第一个数据
     *
     * @author  余根宁
     * <p>创建日期 ：Feb 21, 2012 9:09:14 PM</p>
     *
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public int buildPageFirst(){
        
        return (this.getPage() -1) * this.getRp() +1;
    }
    
    public int getPageFirst(){
    	return buildPageFirst();
    }
    
    public int buildPageLast(){
        return this.getPage()*this.getRp();
    }
    
    public int getPageLast(){
    	return buildPageLast();
    }

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

//	public String getFlexiGridType() {
//		return flexiGridType;
//	}
//
//	public void setFlexiGridType(String flexiGridType) {
//		this.flexiGridType = flexiGridType;
//	}
//
//	public String getFlexiGirdColModel() {
//		return flexiGirdColModel;
//	}
//
//	public void setFlexiGirdColModel(String flexiGirdColModel) {
//		this.flexiGirdColModel = flexiGirdColModel;
//	}
//    
//	public boolean isFlexiGridExcelDown(){
//		return AIStringUtil.isEquals(flexiGridType, "FlexiGridExcelDown");
//	}
    
    
}
