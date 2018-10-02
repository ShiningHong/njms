package com.dao;

import com.bean.User;
import com.model.UserModel;
import com.utils.base.BaseSearchModel;



 
public interface UserDao extends com.utils.base.QueryBaseDao<User> {
 
	
	/**
	 * 功能描述：根据账号和角色获取姓名
	 * @param example
	 * @return
	 */
	public String selectNameByexample(UserModel model);
}