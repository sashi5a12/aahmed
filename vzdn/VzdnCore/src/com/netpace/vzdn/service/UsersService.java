package com.netpace.vzdn.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import com.netpace.vzdn.exceptions.RoleNotFoundException;
import com.netpace.vzdn.exceptions.UserNotFoundException;
import com.netpace.vzdn.model.MyNotifications;
import com.netpace.vzdn.model.Searchable;
import com.netpace.vzdn.model.VzdnCredentials;
import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.model.VzdnEvents;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.model.VzdnTypes;
import com.netpace.vzdn.model.VzdnSysRoles;



/**
 * @author Ikramullah Khan
 * Service interface related to Users.
 */
public interface UsersService {
	public List<VzdnUsers> getAllUsers();
	
	//must return VzdnUsers
	public void createNewUser(VzdnUsers user);
	
	//must return VzdnUsers
	public void updateUser(VzdnUsers user);
	
	//must return VzdnUsers
	public void removeUser(VzdnUsers user);

	public VzdnUsers getUserById(int userId);
	
	public boolean userExists(String userName) throws UserNotFoundException;
	
	public VzdnUsers getUserByUserName(String userName) throws UserNotFoundException;
	
	public List<VzdnSysRoles> getUserRoles(int userId)  throws RoleNotFoundException;	
	
	public List<VzdnTypes> getAllUserTypes();
	
	public List<VzdnUsers> search(Searchable criteria);
	public List<VzdnUsers> search(VzdnUsers criteria);
	
	public SortedSet<MyNotifications> getUserNotifications(VzdnUsers user, VzdnEventNotifications searchCriteria) throws Exception;
	public Boolean newsLetterStatus(VzdnUsers user) throws Exception;
	
	public void updateMyNotifications(Set<VzdnEventNotifications> notifications, VzdnUsers loggedInUser);
	public void updateMyNotifications(Set<VzdnEventNotifications> notifications, VzdnUsers loggedInUser, Boolean optOutNewsLetter);
	
	public void optOutUserFromAllNotifications(VzdnUsers loggedInUser);	
}
