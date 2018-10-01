package com.bean;

import com.utils.base.BaseEntity;

public class User extends BaseEntity {

	private String username; //用户名（学号/职工号）
	private String password; //密码
	private String role; //角色（学生/教师）
	private String name;//用户姓名

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
