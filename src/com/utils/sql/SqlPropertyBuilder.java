package com.utils.sql;

import java.lang.reflect.Field;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.utils.general.AIStringUtil;
import com.utils.general.ArrayUtil;
import com.utils.general.DateUtils;

public class SqlPropertyBuilder {

	protected StringBuffer condition =  new StringBuffer();
	protected Map<String, Object> parameter = new LinkedHashMap<String, Object>();
	protected List<String> paramsList = new ArrayList<String>();
	
	protected String poNa;//表别名 如po

	protected Map<String,SqlPropertyModel> operConfigs;//属性操作配置MAP：DataContainer容器时，key=字段名；Model容器时，key=容器属性名
	protected Object dataModel;//查询容器（可传 DataContainer或自定义Model）
	protected boolean isDataContainer = false;//容器是否DataContainer类型
	
//	protected DataContainer dataContainer;
//	protected Map<String,SqlOperType> operTypes;
	
	/**
	 * 初始化
	 * @param poNa 表别名
	 * @param dataModel 查询条件容器
	 * @param operConfigs 属性操作配置MAP（DataContainer容器时，key=字段名；Model容器时，key=容器属性名）
	 * @param isDataContainer 容器是否DataContainer类型
	 * @throws Exception
	 */
	public SqlPropertyBuilder(String poNa,Object dataModel,Map<String,SqlPropertyModel> operConfigs,boolean isDataContainer) throws Exception{
		this.poNa = poNa;
		this.dataModel = dataModel;
		this.operConfigs = operConfigs;
		this.isDataContainer = isDataContainer;
		buildHqlParam();
	}
	
	public void buildHqlParam() throws Exception{		
		hqlProperty(condition,parameter,paramsList,poNa,dataModel,operConfigs,isDataContainer);
	}
	
	public void buildHqlParam(String poNa,Object dataModel,Map<String,SqlPropertyModel> operConfigs,boolean isDataContainer) throws Exception{
		this.poNa = poNa;
		this.dataModel = dataModel;
		this.operConfigs = operConfigs;
		this.isDataContainer = isDataContainer;
		buildHqlParam();
	}
	
