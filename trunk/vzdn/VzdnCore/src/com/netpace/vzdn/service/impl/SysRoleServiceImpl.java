package com.netpace.vzdn.service.impl;

import java.util.List;

import org.hibernate.Session;

import com.netpace.vzdn.dao.IGenericDAO;
import com.netpace.vzdn.dao.ISysRoleDao;
import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.exceptions.RoleNotFoundException;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.service.ISysRoleService;

public class SysRoleServiceImpl implements ISysRoleService{

	
	public List<VzdnSysRoles> getRolesOnType(List<Integer> typeIds) {
		//Session session = HibernateSessionFactory.getSession();
		
		List<VzdnSysRoles> roles =  roleDao.getRolesOnType(typeIds);
		//HibernateSessionFactory.closeSession();
		return roles;
	}
	

	private ISysRoleDao roleDao;


	public ISysRoleDao getRoleDao() {
		return roleDao;
	}

	public VzdnSysRoles getRolesOnId(Integer id){
		
		VzdnSysRoles role =  roleDao.getRolesOnId(id);
		//HibernateSessionFactory.closeSession();
		return role;
		
	}
	
	public VzdnSysRoles getRolesOnMappingId(Integer id) throws Exception{		
		VzdnSysRoles role =  roleDao.getRolesOnMappingId(id);
		//HibernateSessionFactory.closeSession();
		return role;
		
	}
	
	public List<VzdnSysRoles> getAll(){
		return roleDao.findAll();
	}
	
	public List<VzdnSysRoles> getMinimumUserRoles(List<Integer> minimumRoles){
		return roleDao.getMinimumUserRoles(minimumRoles);
		
	}

	public void setRoleDao(ISysRoleDao roleDao) {
		this.roleDao = roleDao;
		
	}
	
	public List<VzdnSysRoles> getNonHiddenRoles() throws Exception{
		return this.roleDao.getNonHiddenRoles();
	}
}
