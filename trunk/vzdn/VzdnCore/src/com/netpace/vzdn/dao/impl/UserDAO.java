package com.netpace.vzdn.dao.impl;

import com.netpace.vzdn.dao.IUserDAO;
import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.exceptions.RoleNotFoundException;
import com.netpace.vzdn.exceptions.UserNotFoundException;
import com.netpace.vzdn.global.VzdnConstants;
import com.netpace.vzdn.model.Searchable;
import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnTypes;
import com.netpace.vzdn.model.VzdnUsers;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * @author Ikramullah Khan UserDAO represents the user object genericly
 *         extending the GeneriDAO. This class also implements IUserDAO
 *         interface. Any 'User' specific methods must be defined in the
 *         IUserDAO interace.
 */

public class UserDAO extends GenericDAO<VzdnUsers, Integer> implements
		IUserDAO<VzdnUsers, Integer> {

	private static Logger log = Logger.getLogger(UserDAO.class);

	public UserDAO() {
		super(VzdnUsers.class);
	}

	public VzdnUsers getUserByUserName(String userName)
			throws UserNotFoundException {
		Session hibernateSession = null;
		VzdnUsers object = null;
		try {
			hibernateSession = HibernateSessionFactory.getSession();
			Query searchQuery = hibernateSession.createQuery("select u from VzdnUsers u where userName=:uName");
			searchQuery.setString("uName", userName);
			log.info("Going to find " + VzdnUsers.class.getName() + " object");

			// searchQuery.setParameter(1, userName);
			List<VzdnUsers> vzdnUsers = searchQuery.list();
			if (vzdnUsers == null || vzdnUsers.isEmpty()) {
				throw new UserNotFoundException("user '" + userName
						+ "' not found...");
			} else {
				return vzdnUsers.get(0);
			}
		} catch (UserNotFoundException re) {
			log.info("Some problem with finding " + VzdnUsers.class.getName()
					+ " object");			
			throw re;
		}		
		finally{
			HibernateSessionFactory.closeSession();
		}

	}

	public List<VzdnSysRoles> getUserRoles(int userId)
			throws RoleNotFoundException {
		Session hibernateSession = null;
		VzdnUsers object = null;
		try {
			hibernateSession = HibernateSessionFactory.getSession();
			String query = "select roles from VzdnSysRoles roles " +
					" join roles.users users where users.userId =:userId";
			

			Query searchQuery = hibernateSession.createQuery(query);

			searchQuery.setInteger("userId", userId);

			// log.info("Going to find "+VzdnUsers.class.getName()+" object");
			// searchQuery.setParameter(1, userName);
			List<VzdnSysRoles> vzdnRoles = searchQuery.list();
			return vzdnRoles;

		} catch (Exception re) {
			log.info("Some problem with finding " + VzdnUsers.class.getName()
					+ " object");
			throw new RoleNotFoundException("Some issue in getting user roles:");
		}
		finally{
			HibernateSessionFactory.closeSession();
		}

	}

	public List<VzdnTypes> getAllUserTypes() {
		Session hibernateSession = null;
		List<VzdnTypes> objects = null;
		try {
			hibernateSession = HibernateSessionFactory.getSession();
			// log.info("Going to find all "+pojoClass.getClass()+" object");
			Query query = hibernateSession.createQuery("from "
					+ VzdnTypes.class.getName() + " as objects");
			objects = query.list();
			// log.info("All "+pojoClass.getName()+" Object found
			// successfully");
			return objects;
		} catch (RuntimeException re) {
			// log.info("Some Issue with finding all " +pojoClass.getName()+ "
			// object");
			throw re;
		}
		finally{
			HibernateSessionFactory.closeSession();
		}
	}
	

	public List search(VzdnUsers criteria) {
		log.info("List find(VzdnUsers criteria) -- START --");
		List<VzdnUsers> usersList = null;
		Transaction transaction = null;
		Session hibernateSession = null;

		try {

			DetachedCriteria usersCriteria = DetachedCriteria
					.forClass(VzdnUsers.class);

			if (null != criteria.getUserType()) {

				if (null != criteria.getUserType().getTypeId()
						&& criteria.getUserType().getTypeId().intValue() != -1)
					usersCriteria.add(Restrictions.eq("userType", criteria
							.getUserType()));
				else {
					VzdnTypes developer = new VzdnTypes(new Integer(
							VzdnConstants.DeveloperUser));
					VzdnTypes verizon = new VzdnTypes(new Integer(
							VzdnConstants.VerizonUser));
					usersCriteria.add(Restrictions.or(Restrictions.eq(
							"userType", developer), Restrictions.eq("userType",
							verizon)));
				}
			}

			if (null != criteria.getUserName()
					&& !"".equals(criteria.getUserName()))
				usersCriteria.add(Restrictions.like("userName", criteria
						.getUserName(), MatchMode.ANYWHERE));

			if (null != criteria.getFirstName()
					&& !"".equals(criteria.getFirstName()))
				usersCriteria.add(Restrictions.like("firstName", criteria
						.getFirstName(), MatchMode.ANYWHERE));

			if (null != criteria.getLastName()
					&& !"".equals(criteria.getLastName()))
				usersCriteria.add(Restrictions.like("lastName", criteria
						.getLastName(), MatchMode.ANYWHERE));

			hibernateSession = HibernateSessionFactory.getSession();

			usersList = usersCriteria.getExecutableCriteria(hibernateSession)
					.list();

			log.info("userslist size = " + usersList.size());

			return usersList;

		} catch (Exception e) {
			if (null != transaction) {
				transaction.rollback();
			}
			//throw new UserNotFoundException(e.getMessage());
			return null;
		} 
		finally{
			HibernateSessionFactory.closeSession();
		}

	}
	
	public List<VzdnUsers> getAllUsersOnUserName(String criteria){
		log.info("List find(VzdnUsers criteria) -- START --");
		List<VzdnUsers> usersList = null;
		Transaction transaction = null;
		Session hibernateSession = null;

		try {

			DetachedCriteria usersCriteria = DetachedCriteria.forClass(VzdnUsers.class);
			
			if (null != criteria && !"".equals(criteria))
				usersCriteria.add(Restrictions.like("userName", criteria, MatchMode.ANYWHERE));
			hibernateSession = HibernateSessionFactory.getSession();
			usersList = usersCriteria.getExecutableCriteria(hibernateSession).list();

			log.info("userslist size = " + usersList.size());

			return usersList;

		} catch (Exception e) {
			if (null != transaction) {
				transaction.rollback();
			}
			//throw new UserNotFoundException(e.getMessage());
			return null;
		} finally{
			HibernateSessionFactory.closeSession();
		}
	}
	
	
	public List<VzdnUsers> getUserOnUserTypeName(String criteria){
		log.info("List find(VzdnUsers criteria) -- START --");
		List<VzdnUsers> usersList = null;
		Transaction transaction = null;
		Session hibernateSession = null;

		try {
			
			criteria = "'%"+ criteria + "%'";

			hibernateSession = HibernateSessionFactory.getSession();
			Query searchQuery = hibernateSession.createQuery("from VzdnUsers users " +
					" join users.userType types " +
					" where types.typeValue like " + criteria);
			
			
			//searchQuery.setString(0, criteria);
			
			usersList = searchQuery.list();

			log.info("userslist size = " + usersList.size());

			return usersList;

		} catch (Exception e) {
			if (null != transaction) {
				transaction.rollback();
			}
			//throw new UserNotFoundException(e.getMessage());
			return null;
		} 
		finally{
			HibernateSessionFactory.closeSession();
		}
	}
	
	public List<VzdnUsers> getUserOnFirstName(String criteria){
		log.info("List find(VzdnUsers criteria) -- START --");
		List<VzdnUsers> usersList = null;
		Transaction transaction = null;
		Session hibernateSession = null;

		try {

			DetachedCriteria usersCriteria = DetachedCriteria.forClass(VzdnUsers.class);
			
			if (null != criteria && !"".equals(criteria))
				usersCriteria.add(Restrictions.like("firstName", criteria, MatchMode.ANYWHERE));
			
			hibernateSession = HibernateSessionFactory.getSession();
			usersList = usersCriteria.getExecutableCriteria(hibernateSession).list();

			log.info("userslist size = " + usersList.size());

			return usersList;

		} catch (Exception e) {
			if (null != transaction) {
				transaction.rollback();
			}
			//throw new UserNotFoundException(e.getMessage());
			return null;
		} finally{
			HibernateSessionFactory.closeSession();
		}
	}
	public List<VzdnUsers>getUserLastName(String criteria){
		log.info("List find(VzdnUsers criteria) -- START --");
		List<VzdnUsers> usersList = null;
		Transaction transaction = null;
		Session hibernateSession = null;

		try {

			DetachedCriteria usersCriteria = DetachedCriteria.forClass(VzdnUsers.class);
			
			if (null != criteria && !"".equals(criteria))
				usersCriteria.add(Restrictions.like("lastName", criteria, MatchMode.ANYWHERE));
			hibernateSession = HibernateSessionFactory.getSession();
			usersList = usersCriteria.getExecutableCriteria(hibernateSession).list();

			log.info("userslist size = " + usersList.size());

			return usersList;

		} catch (Exception e) {
			if (null != transaction) {
				transaction.rollback();
			}
			//throw new UserNotFoundException(e.getMessage());
			return null;
		} 
		finally{
			HibernateSessionFactory.closeSession();
		}
	}
	
	public List<VzdnEventNotifications> getUserNotifications(Integer userId, VzdnEventNotifications searchCriteria){
		
		log.info("List find(VzdnEventNotifications criteria) -- START --");
		List<VzdnEventNotifications> notifList = null;
		Transaction transaction = null;
		Session hibernateSession = null;

		try {
			
			String notificationTitle = searchCriteria.getNotificationTitle();
			notificationTitle = "'%" + notificationTitle + "%'";


			hibernateSession = HibernateSessionFactory.getSession();
			Query searchQuery = hibernateSession.createQuery("select notifs from VzdnEventNotifications notifs " +
					" join notifs.optOutRecipients recipients " +
					" where recipients.userId =:userId ");
			
			
			//searchQuery.setString(0, criteria);
			searchQuery.setInteger("userId", userId);
			
			notifList = searchQuery.list();

			log.info("notificationList size = " + notifList.size());

			return notifList;

		} catch (Exception e) {
			if (null != transaction) {
				transaction.rollback();
			}
			//throw new UserNotFoundException(e.getMessage());
			return null;
		} finally{
			HibernateSessionFactory.closeSession();			
		}
	}

}