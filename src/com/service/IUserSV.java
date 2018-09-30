package com.service;

import com.bean.User;

public interface IUserSV extends com.utils.base.IQueryBaseServiceSV<User> {
	/**
	 * 
	 * 功能描述：密码的MD5验证
	 * @return
	 * <p>
	 */
	public boolean checkPsw(String password,String HD5password);

}
