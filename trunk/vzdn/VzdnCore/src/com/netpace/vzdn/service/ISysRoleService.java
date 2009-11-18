package com.netpace.vzdn.service;

import java.util.List;

import com.netpace.vzdn.exceptions.RoleNotFoundException;
import com.netpace.vzdn.model.VzdnSysRoles;

public interface ISysRoleService {
	
	public List<VzdnSysRoles> getRolesOnType(List<Integer> typeIds);
	public VzdnSysRoles getRolesOnId(Integer id);
	public VzdnSysRoles getRolesOnMappingId(Integer id) throws Exception;
	public List<VzdnSysRoles> getMinimumUserRoles(List<Integer> minimumRoles);	
	public List<VzdnSysRoles> getAll();
	public List<VzdnSysRoles> getNonHiddenRoles() throws Exception;
}
