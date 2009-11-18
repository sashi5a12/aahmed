package com.netpace.aims.ca.exceptions;

/**  
* @author  Ikramullah Khan
* @version 0.1, 10/06/2009
* @since   JDK1.6
*/

public class DBIssueException extends Exception{
	public static final long serialVersionUID = -2L;
	
	public DBIssueException(String message){
		super(message);
	}
}
