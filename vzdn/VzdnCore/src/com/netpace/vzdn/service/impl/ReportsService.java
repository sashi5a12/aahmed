package com.netpace.vzdn.service.impl;

import java.util.List;


import com.netpace.vzdn.dao.IReportsDao;
import com.netpace.vzdn.dao.impl.ReportsDAO;
import com.netpace.vzdn.model.Report;
import com.netpace.vzdn.service.IReportsService;

public class ReportsService implements IReportsService {

	private ReportsDAO reportsDao;

	
	
	

	public ReportsDAO getReportsDao() {
		return reportsDao;
	}





	public void setReportsDao(ReportsDAO reportsDao) {
		this.reportsDao = reportsDao;
	}





	public List<Report> getUserReports(List<Integer> privilegesIds) {
		return reportsDao.getUserReports(privilegesIds);
	}

	
	
}
