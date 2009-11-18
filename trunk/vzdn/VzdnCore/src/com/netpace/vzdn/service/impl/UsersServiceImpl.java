package com.netpace.vzdn.service.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mysql.jdbc.TimeUtil;
import com.netpace.vzdn.dao.IGenericDAO;
import com.netpace.vzdn.dao.INewsLetterDao;
import com.netpace.vzdn.dao.INewsLetterOptOutDao;
import com.netpace.vzdn.dao.IPrivilegesDAO;
import com.netpace.vzdn.dao.ISubMenuDAO;
import com.netpace.vzdn.dao.IUserDAO;
import com.netpace.vzdn.dao.ISysRoleDao;
import com.netpace.vzdn.dao.impl.NewsLetterDao;
import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.exceptions.MenuNotFoundException;
import com.netpace.vzdn.exceptions.PrivilegesNotFoundException;
import com.netpace.vzdn.exceptions.RoleNotFoundException;
import com.netpace.vzdn.exceptions.SubMenuNotFoundException;
import com.netpace.vzdn.exceptions.UserNotFoundException;
import com.netpace.vzdn.global.VzdnConstants;
import com.netpace.vzdn.model.MyNotifications;
import com.netpace.vzdn.model.Searchable;
import com.netpace.vzdn.model.VzdnCredentials;
import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.model.VzdnMenus;
import com.netpace.vzdn.model.VzdnNewsLetterOptOutRecipients;
import com.netpace.vzdn.model.VzdnNotifAdHocRecipients;
import com.netpace.vzdn.model.VzdnSubMenus;
import com.netpace.vzdn.model.VzdnSysPrivileges;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnTypes;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.service.UsersService;

/**
 * @author Ikramullah Khan Implementing class for the UserService.
 */
public class UsersServiceImpl implements UsersService {

	private IUserDAO<VzdnUsers, Integer> usersDao;
	private ISysRoleDao rolesDao;
	private IPrivilegesDAO<VzdnSysPrivileges, Integer> privilegesDao;
	private ISubMenuDAO<VzdnSubMenus, Integer> subMenuDao;
	private INewsLetterOptOutDao<VzdnNewsLetterOptOutRecipients, Integer> newsLetterOptoutDao;

	public ISubMenuDAO<VzdnSubMenus, Integer> getSubMenuDao() {
		return subMenuDao;
	}

	public void setSubMenuDao(ISubMenuDAO<VzdnSubMenus, Integer> subMenuDao) {
		this.subMenuDao = subMenuDao;
	}

	public List<VzdnUsers> getAllUsers() {
		return usersDao.findAll();
	}

