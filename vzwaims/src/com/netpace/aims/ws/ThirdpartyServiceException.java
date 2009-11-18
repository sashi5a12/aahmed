package com.netpace.aims.ws;

public class ThirdpartyServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ThirdpartyServiceException(String msg){
		super(msg);
	}

	public ThirdpartyServiceException(Throwable cause){
		super(cause);
	}
	
	public ThirdpartyServiceException(String msg, Throwable cause){
		super(msg, cause);
	}

}
