package com.netpace.vzdn.exceptions;

public class CredentialsNotFoundException extends Exception{
	public static final long serialVersionUID=1;
	public String reason;
	
	public CredentialsNotFoundException(String reason){
		this.reason=reason;
	}
}