	// must return VzdnUsers
	public void createNewUser(VzdnUsers user) {
		Transaction transaction = null;
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			usersDao.save(user);
			session.flush();
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if(null!=transaction)
				transaction.rollback();
		}
		finally{
			HibernateSessionFactory.closeSession();
		}		

	}

	// must return VzdnUsers
	public void updateUser(VzdnUsers user) {
		Transaction transaction = null;
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			usersDao.update(user);
			session.flush();
			transaction.commit();			
		} catch (Exception e) {
			e.printStackTrace();
			if(null!=transaction)
				transaction.rollback();
		}
		finally{
			HibernateSessionFactory.closeSession();
		}	

	}

	// must return VzdnUsers
	public void removeUser(VzdnUsers user) {
		Transaction transaction = null;
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			usersDao.delete(user);
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if(null!=transaction)
				transaction.rollback();
		}
		finally{
			HibernateSessionFactory.closeSession();
		}	
	}
	
	public SortedSet<MyNotifications> getUserNotifications(VzdnUsers user, VzdnEventNotifications searchCriteria) throws Exception{
		List<VzdnSysRoles> userRoles = getUserRoles(user.getUserId());		
		List<VzdnEventNotifications> userOptOutNotifications = usersDao.getUserNotifications(user.getUserId(), searchCriteria);
		
		SortedSet<MyNotifications> myNotifications = new TreeSet<MyNotifications>();
		
		for(VzdnSysRoles role : userRoles){
			List<VzdnEventNotifications> notifications = rolesDao.getNotificationOnRole(role);
			for(VzdnEventNotifications notification : notifications){
				MyNotifications myNotification = new MyNotifications();
				myNotification.setNotification(notification);			
				Boolean isOptOut = isNotificationPresent(userOptOutNotifications, notification); 
				myNotification.setIsOptOut(isOptOut);
				
				//filter out those notifications that the user likes to see based on his search criteria
				if(shouldAdd(searchCriteria, myNotification.getNotification()))
					myNotifications.add(myNotification);
				
				
			}
		}
		return myNotifications;
	}
	
	private boolean shouldAdd(VzdnEventNotifications searchCriteria, VzdnEventNotifications myNotification){
		String titleToSearch = searchCriteria.getNotificationTitle();
		String titlePresent = myNotification.getNotificationTitle();
		Integer eventIdPresent = myNotification.getEvent().getEventId();
		
		Integer eventIdToSearch = null;
		
		if(null == searchCriteria)
			return true;
		
		if(null != searchCriteria.getEvent())
			eventIdToSearch = searchCriteria.getEvent().getEventId();
		
		if((null == titleToSearch || "".equals(titleToSearch)) && (null == eventIdToSearch || -1 == eventIdToSearch))
			return true;
		
		
		if((null == titleToSearch || "".equals(titleToSearch)) && (null != eventIdToSearch || -1 != eventIdToSearch)){
			if(eventIdToSearch.equals(eventIdPresent))
				return true;
		}
							
		if((null != titleToSearch || !"".equals(titleToSearch)) && (null == eventIdToSearch || -1 == eventIdToSearch)){
				if(titlePresent.indexOf(titleToSearch) !=-1)
					return true;
		}
		
		if((null != titleToSearch || !"".equals(titleToSearch)) && (null != eventIdToSearch || -1 != eventIdToSearch)){
			if(titlePresent.indexOf(titleToSearch) !=-1 && eventIdToSearch.equals(eventIdPresent))
				return true;
		}		
		return false;
	}


	private Boolean isNotificationPresent(List<VzdnEventNotifications> userNotificaitons, VzdnEventNotifications notification){		
		for(VzdnEventNotifications notif : userNotificaitons){
			if(notif.getNotificationId().equals(notification.getNotificationId())){
				return true;
			}				
		}
		return false;
	}

	
	public List<VzdnUsers> search(Searchable criteria){
		
		if(null !=criteria){
			if(criteria.getSearchableId() == -1){
				return removeHiddenUsers(usersDao.findAll());
			}
			else			
			if(criteria.getSearchableId() == 1){
				return removeHiddenUsers(usersDao.getUserOnUserTypeName(criteria.getContentToSearch()));
			}
			else
			if(criteria.getSearchableId() == 2){
				return removeHiddenUsers(usersDao.getAllUsersOnUserName(criteria.getContentToSearch()));				
			}
			else
			if(criteria.getSearchableId() == 3){
				return removeHiddenUsers(usersDao.getUserOnFirstName(criteria.getContentToSearch()));
			}
			else
			if(criteria.getSearchableId() == 4){
				return removeHiddenUsers(usersDao.getUserLastName(criteria.getContentToSearch()));
			}
			else
				return removeHiddenUsers(usersDao.findAll());				
		}
		else
			return removeHiddenUsers(usersDao.findAll());			
		
	}
	
	private List<VzdnUsers> removeHiddenUsers(List<VzdnUsers> totalUsers){
		List<String> hiddenUserNames = getHiddenUsers();
		List<VzdnUsers> filteredUsers = new ArrayList<VzdnUsers>();
		
		
		for(VzdnUsers user : totalUsers){
			boolean userMatched = false;
			for(String userName : hiddenUserNames){	
				if(userName!=null && userName.equals(user.getUserName())){
					userMatched = true;
					break;
				}
			}
			if(!userMatched){
				filteredUsers.add(user);
			}
		}
		return filteredUsers;
	}
	
	private List<String> getHiddenUsers(){
		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResources");
		String hiddenUsers = rb.getString("hidden.users");
		List<String> hiddenUsersList = new ArrayList<String>();
		StringTokenizer tokenString = new StringTokenizer(hiddenUsers, ",");
		while (tokenString.hasMoreTokens()) {
			String userName = tokenString.nextToken();
			//VzdnTypes searchableType = usersDao.getUserByUserName(userName) .findById(typeId);
			hiddenUsersList.add(userName);
		}		
		return hiddenUsersList;
	}
	
	public void updateMyNotifications(Set<VzdnEventNotifications> notifications, VzdnUsers loggedInUser){
		
		Transaction transaction = null;
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			
			optOutUserFromAllNotifications(loggedInUser);		
			loggedInUser.setNotifications(notifications);		
			usersDao.update(loggedInUser);

			session.flush();
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
			if (null != transaction)
				transaction.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}

	}
	
	
	public void updateMyNotifications(Set<VzdnEventNotifications> notifications, VzdnUsers loggedInUser, Boolean optOutNewsLetter){
		
		Transaction transaction = null;
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			
			optOutUserFromAllNotifications(loggedInUser);		
			loggedInUser.setNotifications(notifications);		
			usersDao.update(loggedInUser);
			
			VzdnNewsLetterOptOutRecipients optOutObject = 
				new VzdnNewsLetterOptOutRecipients(loggedInUser, loggedInUser.getUserName(),new Timestamp(new java.util.Date().getTime()));
			
			
			VzdnNewsLetterOptOutRecipients statusObject = newsLetterOptoutDao.getRecordByUserId(loggedInUser.getUserId());
			
			if(optOutNewsLetter && null == statusObject)
				newsLetterOptoutDao.save(optOutObject);
			else
			if(optOutNewsLetter == false && null != statusObject)
			{				
				newsLetterOptoutDao.delete(statusObject);
			}

			session.flush();
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
			if (null != transaction)
				transaction.rollback();
		} finally {
			HibernateSessionFactory.closeSession();
		}

	}

	
	
	public void optOutUserFromAllNotifications(VzdnUsers loggedInUser){
		loggedInUser.setNotifications(null);
		usersDao.update(loggedInUser);
	}

	public Boolean newsLetterStatus(VzdnUsers user) throws Exception{
		
		try
		{			
			VzdnNewsLetterOptOutRecipients status = newsLetterOptoutDao.getRecordByUserId(user.getUserId());
			if(null == status)
				return false;
			else
				return true;			
		}
		catch(Exception e){
			throw e;
		}
	}
	
	public VzdnUsers getUserById(int userId) {
		return (VzdnUsers) usersDao.findById(new Integer(userId));
	}

	public IUserDAO<VzdnUsers, Integer> getUsersDao() {
		return usersDao;
	}

	public void setUsersDao(IUserDAO<VzdnUsers, Integer> usersDao) {
		this.usersDao = usersDao;
	}

	public boolean userExists(String userName) throws UserNotFoundException {
		if (null == getUserByUserName(userName)) {
			return false;
		}
		return true;
	}

	public VzdnUsers getUserByUserName(String userName)
			throws UserNotFoundException {
		return usersDao.getUserByUserName(userName);
	}
	
	public List<VzdnSysRoles> getUserRoles(int userId) throws RoleNotFoundException{
		return usersDao.getUserRoles(userId);
	}
	
	public ISysRoleDao getRolesDao() {
		return rolesDao;
	}

	public void setRolesDao(ISysRoleDao rolesDao) {
		this.rolesDao = rolesDao;
	}

	public IPrivilegesDAO<VzdnSysPrivileges, Integer> getPrivilegesDao() {
		return privilegesDao;
	}

	public void setPrivilegesDao(
			IPrivilegesDAO<VzdnSysPrivileges, Integer> privilegesDao) {
		this.privilegesDao = privilegesDao;
	}
	
	public List<VzdnTypes> getAllUserTypes(){
		return usersDao.getAllUserTypes();
	}
	
	public List<VzdnUsers> search(VzdnUsers criteria){
		return usersDao.search(criteria);
	}

	public INewsLetterOptOutDao<VzdnNewsLetterOptOutRecipients, Integer> getNewsLetterOptoutDao() {
		return newsLetterOptoutDao;
	}

	public void setNewsLetterOptoutDao(
			INewsLetterOptOutDao<VzdnNewsLetterOptOutRecipients, Integer> newsLetterOptoutDao) {
		this.newsLetterOptoutDao = newsLetterOptoutDao;
	}	
}


