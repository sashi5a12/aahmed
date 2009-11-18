package com.netpace.vzdn.exceptions;

public class PrivilegesNotFoundException extends Exception{
	public static final long serialVersionUID=1;
	public String reason;
	
	public PrivilegesNotFoundException(String reason){
		this.reason=reason;
	}
}
