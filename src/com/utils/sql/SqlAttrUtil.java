package com.utils.sql;

import java.lang.reflect.Field;

import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * {@link SqlAttr} 相关工具类
 * 
 * @author zhengw3
 * @date 2018年7月16日
 */
public class SqlAttrUtil {

	private static Logger logger = (Logger) LogManager.getLogger(SqlAttrUtil.class);

	/**
	 * 通过反射机制为对象赋值<br />
	 * 假设dataMap中有键值对{"id":"1"}，则obj（含所有继承自父类的字段）中若存在带@SqlAttr(column="id" ...)注解的字段，则将该字段赋值为"1"
	 * 
	 * @author zhengw3
	 * @date 2018年7月16日
	 * @param obj 待赋值对象
	 * @param dataMap 数据键值对，SqlAttr.column=值
	 * @return 待赋值对象 obj
	 */
	public static <T> T setFieldValueBySqlAttr(T obj, Map<String, String> dataMap) {
		String value = null;
		Class<?> clazz = obj.getClass();
		while (clazz != null) {
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; ++i) {
				Field field = fields[i];
				SqlAttr sqlAttr = field.getAnnotation(SqlAttr.class);
				if (sqlAttr == null || (value = dataMap.remove(sqlAttr.column())) == null)
					continue;

				field.setAccessible(true);
				try {
					field.set(obj, ConvertUtils.convert(value, field.getType()));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					logger.error(field + " 设值失败，值：" + value, e);
				}
				if (dataMap.isEmpty())
					break;
			}
			if (dataMap.isEmpty())
				break;
			clazz = clazz.getSuperclass();
		}
		return obj;
	}
}