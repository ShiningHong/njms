package com.utils.sql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SqlAttr {

	 String column() default "";//值为对应的数据库字段，如不填则以属性名（必须和表字段相同）查询
	 String dataType() default "String";//数据类型，String/Long/Integer/Float/Double/DateTime 如不填默认String
	 SqlOperType operType() default SqlOperType.eq;//逻辑运算方式 =/!=/in/not in 使用SqlPropertyUtil时，优先级低于operConfigs的指定
	 //String colDictCode() default "";//转义的数据字典编码
	 boolean idArg() default false;//是否主键
	 String autoType()  default "";//增长方式 01-UUID 02-SEQ
	 String seqName() default "";//序列名

}
