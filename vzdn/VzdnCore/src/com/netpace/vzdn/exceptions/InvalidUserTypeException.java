package com.netpace.vzdn.exceptions;

public class InvalidUserTypeException extends Exception{
	public static final long serialVersionUID=1;
	public String reason;
	
	public InvalidUserTypeException(String reason){
		this.reason=reason;
	}
}
