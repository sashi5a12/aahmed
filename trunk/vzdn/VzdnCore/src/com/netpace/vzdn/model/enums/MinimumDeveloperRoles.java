package com.netpace.vzdn.model.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum MinimumDeveloperRoles {
	
	
	Forum_User("Forum User"),  
	Blog_User("Blog User");
	  
	
	private static final List vzdnDeveloperList = new ArrayList();

	static {
		for(MinimumDeveloperRoles s : EnumSet.allOf(MinimumDeveloperRoles.class))
			vzdnDeveloperList.add(s.getValue());
	   	}

	
	private String value;

	private MinimumDeveloperRoles(String value) {
	      
	      this.value=value;
	}

	
	public String getValue() {
		return value;
	}


	public static List getVzdnDeveloperList() {
		return vzdnDeveloperList;
	}


	

}
