/**
 * FlexiGridUtils.java V1.0 Feb 20, 2012 4:35:46 PM
 * 
 * Copyright 2011 AsiaInfo Linkage, All rights reserved.
 * 
 * Modification history(By Time Reason):
 * 
 * Description:
 */

package com.utils.flexigrid;

import java.beans.PropertyDescriptor;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;


import com.utils.base.BaseEntity;
import com.utils.general.AIStringUtil;
import com.utils.general.DateUtils;
import com.utils.sql.SqlAttr;

/**
 * 功能描述：
 * 
 * @author 池文杉
 * 
 * <p>
 * 修改历史：(修改人，修改时间，修改原因/内容)
 * </p>
 */
public class FlexiGridUtils {
	
	public static final String SYS_REGION_ID = "SYS_REGION_ID";
//	public static final String SYS_REGION_CODE = "SYS_REGION_CODE";
	public static final String SYS_REGION_BICODE = "SYS_REGION_BICODE";
    
    /**
     * 
     * 功能描述：将DataContainer[] 专为flexiGrid所需要的json串
     *
     * @author  池文杉
     * <p>创建日期 ：Mar 14, 2012 12:45:25 PM</p>
     *
     * @param total
     * @param page
     * @param datas
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     
    public static String generateFlexiGridJson(int total, int page, DataContainer[] datas) {

        return generateFlexiGridJson(total, page, datas, null);
    }
    */
	
    /**
     * 
     * 功能描述：将DataContainer[] 专为flexiGrid所需要的json串；其中列需要参数转换的，需要提供列名和参数名称的映射关系
     *
     *
     * @param total
     * @param page
     * @param datas
     * @param colDictMap 列名和参数名称的映射关系（当值用分隔符分隔，逐个转义：<列名,"dictName{分隔符}">  如 <"COUNTY_IDS","SYS_REGION_ID{,}">）
     * @return String
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     
    public static String generateFlexiGridJson(int total, int page, DataContainer[] datas,
        Map<String, String> colDictMap) {
    	
        JSONObject object = JSONObject.fromObject(generateFlexiGridResult(total, page, datas, colDictMap));
        return object.toString();
    }*/
    
    /**
     * 
     * 功能描述：将DataContainer[] 转为flexiGrid所需要结果对象FlexiGridResult；其中列需要参数转换的，需要提供列名和参数名称的映射关系
     * @param total
     * @param page
     * @param datas
     * @param colDictMap 列名和参数名称的映射关系（当值用分隔符分隔，逐个转义：<列名,"dictName{分隔符}">  如 <"COUNTY_IDS","SYS_REGION_ID{,}">）
     * @return FlexiGridResult
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     
    public static FlexiGridResult generateFlexiGridResult(int total, int page, DataContainer[] datas,
            Map<String, String> colDictMap){
    	
    	 FlexiGridResult result = new FlexiGridResult();
         result.setTotal(total);
         result.setPage(page);
         result.setRows(generateFlexiGridRow(datas,colDictMap));
         return result;
    }*/
    
    /**
     * 
     * 功能描述：
     * 
     * @author 池文杉
     *         <p>
     *         创建日期 ：Feb 20, 2012 6:53:16 PM
     *         </p>
     * 
     * @param datas
     * @param colDictMap  列与参数的映射关系
     * @return
     * 
     * <p>
     * 修改历史 ：(修改人，修改时间，修改原因/内容)
     * </p>
     
    public static List<FlexiGridRow> generateFlexiGridRow(DataContainer[] datas,Map<String, String> colDictMap) {

        List<FlexiGridRow> result = new ArrayList<FlexiGridRow>();
        
        if (datas == null || datas.length == 0) {
            result.clear();
        } else {
            for (int i = 0; i < datas.length; i++) {
                DataContainer data = datas[i];
                FlexiGridRow row = new FlexiGridRow();
                // 设置值
                row.setCell(buildRowValue(data,colDictMap));
                // 设置主键
                row.setId(buildKey(data, i));
                result.add(row);
            }
        }
        return result;
    }*/
    
