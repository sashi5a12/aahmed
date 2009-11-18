package com.netpace.vzdn.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.netpace.vzdn.dao.ISysRoleDao;
import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.exceptions.PrivilegesNotFoundException;
import com.netpace.vzdn.exceptions.UserNotFoundException;
import com.netpace.vzdn.exceptions.RoleNotFoundException;
import com.netpace.vzdn.global.VzdnConstants;
import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.model.VzdnSysPrivileges;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnUsers;

public class SysRoleDAO extends GenericDAO<VzdnSysRoles,Integer> implements ISysRoleDao{
	
	
	private static Logger log = Logger
	.getLogger(SysRoleDAO.class);
	
	public SysRoleDAO(){
		super(VzdnSysRoles.class);
	}    	
	
	
	@Override
	public List<VzdnSysRoles> getRolesOnType(List<Integer> typeIds){
		
		try {
			Session session = HibernateSessionFactory.getSession();
			Criteria criteria = session.createCriteria(VzdnSysRoles.class);
			criteria.add(Restrictions.in("vzdnTypes.typeId", typeIds));
			List list = criteria.list();
			System.out.println("---------------in dao size is "+list.size());
			//HibernateSessionFactory.closeSession();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			HibernateSessionFactory.closeSession();
		}
	}
	
	
	public VzdnSysRoles getRolesOnId(Integer id){
		
		Session hibernateSession = null;
		VzdnSysRoles role = null;
    	try{            	
    		hibernateSession = HibernateSessionFactory.getSession();
    		//log.info("Going to find "+pojoClass.getName()+" object");
    		role = (VzdnSysRoles)hibernateSession.get(VzdnSysRoles.class, id);
    		//log.info(pojoClass.getName() + " Object found successfully");
    		return role;
	     } 
    	catch (RuntimeException re) {
    		//log.info("Some problem with finding "+ pojoClass.getName() +" object");
            throw re;
        }
    	//every caller of this method must explicitly close the session
		
	}


public VzdnSysRoles getRolesOnMappingId(Integer vzdnRoleMappingId)throws Exception{
	
	Session hibernateSession = null;
	try{            	
		hibernateSession = HibernateSessionFactory.getSession();
		//log.info("Going to find "+pojoClass.getName()+" object");
		Query searchQuery =hibernateSession.createQuery("select r from VzdnSysRoles r where vzdnRoleMappingId=" + vzdnRoleMappingId);
		
		 List<VzdnSysRoles> vzdnRoles = searchQuery.list();
         if (vzdnRoles == null || vzdnRoles.isEmpty()) {
             //throw new RoleNotFoundException("role with id '" + vzdnRoleMappingId + "' not found...");
        	 return null;
         } else
             return vzdnRoles.get(0);         
		
		//log.info(pojoClass.getName() + " Object found successfully");
		
     } 
	catch (Exception re) {
		//log.info("Some problem with finding "+ pojoClass.getName() +" object");
        throw re;
    }	
	finally {
		HibernateSessionFactory.closeSession();
	}	
	
}


public List<VzdnSysRoles> getMinimumUserRoles(List<Integer> minimumRoles){
	try {
		Session session = HibernateSessionFactory.getSession();
		Criteria criteria = session.createCriteria(VzdnSysRoles.class);
		criteria.add(Restrictions.in("vzdnRoleMappingId", minimumRoles));
		List list = criteria.list();
		System.out.println("---------------in dao size is "+list.size());
		//HibernateSessionFactory.closeSession();
		return list;
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}finally {
		HibernateSessionFactory.closeSession();
	}	
}


