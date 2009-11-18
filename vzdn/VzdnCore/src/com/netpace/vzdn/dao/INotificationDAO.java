package com.netpace.vzdn.dao;

import java.io.Serializable;

import org.hibernate.Session;

import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.model.VzdnEmailMessages;
import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.model.VzdnEvents;
import com.netpace.vzdn.model.VzdnNotifAdHocRecipients;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnTypes;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.exceptions.RoleNotFoundException;
import com.netpace.vzdn.exceptions.UserNotFoundException;
import java.util.List;

/**
 * @author Ikramullah Khan
 * IUserDAO represents any 'Notification' specific contract.
 * Add all NotificationDAO specific methods to this interface
 */
public interface INotificationDAO<T,PK extends Serializable> extends IGenericDAO<T, PK>{
	public List<VzdnSysRoles> getRoleIdsOnNotifId(Integer notifId) throws Exception;	
	public List<VzdnNotifAdHocRecipients> getAdHocRecipientsOnNotifId(Integer id) throws Exception;
	public VzdnEvents getEventOnNotificationId(Integer id) throws Exception;
	
	public VzdnEmailMessages getEmailMessdageOnNotificationId(Integer notificationId) throws Exception;
	
	public List<VzdnEventNotifications> search(VzdnEventNotifications criteria);
	public VzdnEventNotifications getNotificationByEmailId(Integer emailId) throws Exception;
	public List<VzdnUsers> getOptOutUsersOnNotificationId(Integer notificationId) throws Exception;
	
}
