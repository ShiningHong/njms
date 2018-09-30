package com.model;

import com.utils.sql.SqlAttr;

public class UserModel extends com.utils.base.BaseSearchModel {
	@SqlAttr(column="USERNAME",dataType="String",idArg=true)
	private java.lang.String username;//   流水号
	@SqlAttr(column="PASSWORD",dataType="String")
    private java.lang.String password;//   null
	@SqlAttr(column="ROLE",dataType="String")
    private java.lang.String role;//   null
	public java.lang.String getUsername() {
		return username;
	}
	public void setUsername(java.lang.String username) {
		this.username = username;
	}
	
	public java.lang.String getPassword() {
		return password;
	}
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	public java.lang.String getRole() {
		return role;
	}
	public void setRole(java.lang.String role) {
		this.role = role;
	}
	
	
}