	public List<VzdnSysPrivileges> getPrivilegesOnRoleId(Integer roleId) throws PrivilegesNotFoundException{
		
		Session hibernateSession = null;
		try{            	
			hibernateSession = HibernateSessionFactory.getSession();
			//log.info("Going to find "+pojoClass.getName()+" object");
			Query searchQuery =hibernateSession.createQuery("select privileges from VzdnSysRoles r where roleId=" + roleId);
			
			 List<VzdnSysPrivileges> privileges = searchQuery.list();
             return privileges;
			
	     } 
		catch (Exception re) {
			//log.info("Some problem with finding "+ pojoClass.getName() +" object");
	        throw new PrivilegesNotFoundException("No privileges found for roleid=" + roleId);
	    }	
		finally {
			HibernateSessionFactory.closeSession();
		}	
	}
	
	public List<VzdnSysRoles> findAll() {
		Session hibernateSession = null;
		List<VzdnSysRoles> objects = null;
    	try{            	
    		hibernateSession = HibernateSessionFactory.getSession();
    		log.info("Going to find all "+VzdnSysRoles.class.getName()+" object");
    		Query query = hibernateSession.createQuery("from "+VzdnSysRoles.class.getName()+" as objects");
    		objects = query.list();
    		log.info("All "+VzdnSysRoles.class.getName()+" Object found successfully");
    		return objects;
	     } 
    	catch (RuntimeException re) {
    		log.info("Some Issue with finding all " +VzdnSysRoles.class.getName()+ " object");
	        throw re;
        }
    	finally {
			HibernateSessionFactory.closeSession();
		}
	}
	
	public List<VzdnEventNotifications> getNotificationOnRole(VzdnSysRoles role) throws Exception{
		Session hibernateSession = null;
		List<VzdnEventNotifications> notifications = null;
    	try{            	
    		hibernateSession = HibernateSessionFactory.getSession();
    		Query query = hibernateSession.createQuery("select notif from VzdnEventNotifications notif " +
    				" join notif.roles roles " +
    				" where roles.roleId =:roleId");
    		query.setInteger("roleId", role.getRoleId());
    		notifications = query.list();
    		return notifications;
	     } 
    	catch (Exception re) {
    		log.info("Some Issue with finding all " +VzdnSysRoles.class.getName()+ " object");
	        throw re;
        }	
    	finally{
    		HibernateSessionFactory.closeSession();
    	}
	}
	
	public List<VzdnSysRoles> getNonHiddenRoles() throws Exception{
		Session hibernateSession = null;
		try{            	
			hibernateSession = HibernateSessionFactory.getSession();
			//log.info("Going to find "+pojoClass.getName()+" object");
			Query searchQuery =hibernateSession.createQuery("select roles from VzdnSysRoles roles " + 
															" join roles.vzdnTypes type " + 
															" where type.typeId !=:hiddenRoleId " +
															" or roles.roleId =:developerRole");
			
			searchQuery.setInteger("hiddenRoleId", VzdnConstants.HIDDEN_ROLE);
			searchQuery.setInteger("developerRole", VzdnConstants.DEFAULT_VZDN_CORE_ROLE);
			 List<VzdnSysRoles> vzdnRoles = searchQuery.list();
			 return vzdnRoles;
	     } 
		catch (Exception re) {
			log.error("Problem with finding non hidden roles:\n" + re);
	        throw re;
	    }
		finally{
    		HibernateSessionFactory.closeSession();
    	}
	}
	
	public List<VzdnUsers> getUsersInRole(Integer roleId) throws Exception{
		Session hibernateSession = null;
		try{            	
			hibernateSession = HibernateSessionFactory.getSession();
			//log.info("Going to find "+pojoClass.getName()+" object");
			Query searchQuery =hibernateSession.createQuery("select users from VzdnUsers users " + 
															" join users.roles roles " + 
															" where roles.roleId =:roleId ");
															
			
			searchQuery.setInteger("roleId", roleId);			
			 List<VzdnUsers> users = searchQuery.list();
			 return users;
	     } 
		catch (Exception re) {
			log.error("Problem with finding non hidden roles:\n" + re);
	        throw re;
	    }
		finally{
    		HibernateSessionFactory.closeSession();
    	}
	}
}