    /**
     * 
     * 功能描述：值转换；
     * 
     * @author 池文杉
     *         <p>
     *         创建日期 ：Feb 20, 2012 7:20:47 PM
     *         </p>
     * 
     * @param data
     * @param colDictMap 列与参数名称的映射关系
     * @return
     * 
     * <p>
     * 修改历史 ：(修改人，修改时间，修改原因/内容)
     * </p>
     
    private static Map<String, String> buildRowValue(DataContainer data,Map<String, String> colDictMap) {

        Map<String, String> map = new HashMap<String, String>();
        if (data == null) {
            map.clear();
        } else {
            for (String property : data.getPropertyNames()) {
                String value = convertValue(data.get(property));
                map.put(property, value);
                
                //对于如果有设置映射关系的处理；
                if(colDictMap == null || colDictMap.size()==0 || !colDictMap.containsKey(property)){
                    //如果没有设定映射关系，那么不需要处理
                } else {
                    String dictName = colDictMap.get(property);
                    
                    if(StringUtils.isEmpty(dictName)){
                        //该列没有设定映射关系，退出；
                    } else {                    	
                    	
                    	//2015-04-27 add by Anna 增加区域ID/区域编码转义
                    	//值用分隔符分隔，逐个转义
                    	String fg = "";
                    	if(dictName.indexOf("{")>0&&dictName.indexOf("}")>0&&dictName.endsWith("}")){
                    		fg = dictName.substring(dictName.indexOf("{")+1, dictName.indexOf("}"));
                    		if(!AIStringUtil.isEmpty(fg)){
                    			dictName = dictName.substring(0, dictName.indexOf("{"));
                    		}
                    	}
                    	if(!AIStringUtil.isEmpty(fg)){//
                    		String[] vas = value.split(fg);
                    		String itemValue = "";
                    		for(String v:vas){
                    			itemValue += ",";
                            	if(AIStringUtil.isEquals(dictName, SYS_REGION_ID)){//区域ID
                            		itemValue += RegionCache.getRegionName(v);
                            	}else if(AIStringUtil.isEquals(dictName, SYS_REGION_BICODE)){//区域编码
                            		itemValue += RegionCache.getRegionNameByBiCode(v);
                            	}else{//取字典
                            		itemValue += DictCache.getDictItemName(dictName, v);
                            	}
                    		}
                    		itemValue = itemValue.substring(1);
                    		map.put(property, itemValue);
                    		map.put(property+"_ECODE", value);
                    		
                    	}else{
                    		
                        	String itemValue = "";
                        	if(AIStringUtil.isEquals(dictName, SYS_REGION_ID)){//区域ID
                        		itemValue = RegionCache.getRegionName(value);
                        	}else if(AIStringUtil.isEquals(dictName, SYS_REGION_BICODE)){//区域编码
                        		itemValue = RegionCache.getRegionNameByBiCode(value);
                        	}else{//取字典
                        		itemValue = DictCache.getDictItemName(dictName, value);
                        	}
                        	map.put(property, itemValue);
                            map.put(property+"_ECODE", value);
                    	}
                    	
                        
                    }
                }
                
            }
        }
        return map;
    }
    */
    /**
     * 
     * 功能描述：设置主键，如果没有主键，那么取行数作为主键；否则，取主键值，多个主键值，之间用“-”分割；
     * 
     * @author 池文杉
     *         <p>
     *         创建日期 ：Feb 20, 2012 7:17:55 PM
     *         </p>
     * 
     * @param data
     * @param index
     * @return
     * 
     * <p>
     * 修改历史 ：(修改人，修改时间，修改原因/内容)
     * </p>
    
    private static String buildKey(DataContainer data, int index) {

        String[] keys = data.getKeyPropertyNames();
        if (keys == null || keys.length == 0) {
            return index + "";
        } else {
            StringBuffer buf = new StringBuffer();
            for (int jindex = 0; jindex < keys.length; jindex++) {
                if (jindex > 0) {
                    buf.append("-");
                }
                buf.append(convertValue(data.get(keys[jindex])));
            }
            return buf.toString();
        }
    } */
    
