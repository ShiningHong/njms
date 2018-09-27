package com.utils.base;

import java.io.Serializable;

public class BaseModel implements Serializable{
	
	private String params;//附加条件（包含and/or）

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	
	

	/*private Map<String,String> likeColMap = new LinkedHashMap<String, String>();//模糊查询列Map<列名,列名>
	private Map<String,String> isNullColMap = new LinkedHashMap<String, String>();//为空查询列Map<列名,列名>

	public Map<String, String> getLikeColMap() {
		return likeColMap;
	}

	public void setLikeColMap(Map<String, String> likeColMap) {
		this.likeColMap = likeColMap;
	}
	
	public void addLikeCols(String...colNames){
		if(colNames!=null&&colNames.length>0){
			if(likeColMap==null){
				likeColMap = new LinkedHashMap<String, String>();
			}
			for(String col:colNames){
				likeColMap.put(col, col);
			}
		}
	}
	public void removeLikeCols(String...colNames){
		if(likeColMap==null)return ;
		if(colNames!=null&&colNames.length>0){
			for(String col:colNames){
				likeColMap.remove(col);
			}
		}
	}

	public Map<String, String> getIsNullColMap() {
		return isNullColMap;
	}

	public void setIsNullColMap(Map<String, String> isNullColMap) {
		this.isNullColMap = isNullColMap;
	}
	public void addIsNullCols(String...colNames){
		if(colNames!=null&&colNames.length>0){
			if(isNullColMap==null){
				isNullColMap = new LinkedHashMap<String, String>();
			}
			for(String col:colNames){
				isNullColMap.put(col, col);
			}
		}
	}
	public void removeIsNullCols(String...colNames){
		if(isNullColMap==null)return ;
		if(colNames!=null&&colNames.length>0){
			for(String col:colNames){
				isNullColMap.remove(col);
			}
		}
	}*/
	
	
	
}
