package com.netpace.vzdn.model.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum VzdnUserTypes {

	Developer("Developer",1 ),  
	Verizon("Verizon",2 ),
	Both("Both",3 );
	  
	private static final Map lookup = new HashMap();

	static {
		for(VzdnUserTypes s : EnumSet.allOf(VzdnUserTypes.class))
			lookup.put(s.getKey(), s.getValue());
	   	}

	private String key;
	private Integer value;

	private VzdnUserTypes(String key,Integer value) {
	      this.key = key;
	      this.value=value;
	}

	public String getKey() {
		return key;
	}

	public Integer getValue() {
		return value;
	}
	   
	   
	public static Integer get(String key) { 
	      return (Integer)lookup.get(key); 
	 }

	public static Map getLookup() {
		return lookup;
	}
	  
	
	
	
}
