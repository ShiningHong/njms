package com.utils.sql;

import java.lang.reflect.Field;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

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
	
//	SqlPropertyUtil(String poNa,Object dataModel,Map<String,String> operConfigs){
//		
//	}
	
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
	
	private static void hqlProSingle(StringBuffer hql,Map<String, Object> para,List<String> paramsList,String poNa,String column,
			Object value,/*Map<String,SqlOperType> operCols*/SqlOperType operType,String dataType){
		if(operType!=null){
			if(SqlOperType.filter.equals(operType)){//过滤掉的属性
				//continue;
				return;
				
			}else if(SqlOperType.isNull.equals(operType)){
				hql.append(" and "+poNa+column+" is null ");
				
			}else if(SqlOperType.isNotNull.equals(operType)){
				hql.append(" and "+poNa+column+" is not null ");
				
			}else{
				if(value!=null){
					
					String vstr = null;
					int type = 1;//1字符  2数字 3日期
					String formatter = "yyyy-MM-dd hh24:mi:ss";
					if(AIStringUtil.isEqualsIgnoreCase(dataType, "String")){
						vstr = String.valueOf(value);
						type = 1;
					}else if(AIStringUtil.isEqualsIgnoreCase(dataType, "Long","Integer","Float","Double")){
						vstr = String.valueOf(value);
						type = 2;
					}else if(AIStringUtil.isEqualsIgnoreCase(dataType, "DateTime")){
						if(value instanceof Date||value instanceof Timestamp){//时间类型
							vstr = DateUtils.formatDate(value, "yyyy-MM-dd HH:mm:ss");
						}else{
							vstr = String.valueOf(value);
						}
						type = 3;
					}
					
					//String vstr = String.valueOf(value);
					boolean addPara = true;
					hql.append(" and "+poNa+column);
					
					if(SqlOperType.eq.equals(operType)){
						hql.append(" = ");
					}else if(SqlOperType.notEq.equals(operType)){
						hql.append(" != ");
					}else if(SqlOperType.like.equals(operType)){
						hql.append(" like ");
						vstr = "%"+vstr+"%";
					}else if(SqlOperType.noLike.equals(operType)){
						hql.append(" not like ");
						vstr = "%"+vstr+"%";
					}else if(SqlOperType.lt.equals(operType)){
						hql.append(" > ");
					}else if(SqlOperType.gt.equals(operType)){
						hql.append(" < ");
					}else if(SqlOperType.lEt.equals(operType)){
						hql.append(" >= ");
					}else if(SqlOperType.gEt.equals(operType)){
						hql.append(" <= ");
					}else if(SqlOperType.in.equals(operType)){
						addPara =  false;
						hql.append(" in ( ");
						if(type==1){
							vstr = AIStringUtil.addDyh(vstr, ",");
						}
						hql.append(vstr+" ) ");
					}else if(SqlOperType.notIn.equals(operType)){
						addPara =  false;
						hql.append(" not in ( ");
						if(type==1){
							vstr = AIStringUtil.addDyh(vstr, ",");
						}
						hql.append(" ) ");
					}
					if(addPara){
						if(type==3){//时间运算
							hql.append(" to_date( :"+column+" ,'"+formatter+"') \n");
						}else{
							hql.append(" :"+column+" \n");
						}
						
						//para.put(column, value);
						para.put(column, vstr);
						paramsList.add(vstr);
					}
					
				}
			}
			
		}else if(value!=null){//无特殊运算配置 默认用 =
			hql.append(" and "+poNa+column + " = " + " :"+column+" \n"); 
			para.put(column, value);
			String vstr = String.valueOf(value);
			paramsList.add(vstr);
		}
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
//			if(operCols!=null){
//				for(String key:operCols.keySet()){
//					SqlOperType oper = operCols.get(key);
//					hqlProSingle(hql, para, paramsList, poNa, column, value, operType,dataType);
//				}
//			}
			return ;
		}
		if(operCols==null)operCols= new HashMap<String, SqlOperType>();		
		
		/*
		if(dataModel instanceof DataContainer){//DataContainer类型
			DataContainer dataPro = (DataContainer)dataModel;
			String[] propertys = dataPro.getPropertyNames();
			if(propertys==null||propertys.length<1){
				return ;
			}
			for(String properName:propertys){
				if(dataPro.get(properName)==null){
					continue;
				}
				String dataType = dataPro.getPropertyType(properName);
				Object value = dataPro.get(properName);
				SqlOperType operType = operCols!=null?operCols.get(properName):null;//逻辑运算
				hqlProSingle(hql, para, paramsList, poNa,properName, value, operType,dataType);
				/*
				if(AIStringUtil.isEqualsIgnoreCase(type, "String")){
					String vaule = String.valueOf(dataPro.get(proper));
					if(AIStringUtil.isNotEmpty(vaule)){
						 
					}
					
				}
				* /				
			}
		}else{//model类型
			Field[] fields = dataModel.getClass().getDeclaredFields();//取装载对象的属性
			for (Field field : fields) {
				if (field.getAnnotation(SqlAttr.class) != null) {
					String name = field.getName();//属性名
					String column = field.getAnnotation(SqlAttr.class).column();
					Object value = PropertyUtils.getSimpleProperty(dataModel,name);//属性值
					SqlOperType operType = operCols!=null?operCols.get(name):null;//逻辑运算
					if(operType==null){
						operType = field.getAnnotation(SqlAttr.class).operType();
					}
					String dataType = field.getAnnotation(SqlAttr.class).dataType();//数据类型
					hqlProSingle(hql, para, paramsList, poNa,column, value, operType,dataType);
					
				}
			}
		}
		*/
    }
    
    
 /*
	public static Object[] hqlParamProperty(String poNa,DataContainer pro,Map<String,String> operCol){
		StringBuffer hql = new StringBuffer("1=1");
		Map<String, Object> para = new LinkedHashMap<String, Object>();
		List<String> paramsList = new ArrayList<String>();
		
		if(pro==null){
			//return null;
			return new Object[]{hql.toString(),para,new String[]{},paramsList};
		}
		if(AIStringUtil.isEmpty(poNa)){
			poNa = "";
		}else{
			poNa += ".";
		}
		if(operCol==null)operCol= new HashMap<String, String>();
		
		String[] propertys = pro.getPropertyNames();
		if(propertys==null||propertys.length<1){
			return new Object[]{hql.toString(),para,new String[]{},paramsList};
		}
		for(String proper:propertys){
			if(pro.get(proper)==null){
				continue;
			}
			String type = pro.getPropertyType(proper);
			if(AIStringUtil.isEqualsIgnoreCase(type, "String")){
				String vaule = String.valueOf(pro.get(proper));
				if(AIStringUtil.isNotEmpty(vaule)){
					
				}
				
			}
		}
		
		return new Object[]{hql.toString(),para,new String[]{},paramsList};
		
	}
*/

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
