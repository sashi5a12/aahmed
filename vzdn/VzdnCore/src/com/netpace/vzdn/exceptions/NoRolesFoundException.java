package com.netpace.vzdn.exceptions;

public class NoRolesFoundException extends Exception{
	private String reason;
	public NoRolesFoundException(String reason){
		this.reason = reason;
	}
	public String toString(){
		return reason;
	}
}
