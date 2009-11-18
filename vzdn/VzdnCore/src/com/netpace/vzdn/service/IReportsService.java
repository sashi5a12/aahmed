package com.netpace.vzdn.service;

import java.util.List;

import com.netpace.vzdn.model.Report;

public interface IReportsService {

	public List<Report> getUserReports(List<Integer> privilegesIds); 
}
