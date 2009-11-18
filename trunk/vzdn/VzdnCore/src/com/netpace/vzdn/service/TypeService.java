package com.netpace.vzdn.service;

import java.util.List;
import java.util.Set;

import com.netpace.vzdn.exceptions.RoleNotFoundException;
import com.netpace.vzdn.exceptions.UserNotFoundException;
import com.netpace.vzdn.model.VzdnCredentials;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.model.VzdnTypes;
import com.netpace.vzdn.model.VzdnSysRoles;



/**
 * @author Ikramullah Khan
 * Service interface related to Users.
 */
public interface TypeService {
	public VzdnTypes getTypeById(Integer id);
	public List<VzdnTypes> getSearchableTypes();
}
