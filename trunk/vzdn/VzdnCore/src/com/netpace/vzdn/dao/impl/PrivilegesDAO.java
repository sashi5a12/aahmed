package com.netpace.vzdn.dao.impl;
import com.netpace.vzdn.dao.IPrivilegesDAO;
import com.netpace.vzdn.dao.IUserDAO;
import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.exceptions.RoleNotFoundException;
import com.netpace.vzdn.exceptions.SubMenuNotFoundException;
import com.netpace.vzdn.exceptions.UserNotFoundException;
import com.netpace.vzdn.model.VzdnSubMenus;
import com.netpace.vzdn.model.VzdnSysPrivileges;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnUsers;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Query;
import java.util.List;
import java.util.Set;
/**
 * @author Ikramullah Khan
 * UserDAO represents the user object genericly extending the GeneriDAO.
 * This class also implements IUserDAO interface. Any 'User' specific methods
 * must be defined in the IUserDAO interace. 
 */

public class PrivilegesDAO extends GenericDAO<VzdnSysPrivileges,Integer> implements IPrivilegesDAO<VzdnSysPrivileges,Integer>{	
	
	private static Logger log = Logger
	.getLogger(PrivilegesDAO.class);
	
	public PrivilegesDAO(){
		super(VzdnSysPrivileges.class);
	}
	

	public VzdnSubMenus getSubMenu(Integer privilegeId) throws SubMenuNotFoundException{
		Session hibernateSession = null;
    	VzdnUsers object = null;
    	try{            	
    		hibernateSession = HibernateSessionFactory.getSession();
    		Query searchQuery =hibernateSession.createQuery("select subMenu from VzdnSysPrivileges p where privilegeId=" + privilegeId);
    	    List<VzdnSubMenus> subMenu = searchQuery.list();
    	    if(null==subMenu || subMenu.isEmpty()){
    	    	throw new SubMenuNotFoundException("SubMenu Not found against privilege id" + privilegeId);
    	    }
    	    else
    	    	return subMenu.get(0);            
           
	     } 
    	catch (SubMenuNotFoundException re) {
    		log.info("Some problem with finding "+ VzdnUsers.class.getName() +" object");
            throw re;
    	}
    	finally{
    		HibernateSessionFactory.closeSession();
    	}
    	
	}
}