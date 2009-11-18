package com.netpace.vzdn.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.netpace.vzdn.dao.IReportsDao;
import com.netpace.vzdn.dao.ISubMenuDAO;
import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.model.Report;
import com.netpace.vzdn.model.VzdnSubMenus;
import com.netpace.vzdn.model.VzdnUsers;

public class ReportsDAO extends GenericDAO<Report,Integer> implements IReportsDao<Report,Integer>{

	private static Logger log = Logger
	.getLogger(ReportsDAO.class);
	
	public ReportsDAO() {
		super(Report.class);
		// TODO Auto-generated constructor stub
	}

	public List<Report> getUserReports(List<Integer> privilegesIds) {
		Session session = null;
		Criteria criteria = null;
		List<Report> reports;
		try{
			session = HibernateSessionFactory.getSession();
			criteria = session.createCriteria(Report.class);
			criteria.add(Restrictions.in("privilege.privilegeId", privilegesIds));
			reports = criteria.list();
			session.close();
			return reports;
		}catch(Exception e){
			session.close();
			log.error("Error fetching reports ",e);
			return null;
			
		}
		
	}
	
	
	
	
	
	

}
