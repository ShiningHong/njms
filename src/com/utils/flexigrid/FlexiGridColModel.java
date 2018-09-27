/**
 * FlexGridColModel.java	  V1.0   Feb 27, 2012 4:23:00 PM
 *
 * Copyright 2011 AsiaInfo Linkage, All rights reserved.
 *
 * Modification history(By    Time    Reason):
 * 
 * Description:
 */

package com.utils.flexigrid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 功能描述：flexGrid的列模型
 *
 * @author  池文杉
 *
 * <p>修改历史：(修改人，修改时间，修改原因/内容)</p>
 */
public class FlexiGridColModel implements Serializable {
    
    /**
     * 日志信息；
     */
    public static Logger logger = LoggerFactory.getLogger(FlexiGridColModel.class);
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1900699510512352066L;

    /**
     * 显示名称
     */
    private String display;
     
    /**
     * 列名
     */
    private String name;

    
    /**
     * @return the display
     */
    public String getDisplay() {
    
        return display;
    }

    
    /**
     * @param display the display to set
     */
    public void setDisplay(String display) {
    
        this.display = display;
    }

    
    /**
     * @return the name
     */
    public String getName() {
    
        return name;
    }

    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
    
        this.name = name;
    }
    
    
    /**
     * 
     * 功能描述：前台请求的colModel JSON 数组 转换为FlexGridColMode的LIST；
     *
     * @author  池文杉
     * <p>创建日期 ：Feb 27, 2012 4:25:00 PM</p>
     *
     * @param colModelJson
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public static List<FlexiGridColModel> parseColModelList(String colModelJson){
        List<FlexiGridColModel> modelLst = new ArrayList<FlexiGridColModel>();
        modelLst.clear();
        
        //如果需要转换的Json格式为空，那么返回长度为0 的list ;
        if(StringUtils.isEmpty(colModelJson.trim())){
            return modelLst;
        }
        JSONArray array = JSONArray.fromObject(colModelJson);
        
        for(int i=0;i<array.size();i++){
            JSONObject object = JSONObject.fromObject(array.get(i));
            FlexiGridColModel model = (FlexiGridColModel)JSONObject.toBean(object, FlexiGridColModel.class);
            modelLst.add(model);
        }
        
        /*String[] modelJsons = colModelJson.trim().split(",");
        for(String modelJson : modelJsons){
            modelJson = modelJson.trim();
            try{
                FlexGridColModel model = parseColModel(modelJson);
                modelLst.add(model);
            }catch(IllegalArgumentException err){
                logger.error("转换列模型异常；参数非法；异常描述："+err.getMessage());
                continue;
            }catch(Exception err){
                logger.error("转换列模型异常；其它异常；异常描述："+err.getMessage());
                continue;
            }
        }*/
        return modelLst;
    }
    
    /**
     * 
     * 功能描述：前台请求的列模型，转换为FlexGridColModel，前台列模型的JSON格式：
     * 
     *
     * @author  池文杉
     * <p>创建日期 ：Feb 27, 2012 4:41:06 PM</p>
     *
     * @param colModelJson
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public static FlexiGridColModel parseColModel(String colModelJson) throws Exception{
        //如果为空，抛出异常
        if(StringUtils.isEmpty(colModelJson.trim())){
            throw new IllegalArgumentException("传入的列模型为空，请检查");
        }
        
        
        JSONObject object = JSONObject.fromObject(colModelJson);
        FlexiGridColModel model = (FlexiGridColModel)JSONObject.toBean(object, FlexiGridColModel.class);
        /*String[] colInfos = colModelJson.trim().split("&");
        
        for(String colInfo:colInfos){
            String[] cols = colInfo.split("=");
            if(cols.length<2){
                continue;
            }
            if("display".equalsIgnoreCase(cols[0].toLowerCase())){
                model.setDisplay(cols[1]);
            } else if("name".equalsIgnoreCase(cols[0].toLowerCase())){
                model.setName(cols[1]);
            }
        }*/
        
        return model;
    }
}
