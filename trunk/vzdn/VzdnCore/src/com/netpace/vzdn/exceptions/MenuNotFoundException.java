package com.netpace.vzdn.exceptions;

public class MenuNotFoundException extends Exception{
	public static final long serialVersionUID=1;
	public String reason;
	
	public MenuNotFoundException(String reason){
		this.reason=reason;
	}
}
