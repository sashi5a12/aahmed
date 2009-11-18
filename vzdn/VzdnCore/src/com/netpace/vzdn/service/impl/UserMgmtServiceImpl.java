package com.netpace.vzdn.service.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.netpace.vzdn.dao.IGenericDAO;
import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.service.UserMgmtService;

public class UserMgmtServiceImpl implements UserMgmtService{
	
	private IGenericDAO<VzdnUsers,Integer> usersDao;	

	public List<VzdnUsers> getAllUsers(){
		//Session session = HibernateSessionFactory.getSession();
		//Transaction transaction = session.beginTransaction();
		List<VzdnUsers> userList =  usersDao.findAll();
		//HibernateSessionFactory.closeSession();
		return userList;
	}	

	public void updateUser(VzdnUsers user){
		Transaction transaction = null;
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			usersDao.update(user);
			session.flush();
			transaction.commit();
			HibernateSessionFactory.closeSession();
		} 
		catch (Exception e) {
			e.printStackTrace();
			if (null != transaction)
				transaction.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}
	

	public void removeUser(VzdnUsers user){
		
		
		try {
			Session session = HibernateSessionFactory.getSession();
			Transaction transaction = session.beginTransaction();
			usersDao.delete(user);
			transaction.commit();
			HibernateSessionFactory.closeSession();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			HibernateSessionFactory.closeSession();
		}
		
		
	}

	public VzdnUsers getUserById(Integer userId){
		
		try {
			//Session session = HibernateSessionFactory.getSession();
			//Transaction transaction = session.beginTransaction();
			VzdnUsers userBean =  (VzdnUsers)usersDao.findById(userId);
			userBean.getRoles();
			HibernateSessionFactory.closeSession();
			return userBean;
			
			
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
			
		}
		
		
		
		
		
		
	}	
	
	public IGenericDAO<VzdnUsers,Integer> getUsersDao(){
		return usersDao;
	}	
	
	public void setUsersDao(IGenericDAO<VzdnUsers,Integer> usersDao){
		this.usersDao = usersDao;
	}

}
