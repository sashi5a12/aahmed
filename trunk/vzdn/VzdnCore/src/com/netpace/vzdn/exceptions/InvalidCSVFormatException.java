package com.netpace.vzdn.exceptions;

public class InvalidCSVFormatException extends Exception{
	private String reason;
	public InvalidCSVFormatException(String reason){
		this.reason = reason;
	}
	public String toString(){
		return reason;
	}
}
