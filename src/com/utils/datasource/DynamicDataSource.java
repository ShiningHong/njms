package com.utils.datasource;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;


public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		// TODO Auto-generated method stub
		//return DataSourceContextHolder.getDataSourceType();
		DataSourceType dataSourceType= DataSourceContextHolder.getDataSourceType();
		return dataSourceType;
	}

	public void determineTargetDataSourceSelf(){
		super.determineTargetDataSource();
	}
	
	
	public DataSource getDataSource(String dsName){
		return super.resolveSpecifiedDataSource(dsName);
	}
	
	public void setDataSourceLookup(DataSourceLookup dataSourceLookup) {
		super.setDataSourceLookup(dataSourceLookup);
	}

	
	public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
		super.setDefaultTargetDataSource(defaultTargetDataSource);
	}

	
	public void setTargetDataSources(Map targetDataSources) {
		super.setTargetDataSources(targetDataSources);
	}
}
