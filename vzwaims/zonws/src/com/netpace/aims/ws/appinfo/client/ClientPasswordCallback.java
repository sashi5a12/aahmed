/*
* Created on May 2, 2009 by mahmood
* Copyright (c) 2009 Netpace Inc.  All rights reserved. 
* You shall use it and distribute only in accordance with the terms of the License Agreement.
*/

package com.netpace.aims.ws.appinfo.client;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.ws.security.WSPasswordCallback;

public class ClientPasswordCallback implements CallbackHandler {

	private String password = "";
	public void setPassword(String passwd){
		password = passwd;
	}

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
        
        // set the password for our message.
        //pc.setPassword("password");
		pc.setPassword(password);
    }

}