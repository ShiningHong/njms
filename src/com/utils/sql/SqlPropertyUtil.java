package com.utils.sql;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import com.utils.general.AIStringUtil;
import com.utils.general.DateUtils;

public class SqlPropertyUtil {
	
	
	private StringBuffer condition =  new StringBuffer();
	private Map<String, Object> parameter = new LinkedHashMap<String, Object>();
	private List<String> paramsList = new ArrayList<String>();
	
	private String poNa;
	//private DataContainer dataModel;
	private Object dataModel;//condition
	private Map<String,SqlOperType> operConfigs;

	
	public void buildHqlParam() throws Exception{
		//hqlProperty(sql, para, paramsList);
		
		hqlProperty(condition,parameter,paramsList,poNa,dataModel,operConfigs);
	}
	
	public void buildHqlParam(String poNa,Object dataModel,Map<String,SqlOperType> operConfigs) throws Exception{
		this.poNa = poNa;
		this.dataModel = dataModel;
		this.operConfigs = operConfigs;
		//hqlProperty(sql, para, paramsList);
		buildHqlParam();
	}
	

	
    public static void hqlProperty(StringBuffer hql,Map<String, Object> para,List<String> paramsList,String poNa,Object dataModel,Map<String,SqlOperType> operCols) throws Exception{
    	if(hql==null) hql= new StringBuffer();
		if(para==null) para = new LinkedHashMap<String, Object>();
		if(paramsList==null) paramsList = new ArrayList<String>();
		if(AIStringUtil.isEmpty(poNa)){
			poNa = "";
		}else{
			poNa += ".";
		}
		
		if(dataModel==null){
			return ;
		}
		if(operCols==null)operCols= new HashMap<String, SqlOperType>();		
    }
    
 

	public StringBuffer getCondition() {
		return condition;
	}

	public void setCondition(StringBuffer condition) {
		this.condition = condition;
	}

	public Map<String, Object> getParameter() {
		return parameter;
	}

	public void setParameter(Map<String, Object> parameter) {
		this.parameter = parameter;
	}

	public List<String> getParamsList() {
		return paramsList;
	}

	public void setParamsList(List<String> paramsList) {
		this.paramsList = paramsList;
	}

	public String getPoNa() {
		return poNa;
	}

	public void setPoNa(String poNa) {
		this.poNa = poNa;
	}

	public Object getDataModel() {
		return dataModel;
	}

	public void setDataModel(Object dataModel) {
		this.dataModel = dataModel;
	}

	public Map<String, SqlOperType> getOperConfigs() {
		return operConfigs;
	}

	public void setOperConfigs(Map<String, SqlOperType> operConfigs) {
		this.operConfigs = operConfigs;
	}
	
	
	
}
