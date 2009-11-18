package com.netpace.vzdn.dao;

import java.io.Serializable;

import org.hibernate.Session;

import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.model.VzdnSubMenus;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.exceptions.RoleNotFoundException;
import com.netpace.vzdn.exceptions.SubMenuNotFoundException;
import com.netpace.vzdn.exceptions.UserNotFoundException;
import java.util.List;

/**
 * @author Ikramullah Khan
 * 
 */
public interface IPrivilegesDAO<T,PK extends Serializable> extends IGenericDAO<T, PK>{
	public VzdnSubMenus getSubMenu(Integer privilegeId)  throws SubMenuNotFoundException;
}
