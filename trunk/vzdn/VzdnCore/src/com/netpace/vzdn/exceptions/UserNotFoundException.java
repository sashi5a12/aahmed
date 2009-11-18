package com.netpace.vzdn.exceptions;

public class UserNotFoundException extends Exception{
	private String message;
	public UserNotFoundException(String message){
		this.message = message;
	}
	public String toString(){
		return message;
	}
}
