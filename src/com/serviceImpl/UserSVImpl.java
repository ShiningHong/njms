package com.serviceImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bean.User;
import com.dao.UserDao;
import com.model.UserModel;
import com.service.IUserSV;

@Service("iUserSV")
@Transactional
public class UserSVImpl extends com.utils.base.QueryBaseServiceSVImpl<User>
		implements IUserSV {
	@Autowired
	private UserDao dao;

	@Override
	public com.utils.base.QueryBaseDao<User> getDao() {
		return dao;
	}

	//传入密码和数据库中的MD5密码
	public boolean checkPsw(String str, String HD5password) {
		// TODO Auto-generated method stub
		// 将字符串转换为MD5格式
		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] hash = md.digest();
			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10) {
					hexString.append("0"
							+ Integer.toHexString((0xFF & hash[i])));
				} else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		//比较两个字符串是否相等
		if(hexString.toString().equals(HD5password))
			return true;
		else
			return false;

	}

	@Override
	public String selectNameByexample(UserModel model) {
		// TODO Auto-generated method stub
		return dao.selectNameByexample(model);
	}

}
