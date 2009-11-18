/*
 * Created on May 2, 2009 by mahmood
 * Copyright (c) 2009 Netpace Inc.  All rights reserved. 
 * You shall use it and distribute only in accordance with the terms of the License Agreement.
 */

package com.netpace.aims.ws.appinfo.client;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.netpace.aims.ws.appinfo.ApplicationInfoRequest;
import com.netpace.aims.ws.appinfo.ApplicationInfoResponse;
import com.netpace.aims.ws.appinfo.ApplicationInfoSoap;
import com.netpace.aims.ws.appinfo.ErrorItemType;
import com.netpace.aims.ws.appinfo.ErrorSoapOut;


public final class Client {

    private Client() {
    }

    public static void main(String args[]) {
        ApplicationContext context 
        	= new FileSystemXmlApplicationContext(args[0]);

        ApplicationInfoSoap client = (ApplicationInfoSoap)context.getBean("client");
        
        try{
			//webservice request
	        ApplicationInfoRequest request = new ApplicationInfoRequest();
	        request.setApplicationKeyword(args[1]);
	        System.out.println("REQUEST =\n"+request);
        
	        //webservice call
	        ApplicationInfoResponse response = client.getApplicationInfo(request);
	        	        			
			//webservice response.
	        System.out.println("RESPONSE =\n"+response);
	        
        }
        catch(ErrorSoapOut e) {
        	ErrorItemType err = e.getFaultInfo().getErrorItem().get(0);
        	String errMsg = err.getErrorField()+" - "+err.getErrorText();
        	System.out.println(errMsg);	
        	System.exit(-1);
        }
    	System.exit(0);
    }
}
