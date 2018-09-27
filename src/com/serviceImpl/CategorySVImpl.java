package com.serviceImpl;




import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.Category;
import com.dao.CategoryDao;
import com.service.ICategorySV;

@Service("iSceneInfoFindSV")
public class CategorySVImpl extends com.utils.base.QueryBaseServiceSVImpl<Category> implements ICategorySV{
	@Autowired
	private CategoryDao dao;
	
	@Override    
    public com.utils.base.QueryBaseDao<Category> getDao() {
        return dao;
    }
	
	
	public Category getNameById(int id) {
		// TODO Auto-generated method stub
		return dao.get(id);
	}
	public int addCategory(Category category) {
		// TODO Auto-generated method stub
		return dao.add(category);
	}
	public List<Category> list(){
		return dao.list();
	}
	public int update(Category category){
		return dao.update(category);
	}
	public void del(int id) {
		// TODO Auto-generated method stub
		dao.delete(id);
		return;
	}
}
