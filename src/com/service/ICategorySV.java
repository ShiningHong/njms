package com.service;




import java.util.List;

import com.bean.Category;

public interface ICategorySV extends com.utils.base.IQueryBaseServiceSV<Category> {

	public Category getNameById(int id);
	public int addCategory(Category category);
	public List<Category> list();
	public int update(Category category);  
	public void del(int id);
}
