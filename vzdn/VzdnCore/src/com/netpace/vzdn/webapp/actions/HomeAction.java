package com.netpace.vzdn.webapp.actions;

import org.apache.log4j.Logger;
import com.netpace.vzdn.model.VzdnUsers;

import com.opensymphony.xwork2.Preparable;

/**
 * @author Ikramullah Khan Action class dealing with User login and session
 *         managemnt tasks.
 */
public class HomeAction extends BaseAction implements Preparable{
	
	private static Logger log = Logger.getLogger(HomeAction.class);
	private VzdnUsers loggedInUser;
	
	public void prepare(){
		//loggedInUser = (VzdnUsers)getSession().getAttribute(VzdnConstants.LOGGED_IN_USER);
	}
	
	public String execute(){
		//The following lines are committed due to the new strategy of Signout Functionality.
		/*
		if(null == loggedInUser)
			return INPUT;
		else
		*/
			return SUCCESS;
	}
}
