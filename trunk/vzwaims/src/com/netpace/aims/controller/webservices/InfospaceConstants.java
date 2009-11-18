package com.netpace.aims.controller.webservices;

import java.text.*;

/**
 * This class has static final constants which will be refered in the webservices application.
 *
 * @author Rizwan Qazi
 */
public class InfospaceConstants
{

	// 	Infospace SOAP HTTP Connection Timeout ( in milli seconds)
	public static final int INFOSPACE_WS_TIMEOUT = 15000; //MILLISECONDS (15 * 1000) 15 SECS
	
    //	Infospace Web Service URL
    public static final String INFOSPACE_WS_URL = "http://vzwdemoappmgr.infospace.com/vzwhandler/default.asmx";
    
    //  Infospace Web Service UserName
    public static final String INFOSPACE_WS_USER_NAME = "NetPace";

    // 	Infospace Web Service Password
    public static final String INFOSPACE_WS_PASS_WORD = "2bOr!2b";
    	
    // 	Infospace End Point URL
    public static final String INFOSPACE_WS_END_POINT_URL = "http://www.infospace.com/vzw/import/SubmitDocument";
    
	// 	Infospace Action URL
    public static final String INFOSPACE_ACTION_URL = "http://www.infospace.com/vzw/import";
}