    /**
     * 
     * 功能描述：转换格式 日期默认格式转换为：yyyy-MM-dd HH:mm:ss
     * 
     * @author 池文杉
     *         <p>
     *         创建日期 ：Feb 20, 2012 7:10:38 PM
     *         </p>
     * 
     * @param value
     * @return
     * 
     * <p>
     * 修改历史 ：(修改人，修改时间，修改原因/内容)
     * </p>
     */
    private static String convertValue(Object value) {

        if (value == null) {
            return "";
        } else if (value instanceof Integer || value instanceof Long || value instanceof Double
                || value instanceof Float) {
            return value + "";
        } else if (value instanceof java.sql.Timestamp || value instanceof java.sql.Date||value instanceof java.util.Date) {
            //return DataType.transferToString(value, DataType.DATATYPE_DATETIME);
        	return DateUtils.formatDate(value, "yyyy-MM-dd HH:mm:ss");
        } else {
            return value.toString();
        }
    }
    

    /**
     * 
     * 功能描述：将List<Map<String, Object>> 转为flexiGrid所需要的json串；其中列需要参数转换的，需要提供列名和参数名称的映射关系
     * 			和DBUtil.getQueryMap搭配使用
     * 
     * @author chenyn3 2014-08-26
     * @param total 
     * @param page
     * @param datas
     * @param colDictMap 列名和参数名称的映射关系
     * @return
     * <p>
     * 修改历史 ：(修改人，修改时间，修改原因/内容)
     * </p>
     */
    public static String generateFlexiGridJson(int total, int page, List<Map<String, Object>> datas,
            Map<String, String> colDictMap) {

            FlexiGridResult result = new FlexiGridResult();
            result.setTotal(total);
            result.setPage(page);
            result.setRows(generateFlexiGridRow(datas,colDictMap));
            JSONObject object = JSONObject.fromObject(result);
            return object.toString();
    }
    
    /**
     * 
     * 功能描述：将List<Map<String, Object>> 转为List<FlexiGridRow>；其中列需要参数转换的，需要提供列名和参数名称的映射关系
     * @author chenyn3 2014-08-26
     * @param datas
     * @param colDictMap
     * @return
     * <p>
     * 修改历史 ：(修改人，修改时间，修改原因/内容)
     * </p>
     */
    private static List<FlexiGridRow> generateFlexiGridRow(List<Map<String, Object>> datas,Map<String, String> colDictMap) {

        List<FlexiGridRow> result = new ArrayList<FlexiGridRow>();
        
        if (datas == null || datas.size() == 0) {
            result.clear();
        } else {
            for (int i = 0; i < datas.size(); i++) {
            	Map<String,Object> data = datas.get(i);
                FlexiGridRow row = new FlexiGridRow();
                // 设置值
                row.setCell(buildRowValue(data,colDictMap));
                // 没有主键，取行数作为主键
                row.setId(i+"");
                result.add(row);
            }
        }
        return result;
    }
    
    /**
     * 
     * 功能描述：值转换；
     * @author chenyn3 2014-08-26
     * @param data
     * @param colDictMap
     * @return
     * <p>
     * 修改历史 ：(修改人，修改时间，修改原因/内容)
     * </p>
     */
    private static Map<String, String> buildRowValue(Map<String,Object> data,Map<String, String> colDictMap) {

        Map<String, String> map = new HashMap<String, String>();
        if (data == null) {
            map.clear();
        } else {
            for (String property : data.keySet()) {
            	String property_upper = property.toUpperCase();//返回的map中的key用大写，以便和DataContainer相适应
                String value = convertValue(data.get(property));
                map.put(property_upper, value);
                
                //对于如果有设置映射关系的处理；
                if(colDictMap == null || colDictMap.size()==0 || !colDictMap.containsKey(property_upper)){
                    //如果没有设定映射关系，那么不需要处理
                } else {
                    String dictName = colDictMap.get(property_upper);
                    if(StringUtils.isEmpty(dictName)){
                        //该列没有设定映射关系，退出；
                    }
                }
                
            }
        }
        return map;
    }
    
