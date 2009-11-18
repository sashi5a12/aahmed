package com.netpace.vzdn.exceptions;

public class NotLoggedInException extends Exception{
	public static final long serialVersionUID=1;
	public String reason;
	
	public NotLoggedInException(String reason){
		this.reason=reason;
	}
}
