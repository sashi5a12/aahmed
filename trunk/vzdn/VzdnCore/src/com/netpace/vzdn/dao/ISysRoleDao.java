package com.netpace.vzdn.dao;

import java.io.Serializable;
import java.util.List;

import com.netpace.vzdn.dao.impl.GenericDAO;
import com.netpace.vzdn.exceptions.PrivilegesNotFoundException;
import com.netpace.vzdn.exceptions.RoleNotFoundException;
import com.netpace.vzdn.exceptions.UserNotFoundException;
import com.netpace.vzdn.model.VzdnEventNotifications;
import com.netpace.vzdn.model.VzdnSysPrivileges;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnUsers;

public interface ISysRoleDao{

	public List<VzdnSysRoles> getRolesOnType(List<Integer> typeIds);
	public VzdnSysRoles getRolesOnId(Integer id);
	public VzdnSysRoles getRolesOnMappingId(Integer id) throws Exception;
	public List<VzdnSysRoles> getMinimumUserRoles(List<Integer> minimumRoles);
	public List<VzdnSysPrivileges> getPrivilegesOnRoleId(Integer roleId)  throws PrivilegesNotFoundException;
	public List<VzdnSysRoles> findAll();
	public List<VzdnEventNotifications> getNotificationOnRole(VzdnSysRoles role) throws Exception;
	public List<VzdnSysRoles> getNonHiddenRoles() throws Exception;
	public List<VzdnUsers> getUsersInRole(Integer roleId) throws Exception;
	
	
}
