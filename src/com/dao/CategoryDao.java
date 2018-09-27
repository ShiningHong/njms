package com.dao;

import java.util.List;



import com.bean.Category;


 
public interface CategoryDao extends com.utils.base.QueryBaseDao<Category> {
 
      
    public int add(Category category);  
       
      
    public void delete(int id);  
       
      
    public Category get(int id);  
     
      
    public int update(Category category);   
       
      
    public List<Category> list();
    
      
    public int count();  
    
    
    
}