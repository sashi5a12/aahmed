package com.netpace.vzdn.dao;

import java.io.Serializable;

import org.hibernate.Session;

import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.model.VzdnMenus;
import com.netpace.vzdn.model.VzdnSubMenus;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.exceptions.MenuNotFoundException;
import com.netpace.vzdn.exceptions.RoleNotFoundException;
import com.netpace.vzdn.exceptions.UserNotFoundException;
import java.util.List;

/**
 * @author Ikramullah Khan
 * 
 */
public interface ISubMenuDAO<T,PK extends Serializable> extends IGenericDAO<T, PK>{
	public VzdnMenus getMainMenu(Integer privilegeId)throws MenuNotFoundException;
}
