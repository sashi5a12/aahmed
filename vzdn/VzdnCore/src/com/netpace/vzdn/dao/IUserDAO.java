package com.netpace.vzdn.dao;

import java.io.Serializable;

import org.hibernate.Session;

import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.model.Searchable;
import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnTypes;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.exceptions.RoleNotFoundException;
import com.netpace.vzdn.exceptions.UserNotFoundException;
import java.util.List;

/**
 * @author Ikramullah Khan
 * IUserDAO represents any 'User' specific contract.
 * Add all UserDAO specific methods to this interface
 */
public interface IUserDAO<T,PK extends Serializable> extends IGenericDAO<T, PK>{	
	public VzdnUsers getUserByUserName(String userName) throws UserNotFoundException;
	public List<VzdnSysRoles> getUserRoles(int userId) throws RoleNotFoundException;
	public List<VzdnTypes> getAllUserTypes();
	
	public List<VzdnUsers> search(VzdnUsers criteria);
	
	public List<VzdnUsers> getAllUsersOnUserName(String searchOn);
	public List<VzdnUsers> getUserOnUserTypeName(String searchOn);	
	public List<VzdnUsers> getUserOnFirstName(String searchOn);	
	public List<VzdnUsers>getUserLastName(String searchOn);	
	public List<VzdnEventNotifications> getUserNotifications(Integer userId, VzdnEventNotifications searchCriteria);

}