	protected static void hqlProSingle(StringBuffer hql,Map<String, Object> para,List<String> paramsList,String poNa,String column,
			Object value,/*Map<String,SqlOperType> operCols*/SqlOperType operType,String dataType,String filterValue){
		
		String formatter = "yyyy-MM-dd hh24:mi:ss";
		
		if(operType!=null){
			if(SqlOperType.filter.equals(operType)||SqlOperType.orderBy.equals(operType)||SqlOperType.orderByDesc.equals(operType)){//过滤掉的属性
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
					
					if(AIStringUtil.isEmpty(vstr)){
						return ;
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
			
			
			String vstr = null;
			int type = 1;//1字符  2数字 3日期
			
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
			
			if(AIStringUtil.isEmpty(vstr)){
				return ;
			}
			
			if(AIStringUtil.isEquals(vstr, filterValue)){//过滤值
				return ;
			}
			
			hql.append(" and "+poNa+column + " = ");
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
	
	/*
	//根据DataContainer容器生成
	protected static void hqlPropertyByDataContainer(StringBuffer hql,Map<String, Object> para,List<String> paramsList,String poNa,
			DataContainer dataModel,Map<String,SqlPropertyModel> operCols) throws Exception{
//		String orderBy = "";
		
		if(dataModel==null){//容器为空
//			if(operCols!=null){//根据属性配置生成
//				
//				for(String key:operCols.keySet()){
//					SqlPropertyModel oper = operCols.get(key);
//					String propertyName = (oper!=null&&AIStringUtil.isNotEmpty(oper.getPropertyName()))?oper.getPropertyName():key;
//					SqlOperType operType = oper!=null?oper.getOperType():null;
//					if(SqlOperType.orderBy.equals(operType)){
//						orderBy += ","+propertyName+" asc ";
//						continue;
//					}else if(SqlOperType.orderByDesc.equals(operType)){
//						orderBy += ","+propertyName+" desc ";
//						continue;
//					}else{
//						hqlProSingle(hql, para, paramsList, poNa, key, null, operType,null,null);
//					}
//				}
//			}
			
		}else{
			
			DataContainer dataPro = new DataContainer(dataModel.getObjectType());
			dataPro.copy(dataModel);
			String[] propertys = dataPro.getPropertyNames();//取bean的属性
			if(propertys==null||propertys.length<1){
				return ;
			}
			for(String propertyName:propertys){
				
				String dataType = dataPro.getPropertyType(propertyName);//数据类型
				Object value = dataPro.get(propertyName);//属性值
				SqlPropertyModel oper =  (operCols!=null)?operCols.get(propertyName):null;
				propertyName = (oper!=null&&AIStringUtil.isNotEmpty(oper.getPropertyName()))?oper.getPropertyName():propertyName;//重写属性名
				String filterValue = (oper!=null&&AIStringUtil.isNotEmpty(oper.getFilterValue()))?oper.getFilterValue():null;//过滤值
				SqlOperType operType = (oper!=null)?oper.getOperType():null;//逻辑运算
				
				/*
				if(SqlOperType.orderBy.equals(operType)){
					orderBy += ","+propertyName+" asc ";
					continue;
				}else if(SqlOperType.orderByDesc.equals(operType)){
					orderBy += ","+propertyName+" desc ";
					continue;
				}else{
					hqlProSingle(hql, para, paramsList, poNa,propertyName, value, operType,dataType,filterValue);
				}
				* /
				if(!SqlOperType.isNull.equals(operType)&&!SqlOperType.isNotNull.equals(operType)){
					hqlProSingle(hql, para, paramsList, poNa,propertyName, value, operType,dataType,filterValue);
				}
			}			
		}
		
//		if(AIStringUtil.isNotEmpty(orderBy)){//有排序
//			hql.append(" order by "+orderBy.substring(1));//去掉第一个,
//		}
		
	}
	*/
	
	/*
    protected static void hqlPropertyBySearchModel(StringBuffer hql,Map<String, Object> para,List<String> paramsList,String poNa,
    		Object dataModel,Map<String,SqlPropertyModel> operCols) throws Exception{
    	
//    	String orderBy = "";
		
		if(dataModel==null){//容器为空
//			if(operCols!=null){//根据属性配置生成
//				
//				for(String key:operCols.keySet()){
//					SqlPropertyModel oper = operCols.get(key);
//					String propertyName = (oper!=null&&AIStringUtil.isNotEmpty(oper.getPropertyName()))?oper.getPropertyName():key;
//					SqlOperType operType = oper!=null?oper.getOperType():null;
//					if(SqlOperType.orderBy.equals(operType)){
//						orderBy += ","+propertyName+" asc ";
//						continue;
//					}else if(SqlOperType.orderByDesc.equals(operType)){
//						orderBy += ","+propertyName+" desc ";
//						continue;
//					}else{
//						hqlProSingle(hql, para, paramsList, poNa, key, null, operType,null,null);
//					}
//				}
//			}
		}else{
			
			Field[] fields = dataModel.getClass().getDeclaredFields();//取装载对象的属性
			for (Field field : fields) {
				if (field.getAnnotation(SqlAttr.class) != null) {//属性添加了SqlAttr注解8
					String name = field.getName();//model属性名
					Object value = PropertyUtils.getSimpleProperty(dataModel,name);//属性值
					SqlPropertyModel proper =  (operCols!=null)?operCols.get(name):null;
					String column = (proper!=null&&AIStringUtil.isNotEmpty(proper.getPropertyName()))?proper.getPropertyName():null;//重写的数据字段名
					if(AIStringUtil.isEmpty(column)){
						column = field.getAnnotation(SqlAttr.class).column();//取model配置的数据字段名
					}
					if(AIStringUtil.isEmpty(column)) column = name;//属性未指定对应的字段名，则将属性名作为字段名
					SqlOperType operType = (proper!=null)?proper.getOperType():null;//逻辑运算
					if(operType==null){
						operType = field.getAnnotation(SqlAttr.class).operType();//取model配置的逻辑运算
					}
					String dataType = field.getAnnotation(SqlAttr.class).dataType();//数据类型
					String filterValue = (proper!=null&&AIStringUtil.isNotEmpty(proper.getFilterValue()))?proper.getFilterValue():null;//过滤值
					
					/*
					if(SqlOperType.orderBy.equals(operType)){
						orderBy += ","+propertyName+" asc ";
						continue;
					}else if(SqlOperType.orderByDesc.equals(operType)){
						orderBy += ","+propertyName+" desc ";
						continue;
					}else{
						hqlProSingle(hql, para, paramsList, poNa,column, value, operType,dataType,filterValue);
					}* /
					
					if(!SqlOperType.isNull.equals(operType)&&!SqlOperType.isNotNull.equals(operType)){
						hqlProSingle(hql, para, paramsList, poNa, column, value, operType,dataType,filterValue);
					}
					
				}
			}
		}
		
//		if(AIStringUtil.isNotEmpty(orderBy)){//有排序
//			hql.append(" order by "+orderBy.substring(1));//去掉第一个,
//		}
		
    }
    */
	
    public static void hqlProperty(StringBuffer condition,Map<String, Object> para,List<String> paramsList,String poNa,
    		Object dataModel,Map<String,SqlPropertyModel> operCols,boolean isDataContainer) throws Exception{
    	
    	StringBuffer hql = new StringBuffer();
    	
    	if(condition==null) condition = new StringBuffer();
		if(para==null) para = new LinkedHashMap<String, Object>();
		if(paramsList==null) paramsList = new ArrayList<String>();
		if(AIStringUtil.isEmpty(poNa)){
			poNa = "";
		}else{
			poNa += ".";
		}
		
		if(operCols==null)operCols= new HashMap<String, SqlPropertyModel>();		
		
		/*
		//按容器生成sql
		if(isDataContainer||dataModel instanceof DataContainer){//DataContainer类型
			hqlPropertyByDataContainer(hql, para, paramsList, poNa, (DataContainer)dataModel, operCols);
			
		}else{//model类型
			hqlPropertyBySearchModel(hql, para, paramsList, poNa, dataModel, operCols);
		}
		*/
		String orderBy = "";
//		if(operCols!=null){//根据属性配置生成
			for(String key:operCols.keySet()){
				SqlPropertyModel oper = operCols.get(key);
				String propertyName = (oper!=null&&AIStringUtil.isNotEmpty(oper.getPropertyName()))?oper.getPropertyName():key;
				SqlOperType operType = oper!=null?oper.getOperType():null;
				if(SqlOperType.orderBy.equals(operType)){
					orderBy += ","+propertyName+" asc ";
					continue;
				}else if(SqlOperType.orderByDesc.equals(operType)){
					orderBy += ","+propertyName+" desc ";
					continue;
				}else if(SqlOperType.isNull.equals(operType)||SqlOperType.isNotNull.equals(operType)){
					hqlProSingle(hql, para, paramsList, poNa, key, null, operType,null,null);
				}
			}
//		}
		
		if(AIStringUtil.isNotEmpty(orderBy)){//有排序
			hql.append(" order by "+orderBy.substring(1));//去掉第一个,
		}
		
		int andIndex = hql.toString().toLowerCase().indexOf("and");
		if(andIndex>-1){//
			condition.append(hql.substring(andIndex+3));
		}else{
			condition.append(hql.toString());
		}

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
	
	public String[] getParamsArray(){
		if(paramsList!=null&&paramsList.size()>0){
			 return ArrayUtil.convertToArr(paramsList);
		}
		return new String[0];
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

	public Map<String, SqlPropertyModel> getOperConfigs() {
		return operConfigs;
	}

	public void setOperConfigs(Map<String, SqlPropertyModel> operConfigs) {
		this.operConfigs = operConfigs;
	}
	
	
}
