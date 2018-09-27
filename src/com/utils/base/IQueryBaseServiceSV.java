package com.utils.base;

import java.util.List;

import java.util.Map;

import com.utils.codeModel.ResultModel;
import com.utils.flexigrid.FlexiGridResult;

public interface IQueryBaseServiceSV<T>{

	/**
	 * 功能描述：按条件查询总数
	 * @param example
	 * @return
	 */
	public int countByExample(BaseSearchModel example);
	
	/**
	 * 功能描述：按条件查询对象列表
	 * @param example
	 * @return
	 */
	public List<T> selectByExample(BaseSearchModel example);
	
	/**
	 * 功能描述：按主键取对象
	 * @param key
	 * @return
	 */
	public T selectByPrimaryKey(Object key);
	
	/**
	 * 功能描述：按主键取对象(批量)(联合主键不使用)
	 * @param List
	 * @return
	 */
	public List<T> selectByPrimaryKeyBatch(List keys);

	/**
	 * 功能描述：按条件分页查询对象列表
	 * @param example
	 * @return
	 */
	public List<T> selectByExamplePagination(BaseSearchModel example);
	
	/**
	 * 功能描述：按条件分页查询FlexiGrid列表数据
	 * @param example
	 * @return
	 */
	public FlexiGridResult selectFlexgridByExample(BaseSearchModel example);
	
	/**
	 * 功能描述：按条件分页查询FlexiGrid列表数据
	 * @param example
	 * @param colDictMap 数据字典转换Map<"字段名","字典编码">
	 * @return
	 */
	public FlexiGridResult selectFlexgridByExample(BaseSearchModel example,Map<String, String> colDictMap);
	

}
