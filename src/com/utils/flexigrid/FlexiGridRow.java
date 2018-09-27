/**
 * FlexiGridRow.java V1.0 Feb 20, 2012 4:17:42 PM
 * 
 * Copyright 2011 AsiaInfo Linkage, All rights reserved.
 * 
 * Modification history(By Time Reason):
 * 
 * Description:
 */

package com.utils.flexigrid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 功能描述：
 * 
 * @author 池文杉
 * 
 * <p>
 * 修改历史：(修改人，修改时间，修改原因/内容)
 * </p>
 */
public class FlexiGridRow implements Serializable {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3725311359724596337L;
    private String id;
    private Map<String, String> cell;
    
    /**
     * @return the id
     */
    public String getId() {

        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(String id) {

        this.id = id;
    }
    
    /**
     * @return the cell
     */
    public Map<String, String> getCell() {

        return cell;
    }
    
    /**
     * @param cell the cell to set
     */
    public void setCell(Map<String, String> cell) {

        this.cell = cell;
    }
    
    /**
     * 
     * 功能描述：将行的JSON数据，转为FlexiGridRow的List；
     *
     * @author  池文杉
     * <p>创建日期 ：Feb 28, 2012 3:05:12 PM</p>
     *
     * @param rowJsonData
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public static List<FlexiGridRow> parseRowJsonDataList(String rowJsonData){
        List<FlexiGridRow> rowLst = new ArrayList<FlexiGridRow>();
        rowLst.clear();
        if(StringUtils.isEmpty(rowJsonData)){
            return rowLst;
        }
        
        JSONArray array = JSONArray.fromObject(rowJsonData);
        for(int i=0;i<array.size();i++){
            FlexiGridRow row = parseRowJsonData(array.get(i).toString());
            rowLst.add(row);
        }
        return rowLst;
    }
    
    /**
     * 
     * 功能描述：根据FlexiGridRow的JSON串，反序列化
     *
     * @author  池文杉
     * <p>创建日期 ：Feb 28, 2012 3:27:30 PM</p>
     *
     * @param rowJsonData
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public static FlexiGridRow parseRowJsonData(String rowJsonData){
        if(StringUtils.isEmpty(rowJsonData)){
            return null;
        }
        FlexiGridRow row = new FlexiGridRow();
        //转换
        JSONObject jsonObject = JSONObject.fromObject(rowJsonData);
        //设置ID
        row.setId(jsonObject.getString("id"));
        //获取Cell，并设置cell
        JSONObject cellObject = jsonObject.getJSONObject("cell");
        Map<String,String> map = new HashMap();    
        for(Iterator iter = cellObject.keys(); iter.hasNext();){    
            String key = (String)iter.next();    
            map.put(key, cellObject.getString(key));    
        }   
        row.setCell(map);
        return row;
    }
}
