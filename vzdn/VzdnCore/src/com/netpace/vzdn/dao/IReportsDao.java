package com.netpace.vzdn.dao;

import java.io.Serializable;
import java.util.List;

import com.netpace.vzdn.model.Report;
import com.netpace.vzdn.model.VzdnUsers;

public interface IReportsDao<T,PK extends Serializable> extends IGenericDAO<T, PK>{
	
	public List<Report> getUserReports(List<Integer> privilegesIds);

}
