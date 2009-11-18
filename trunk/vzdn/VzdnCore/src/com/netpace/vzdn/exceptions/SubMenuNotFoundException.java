package com.netpace.vzdn.exceptions;

public class SubMenuNotFoundException extends Exception{
	public static final long serialVersionUID=1;
	public String reason;
	
	public SubMenuNotFoundException(String reason){
		this.reason=reason;
	}
}
