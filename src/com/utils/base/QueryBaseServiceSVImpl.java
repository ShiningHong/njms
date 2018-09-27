package com.utils.base;


import java.util.List;

import java.util.Map;

import com.utils.flexigrid.FlexiGridResult;
import com.utils.flexigrid.FlexiGridUtils;
public abstract class QueryBaseServiceSVImpl<T> implements IQueryBaseServiceSV<T>{
		
	/**
	 * 子类中实现
	 */
	public abstract QueryBaseDao<T> getDao();
	
    
	/**
	 * 功能描述：按条件查询总数
	 * @param example
	 * @return
	 */
	public int countByExample(BaseSearchModel example){
		return getDao().countByExample(example);
	}
    
	/**
	 * 功能描述：按条件查询对象列表
	 * @param example
	 * @return
	 */
	public List<T> selectByExample(BaseSearchModel example){
		return getDao().selectByExample(example);
	}
	
	/**
	 * 功能描述：按主键取对象
	 * @param example
	 * @return
	 */
	public T selectByPrimaryKey(Object key){
		return getDao().selectByPrimaryKey(key);
	}
	
	/**
	 * 功能描述：按主键取对象(批量)(联合主键不使用)
	 * @param List
	 * @return
	 */
	public List<T> selectByPrimaryKeyBatch(List keys){
		return getDao().selectByPrimaryKeyBatch(keys);
	}

	
	/**
	 * 功能描述：按条件分页查询对象列表
	 * @param example
	 * @return
	 */
	public List<T> selectByExamplePagination(BaseSearchModel example){
		return getDao().selectByExamplePagination(example);
	}
	
	/**
	 * 功能描述：按条件分页查询FlexiGrid列表数据
	 * @param example
	 * @return
	 */
	public FlexiGridResult selectFlexgridByExample(BaseSearchModel example){
		return this.selectFlexgridByExample(example, null);
	}
	
	/**
	 * 功能描述：按条件分页查询FlexiGrid列表数据
	 * @param example
	 * @param colDictMap 数据字典转换Map<"字段名","字典编码">
	 * @return
	 */
	public FlexiGridResult selectFlexgridByExample(BaseSearchModel example,Map<String, String> colDictMap){
		int total = 0;
		total = countByExample(example);
		List<T> beans = null;
		if(total>0){
			if(example.getRp()<0){//不分页
				beans = selectByExample(example);
			}else{
				beans = selectByExamplePagination(example);
			}
		}
		return FlexiGridUtils.generateFlexiGridResultByEntity(total, example.getPage(), beans, colDictMap, true);
	}

	
	
}
