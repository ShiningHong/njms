package com.utils.base;

import java.util.List;

import org.springframework.stereotype.Repository;


@Repository
public interface QueryBaseDao<T> {
	
    
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
	 * 功能描述：按主键取对象(批量)
	 * @param List
	 * @return
	 */
	public List<T> selectByPrimaryKeyBatch(List keys);

	/**
	 * 功能描述：按条件分页查询
	 * @param example
	 * @return
	 */
	public List<T> selectByExamplePagination(BaseSearchModel example);

	
	
}
