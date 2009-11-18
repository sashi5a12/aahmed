package com.netpace.aims.ws.appinfo;


import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.log4j.Logger;
import org.apache.ws.security.WSPasswordCallback;

import com.netpace.aims.util.ZonwsEncoder;
import com.netpace.aims.util.ZonwsProperties;

public class ServerPasswordCallback implements CallbackHandler {

	private static Logger log = Logger.getLogger(ApplicationInfoSoapImpl.class.getName());
	
	private static final String PROP_USERNAME = "com.netpace.aims.ws.appinfo.username";
	private static final String PROP_PASSWORD = "com.netpace.aims.ws.appinfo.password";
	
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

        if(log.isInfoEnabled()){
        	log.info("Enter handle() callbacks="+callbacks);
        }
    	WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
        String username = ZonwsProperties.getInstance().getProperty(PROP_USERNAME);
        String password = ZonwsProperties.getInstance().getProperty(PROP_PASSWORD);
        if(log.isDebugEnabled()){
        	log.debug("Read from property username = "+username+", password = "+password);
        }
        try {
			password = ZonwsEncoder.decode(password);
		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug("##############################################");
	        	log.debug("Failed to decode password = "+password, e);
	        	log.debug("##############################################");
			}
			e.printStackTrace();
		}
        if (pc.getIdentifier().equals(username) ) {
        	if(log.isDebugEnabled()){
            	log.debug("Matched username = "+username);
            }
        	//pc.setPassword("c!entP@$$w0rd");
            pc.setPassword(password);
         }
    }

}