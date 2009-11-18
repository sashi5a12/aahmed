package com.netpace.vzdn.exceptions;

public class PlaceHoldersNotFoundException extends Exception{
	private String message;
	public PlaceHoldersNotFoundException(String message){
		this.message = message;
	}
	public String toString(){
		return message;
	}
}
