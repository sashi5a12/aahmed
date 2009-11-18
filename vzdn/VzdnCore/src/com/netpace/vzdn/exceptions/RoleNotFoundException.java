package com.netpace.vzdn.exceptions;

public class RoleNotFoundException extends Exception{
	private String message;
	public RoleNotFoundException(String message){
		this.message = message;
	}
	public String toString(){
		return message;
	}
}
