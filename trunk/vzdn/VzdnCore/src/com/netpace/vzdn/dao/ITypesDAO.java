package com.netpace.vzdn.dao;

import java.io.Serializable;

import org.hibernate.Session;

import com.netpace.vzdn.db.HibernateSessionFactory;
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
public interface ITypesDAO<T,PK extends Serializable> extends IGenericDAO<T, PK>{	
}
