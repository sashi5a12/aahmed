package com.netpace.vzdn.model.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum VzdnManagerRoles {

	//DEVELOPER("Developer",1),
	//ADMINISTRATOR("Administrator",2),
	
	Forum_Admin("Forum Admin"),  
	Forum_User("Forum User"),  
	Blog_Admin("Blog Admin"),  
	Blog_User("Blog User");
	  
	//private static final Map lookup = new HashMap();
	private static final List vzdnManagerList = new ArrayList();

	static {
		for(VzdnManagerRoles s : EnumSet.allOf(VzdnManagerRoles.class))
			vzdnManagerList.add(s.getValue());
	   	}

	
	private String value;

	private VzdnManagerRoles(String value) {
	      
	      this.value=value;
	}

	
	public String getValue() {
		return value;
	}


	public static List getVzdnManagerList() {
		return vzdnManagerList;
	}
	   
	   
	

	
}