    /**
     * 
     * 功能描述：单表自定义查询sql 封装FlexiGridJson
     * @author chenyn3
     * @createDate 2014-11-18
     * @param row_index_start 分页参数searchModel.buildPageFirst()
     * @param row_index_end 分页参数searchModel.buildPageLast()
     * @param page 分页参数searchModel.getPage()
     * @param tableName 表名
     * @param selectField 查询列
     * @param condition 查询条件语句 " and ... "
     * @param colDictMap 列名和参数名称的映射关系
     * @return
     * <p>
     * 修改历史 ：(修改人，修改时间，修改原因/内容)
     * </p>
     */
    /*
    public static String generateFlexiGridJson(int row_index_start,int row_index_end,int page,String tableName,String selectField,String condition,Map<String, String> colDictMap){
    	try{
			if(StringUtils.isNotEmpty(tableName)){
				StringBuffer sql = new StringBuffer();//bo sql
				StringBuffer sqlCount = new StringBuffer();//count sql
				StringBuffer sqlList  = new StringBuffer();//分页 sql
				StringBuffer where = new StringBuffer(" where 1=1 ");//查询条件 
				where.append(condition);
//				sql.append("select * from "+tableName);
				sql.append("select ");
				if(!StringUtils.isNotEmpty(selectField)){
					selectField = "*";
				}
				sql.append(selectField+" from "+tableName);
				sqlCount.append("select count(*) from "+tableName);				
				
				sql.append(where.toString()+" \n");
				sqlCount.append(where.toString()+" \n");
				
				sqlList.append("select * from ( \n");
				sqlList.append("select B.*, rownum as row_index from (");
				sqlList.append(sql.toString());
				sqlList.append(") B \n");
				sqlList.append("where rownum <= "+row_index_end+" \n");
				sqlList.append(") where row_index >= "+row_index_start+" and row_index <= "+row_index_end+" \n");
				
				int totalCnt = Integer.valueOf( DBUtil.queryForString(ServiceManager.getSession().getConnection(), sqlCount.toString(),new String[]{}));
				
				if(totalCnt>0){
					List<Map<String, Object>> list= DBUtil.getQueryMap(ServiceManager.getSession().getConnection(), sqlList.toString(), new String[]{});
					return generateFlexiGridJson(totalCnt, page,list,colDictMap);
				}
			}
		}catch(Exception e){
			
		}
		return generateFlexiGridJson(0, page,new ArrayList<Map<String, Object>>(),null);
    }
    */    
    public static <T> FlexiGridResult generateFlexiGridResultByEntity(int total, int page, List<T> datas,
            Map<String, String> colDictMap,boolean useColName){
    	
    	 FlexiGridResult result = new FlexiGridResult();
         result.setTotal(total);
         result.setPage(page);
         result.setRows(generateFlexiGridRowByEntity(datas,colDictMap,useColName));
         return result;
    }
    
    public static <T> List<FlexiGridRow> generateFlexiGridRowByEntity(List<T> datas,Map<String, String> colDictMap,boolean useColName) {

        List<FlexiGridRow> result = new ArrayList<FlexiGridRow>();
        
        if (datas == null || datas.size() == 0) {
            result.clear();
        } else {
            for (int i = 0; i < datas.size(); i++) {
            	T data = datas.get(i);
                FlexiGridRow row = new FlexiGridRow();
                // 设置值
                row.setCell(buildRowValueByEntity(data,colDictMap,useColName));
                // 设置主键
                row.setId(buildKey(data, i,useColName));
                result.add(row);
            }
        }
        return result;
    }
    
