package com.model;

import com.utils.sql.SqlAttr;

public class CategoryModel extends com.utils.base.BaseSearchModel {
	@SqlAttr(column="ID",dataType="Long",idArg=true)
	private java.lang.String id;//   流水号
	@SqlAttr(column="NAME",dataType="String")
    private java.lang.String name;//   null
	
	public java.lang.String getId() {
		return id;
	}
	public void setId(java.lang.String id) {
		this.id = id;
	}
	public java.lang.String getName() {
		return name;
	}
	public void setName(java.lang.String name) {
		this.name = name;
	}
	
}
