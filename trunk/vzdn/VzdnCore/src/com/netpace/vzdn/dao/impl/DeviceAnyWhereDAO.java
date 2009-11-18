package com.netpace.vzdn.dao.impl;

import java.math.BigInteger;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.netpace.vzdn.dao.IDeviceAnyWhere;
import com.netpace.vzdn.dao.ISubMenuDAO;
import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.model.DeviceAnyWhere;
import com.netpace.vzdn.model.VzdnSubMenus;

public class DeviceAnyWhereDAO extends GenericDAO<DeviceAnyWhere,Integer> implements IDeviceAnyWhere<DeviceAnyWhere,Integer> {
	
	private static Logger log = Logger
	.getLogger(DeviceAnyWhereDAO.class);
	
	public DeviceAnyWhereDAO(){
		super(DeviceAnyWhere.class);
	}

	

	public void saveDeviceInfo(DeviceAnyWhere deviceAnyWhere) throws Exception {    	
    	Session hibernateSession = null;
    	Transaction hibernateTransaction = null;
    	try{            	
    		hibernateSession = HibernateSessionFactory.getSession();
    		hibernateTransaction = hibernateSession.beginTransaction();
    		log.info("Going to save DeviceAnyWhere object");
    		hibernateSession.save(deviceAnyWhere);   
    		log.info("DeviceAnyWhere Object saved successfully");
    		hibernateTransaction.commit();
    		hibernateSession.close();
	     } 
    	catch (Exception re) {
    		log.error("Some problem with saving  DeviceAnyWhere object"+re.getMessage());
        	if(null != hibernateSession){
        		hibernateTransaction.rollback();
        		hibernateSession.close();
        	}
	        throw re;
        }
    }
	
	

}
