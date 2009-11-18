package com.netpace.vzdn.dao.impl;

import com.netpace.vzdn.dao.ITypesDAO;
import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.exceptions.RoleNotFoundException;
import com.netpace.vzdn.exceptions.UserNotFoundException;
import com.netpace.vzdn.global.VzdnConstants;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnTypes;
import com.netpace.vzdn.model.VzdnUsers;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * @author Ikramullah Khan UserDAO represents the user object genericly
 *         extending the GeneriDAO. This class also implements IUserDAO
 *         interface. Any 'User' specific methods must be defined in the
 *         IUserDAO interace.
 */

public class TypesDAO extends GenericDAO<VzdnTypes, Integer> implements
		ITypesDAO<VzdnTypes, Integer> {

	private static Logger log = Logger.getLogger(TypesDAO.class);

	public TypesDAO() {
		super(VzdnTypes.class);
	}

}