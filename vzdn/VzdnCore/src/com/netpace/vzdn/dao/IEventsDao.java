package com.netpace.vzdn.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.netpace.vzdn.dao.impl.GenericDAO;
import com.netpace.vzdn.exceptions.PrivilegesNotFoundException;
import com.netpace.vzdn.exceptions.RoleNotFoundException;
import com.netpace.vzdn.exceptions.UserNotFoundException;
import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.model.VzdnEvents;
import com.netpace.vzdn.model.VzdnSysPrivileges;
import com.netpace.vzdn.model.VzdnSysRoles;

public interface IEventsDao<T,PK extends Serializable> extends IGenericDAO<T, PK>{
	public List<VzdnEventNotifications> getNotificationsOnEvent(Long eventId) throws Exception;
}
