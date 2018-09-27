/**
 * FlexiGridResult.java V1.0 Feb 20, 2012 4:11:38 PM
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
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

/**
 * 功能描述：
 * 
 * @author 池文杉
 * 
 * <p>
 * 修改历史：(修改人，修改时间，修改原因/内容)
 * </p>
 */
public class FlexiGridResult implements Serializable {
    
    private int page = 1;
    
    private int total;
    
    private List<FlexiGridRow> rows = new ArrayList<FlexiGridRow>();
    
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
     * @return the total
     */
    public int getTotal() {

        return total;
    }
    
    /**
     * @param total the total to set
     */
    public void setTotal(int total) {

        this.total = total;
    }

    
    /**
     * @return the rows
     */
    public List<FlexiGridRow> getRows() {
    
        return rows;
    }

    
    /**
     * @param rows the rows to set
     */
    public void setRows(List<FlexiGridRow> rows) {
    
        this.rows = rows;
    }
    
    
    /**
     * 
     * 功能描述：
     *
     * @author  池文杉
     * <p>创建日期 ：Feb 28, 2012 2:54:47 PM</p>
     *
     * @param jsonGridData
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public static FlexiGridResult parseGridDataJson(String jsonGridData){
        if(StringUtils.isEmpty(jsonGridData)){
            return null;
        }
        
        FlexiGridResult result = new FlexiGridResult();
        
        JSONObject jsonObject = JSONObject.fromObject(jsonGridData);
        
        result.setPage(jsonObject.getInt("page"));
        result.setTotal(jsonObject.getInt("total"));
        //设置行；
        JSONArray array = jsonObject.getJSONArray("rows");
        result.setRows(FlexiGridRow.parseRowJsonDataList(array.toString()));
        
        return result;
    }
    
}
