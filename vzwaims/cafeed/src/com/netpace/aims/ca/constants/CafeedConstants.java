package com.netpace.aims.ca.constants;

/**  
* @author  Ikramullah Khan
* @version 0.1, 10/06/2009
* @since   JDK1.6
*/

public interface CafeedConstants {
	public static final String CONTRACTS_ACCEPTED_STATUS = "ACCEPTED";
	public static final long CONTRACTS_VCAST_APP = 172;
	public static final long CONTRACTS_VCAST_APP_MORE = 173;
	public static final long CONTRACTS_PLATFORM_JAVA = 44;
	public static final int TOP_ONE = 1;
	public static final int TIME_FORWARD_DIRECTION = 1;
	public static final int TIME_BACKWARD_DIRECTION = 2;
	public static final int SORT_ORDER_1 = 1;
	public static final int SORT_ORDER_2 = 2;
	public static final int SORT_ORDER_3 = 3;
	public static final int SORT_ORDER_4 = 4;
	public static final int SORT_ORDER_5 = 5;
	public static final String TIME_FORMAT = "MM/dd/yyyy hh:mm:ss";
	public static final String RETRIEVE_TIME_FORMAT = "MM/dd/yyyy";
	public static final String FEED_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";
	public static final String FILE_NAME_TIME_FORMAT = "MMddyyyy_hhmmss";
	public static final String AUDIT_ACTION_INSERT = "INS";
	public static final String AUDIT_ACTION_UPDATE = "UPD";
	public static final String AUDIT_ACTION_DELETE = "DEL";
	public static final String INVALID_TIME_MESSAGE= "Start and end times cannot be empty and must follow the format ["+ TIME_FORMAT + "]";
	public static final String ARGUMENTS_USAGE_MESSAGE= "\nUsage::\n\n<Start Time> <End Time> <Filename*> <dontsend*(yes|no)> <scp server*> <scp username*> <scp password*>\nNote:: * means optional argument.";
	public static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[_A-Za-z0-9-]+)";
	public static final String EMAIL_FROM_ADDRESS = "vzwtech@netpace.com";
	public static final String EMAIL_TITLE = "Developer Feed Not Successfully Sent";
	public static final String EMAIL_BODY = "Due to some issue, the developer feed could not be sent successfully.";
	public static final String EMAIL_BODY_HEADER = "The developer feed could not be sent. Following is the exception trace:\n\n";
	public static final String EMAIL_BODY_FOOTER = "Cafeed Team";
	public static final String DEFAULT_SEPARATOR = "<|>";	
}
