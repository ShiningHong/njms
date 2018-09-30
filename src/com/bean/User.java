package com.bean;

import com.utils.base.BaseEntity;

public class User extends BaseEntity {

	private String userName; //用户名（学号/职工号）
	private String password; //密码
	private String role; //角色（学生/教师）

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
