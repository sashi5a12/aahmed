package com.netpace.vic.dao;

import java.sql.Connection;

import com.day.commons.datasource.poolservice.DataSourcePool;

public interface GenericDAO {
	
	public void setSource(DataSourcePool source);

	public Connection getConnection();
}