    private static <T> Map<String, String> buildRowValueByEntity(T data,Map<String, String> colDictMap,boolean useColName) {

        Map<String, String> map = new HashMap<String, String>();
        if (data == null) {
            map.clear();
        } else {
        	/*
            for (String property : data.getPropertyNames()) {
        		String name = objDescriptors[i].getName();
                String value = convertValue(data.get(property));
                map.put(property, value);
                
                //对于如果有设置映射关系的处理；
                if(colDictMap == null || colDictMap.size()==0 || !colDictMap.containsKey(property)){
                    //如果没有设定映射关系，那么不需要处理
                } else {
                    String dictName = colDictMap.get(property);
                    
                    if(StringUtils.isEmpty(dictName)){
                        //该列没有设定映射关系，退出；
                    } else {                    	
                    	
                    	//2015-04-27 add by Anna 增加区域ID/区域编码转义
                    	//值用分隔符分隔，逐个转义
                    	String fg = "";
                    	if(dictName.indexOf("{")>0&&dictName.indexOf("}")>0&&dictName.endsWith("}")){
                    		fg = dictName.substring(dictName.indexOf("{")+1, dictName.indexOf("}"));
                    		if(!AIStringUtil.isEmpty(fg)){
                    			dictName = dictName.substring(0, dictName.indexOf("{"));
                    		}
                    	}
                    	if(!AIStringUtil.isEmpty(fg)){//
                    		String[] vas = value.split(fg);
                    		String itemValue = "";
                    		for(String v:vas){
                    			itemValue += ",";
                            	if(AIStringUtil.isEquals(dictName, SYS_REGION_ID)){//区域ID
                            		itemValue += RegionCache.getRegionName(v);
                            	}else if(AIStringUtil.isEquals(dictName, SYS_REGION_BICODE)){//区域编码
                            		itemValue += RegionCache.getRegionNameByBiCode(v);
                            	}else{//取字典
                            		itemValue += DictCache.getDictItemName(dictName, v);
                            	}
                    		}
                    		itemValue = itemValue.substring(1);
                    		map.put(property, itemValue);
                    		map.put(property+"_ECODE", value);
                    		
                    	}else{
                    		
                        	String itemValue = "";
                        	if(AIStringUtil.isEquals(dictName, SYS_REGION_ID)){//区域ID
                        		itemValue = RegionCache.getRegionName(value);
                        	}else if(AIStringUtil.isEquals(dictName, SYS_REGION_BICODE)){//区域编码
                        		itemValue = RegionCache.getRegionNameByBiCode(value);
                        	}else{//取字典
                        		itemValue = DictCache.getDictItemName(dictName, value);
                        	}
                        	map.put(property, itemValue);
                            map.put(property+"_ECODE", value);
                    	}
                    	
                        
                    }
                }
                
            }*/
        	
        	PropertyDescriptor[] objDescriptors = PropertyUtils.getPropertyDescriptors(data);
        	for (int i = 0; i < objDescriptors.length; i++) {
        		String name = objDescriptors[i].getName();
    			if ("class".equals(name)||"serialVersionUID".equals(name)) {
    				continue;
    			}else{
    				if (PropertyUtils.isReadable(data, name) && PropertyUtils.isWriteable(data, name)) {
    					try {
    						Object value = PropertyUtils.getSimpleProperty(data,name);
    						String valueStr = convertValue(value);
    						String key = name;
    						if(useColName){//使用字段名为KEY
    							key = AIStringUtil.underscoreNameUpper(key);//转换为下划线
    						}
    						map.put(key, valueStr);
    						
    						//对于如果有设置映射关系的处理；
    		                if(colDictMap == null || colDictMap.size()==0 || !colDictMap.containsKey(key)){
    		                    //如果没有设定映射关系，那么不需要处理
    		                } else {
    		                    String dictName = colDictMap.get(key);
    		                    
    		                    if(StringUtils.isEmpty(dictName)){
    		                        //该列没有设定映射关系，退出；
    		                    } else {                    	
    		                    }
    		                }
    						
    						
    					} catch (IllegalAccessException e) {
    						// TODO Auto-generated catch block
    					    e.printStackTrace();
    						return map;
    					} catch (InvocationTargetException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    						return map;
    					} catch (NoSuchMethodException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    						return map;
    					}
    				}
    			}
        		
        	}
        }
        return map;
    }
    
    private static <T> String buildKey(T data,int index,boolean useColName) {
    	StringBuffer buf = new StringBuffer();
    	Field[] fields = data.getClass().getDeclaredFields();
    	for (Field field : fields) {
    		if (field.getAnnotation(SqlAttr.class) != null) {
    			boolean isIdArg = field.getAnnotation(SqlAttr.class).idArg();
        		if(isIdArg){//主键字段
        			String name = field.getName();
					try {
						Object value = PropertyUtils.getSimpleProperty(data,name);
						String valueStr = convertValue(value);
	    				if(buf.length()>0){
	    					 buf.append("-");
	    				}
	    				buf.append(valueStr);
	    				
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				
        		}
        	}
    	}
    	
    	if(buf.length()<1){
    		buf.append(index + "");
    	}
    	
    	return buf.toString();
    }
    
}
