package com.netpace.vzdn.dao.impl;
import com.netpace.vzdn.dao.IPrivilegesDAO;

import com.netpace.vzdn.dao.IUserDAO;
import com.netpace.vzdn.db.HibernateSessionFactory;
import com.netpace.vzdn.exceptions.MenuNotFoundException;
import com.netpace.vzdn.exceptions.RoleNotFoundException;
import com.netpace.vzdn.exceptions.UserNotFoundException;
import com.netpace.vzdn.model.VzdnMenus;
import com.netpace.vzdn.model.VzdnSubMenus;
import com.netpace.vzdn.model.VzdnSysRoles;
import com.netpace.vzdn.model.VzdnUsers;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Query;
import java.util.List;
import java.util.Set;
import com.netpace.vzdn.dao.ISubMenuDAO;
/**
 * @author Ikramullah Khan
 * UserDAO represents the user object genericly extending the GeneriDAO.
 * This class also implements IUserDAO interface. Any 'User' specific methods
 * must be defined in the IUserDAO interace. 
 */

public class SubMenuDAO extends GenericDAO<VzdnSubMenus,Integer> implements ISubMenuDAO<VzdnSubMenus,Integer>{	
	
	private static Logger log = Logger
	.getLogger(SubMenuDAO.class);
	
	public SubMenuDAO(){
		super(VzdnSubMenus.class);
	}
	

	public VzdnMenus getMainMenu(Integer subMenuId) throws MenuNotFoundException{
		Session hibernateSession = null;
    	VzdnUsers object = null;
    	try{            	
    		hibernateSession = HibernateSessionFactory.getSession();
    		Query searchQuery =hibernateSession.createQuery("select m from VzdnMenus m where subMenuId=" + subMenuId);
    	    List<VzdnMenus> menu = searchQuery.list();
    	    if(null==menu || menu.isEmpty()){
    	    	throw new MenuNotFoundException("Main Menu Not found against Sub Menu id" + subMenuId);
    	    }
    	    else
    	    	return menu.get(0);            
           
	     } 
    	catch (MenuNotFoundException re) {
    		log.info("Some problem with finding "+ VzdnUsers.class.getName() +" object");
            throw re;
    	}
    	finally{
    		HibernateSessionFactory.closeSession();
    	}
	}
}