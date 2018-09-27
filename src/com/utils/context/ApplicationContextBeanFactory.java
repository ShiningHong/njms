package com.utils.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @Title:实例化Bean工厂
 * @Description:
 * 开启工厂：
 *  spring配置文件： 配置扫描包路径<context:component-scan base-package="com.ai.*"/>
 *  注册工厂：<bean class="com.ai.utils.context.ApplicationContextBeanFactory" autowire="byName"/>
 *  
 */
public class ApplicationContextBeanFactory implements ApplicationContextAware {

	public static ApplicationContext context;
	
	private static final ThreadLocal<ApplicationContext> contextHolder = new ThreadLocal<ApplicationContext>();
	
	/**
	 * 
	 * 根据Bean名称获取对象实例
	 */
	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}
	
	public static Object getBean(Class Z) {
		return context.getBean(Z);
	}
	
	/**
	 * 
	 * 根据Bean名称判断对象实例是否存在
	 */
	public static Boolean containsBean(String beanName){
		return context.containsBean(beanName);
	}
	
	/**
	 * 
	 * 功能描述：返回bean的完整路径
	 * @param beanName
	 * @return
	 * <p>
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 * </p>
	 */
	public static String getBeanClassPath(String beanName){
		if(containsBean(beanName))return context.getType(beanName).getName();
		return null;
	}
	

	/**
	 * 清空spring框架配置信息
	 */
	public static void clear() {
		if (context != null) {
			context = null;
		}
	}
	
	@Override
	public void setApplicationContext(ApplicationContext app)
			throws BeansException {
		// TODO Auto-generated method stub
		context = app;
	}
	
	public static ApplicationContext getContext() {
		return context;
	}

}
