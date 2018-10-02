package com.service;

import com.bean.User;
import com.model.UserModel;

public interface IUserSV extends com.utils.base.IQueryBaseServiceSV<User> {
	/**
	 * 
	 * 功能描述：密码的MD5验证
	 * @return
	 * <p>
	 */
	public boolean checkPsw(String password,String HD5password);
	/**
	 * 功能描述：根据账号和角色获取姓名
	 * @param example
	 * @return
	 */
	public String selectNameByexample(UserModel model);
}
