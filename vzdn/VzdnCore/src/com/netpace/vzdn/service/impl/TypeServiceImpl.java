package com.netpace.vzdn.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.netpace.vzdn.dao.IGenericDAO;
import com.netpace.vzdn.dao.ITypesDAO;
import com.netpace.vzdn.dao.ISubMenuDAO;
import com.netpace.vzdn.dao.IUserDAO;
import com.netpace.vzdn.dao.ISysRoleDao;
import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.exceptions.MenuNotFoundException;
import com.netpace.vzdn.exceptions.PrivilegesNotFoundException;
import com.netpace.vzdn.exceptions.RoleNotFoundException;
import com.netpace.vzdn.exceptions.SubMenuNotFoundException;
import com.netpace.vzdn.exceptions.UserNotFoundException;
import com.netpace.vzdn.global.VzdnConstants;
import com.netpace.vzdn.model.VzdnCredentials;
import com.netpace.vzdn.model.VzdnMenus;
import com.netpace.vzdn.model.VzdnSubMenus;
import com.netpace.vzdn.model.VzdnSysPrivileges;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnTypes;
import com.netpace.vzdn.model.VzdnUsers;
import com.netpace.vzdn.service.TypeService;

/**
 * @author Ikramullah Khan Implementing class for the UserService.
 */
public class TypeServiceImpl implements TypeService {

	private ITypesDAO<VzdnTypes, Integer> typesDao;
	
	public VzdnTypes getTypeById(Integer id){
		return typesDao.findById(id);
	}

	public ITypesDAO<VzdnTypes, Integer> getTypesDao() {
		return typesDao;
	}

	public void setTypesDao(ITypesDAO<VzdnTypes, Integer> typesDao) {
		this.typesDao = typesDao;
	}
	
	public List<VzdnTypes> getSearchableTypes(){
		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResources");
		String searchableTypes = rb.getString("searchable.types");
		List<VzdnTypes> searchableTypesList = new ArrayList<VzdnTypes>();
		StringTokenizer tokenString = new StringTokenizer(searchableTypes, ",");
		while (tokenString.hasMoreTokens()) {
			int typeId = Integer.valueOf(tokenString.nextToken());
			VzdnTypes searchableType = typesDao.findById(typeId);
			searchableTypesList.add(searchableType);
		}		
		return searchableTypesList;
	}
}
