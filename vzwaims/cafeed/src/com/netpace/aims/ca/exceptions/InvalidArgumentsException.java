package com.netpace.aims.ca.exceptions;

/**  
* @author  Ikramullah Khan
* @version 0.1, 10/06/2009
* @since   JDK1.6
*/

public class InvalidArgumentsException extends Exception{
	public static final long serialVersionUID = -1L;
	
	public InvalidArgumentsException(String message){
		super(message);
	}
}
