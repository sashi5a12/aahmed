/*
 * AllianceServicesException.java Created on Sep 25, 2009 by smughal Copyright (c) 2009
 * Netpace Inc. All rights reserved. You shall use it and distribute only in accordance with the
 * terms of the License Agreement.
 */
package com.netpace.aims.ws.vds;

import com.netpace.aims.ws.WebServiceException;

/**
 * @author <a href="mailto:smughal@netpace.com">Shahzad Mughal</a>.
 * $Id: AllianceServicesException.java,v 1.2 2009/10/23 00:12:06 aahmed Exp $
 * 
 * This class has been updated by Waseem Akram. AllianceServicesException now extends
 * WebServiceException instead of extending Exception
 * updated on 2009/09/29 12:41:42 wakram
 */
public class AllianceServicesException extends WebServiceException {
	/**
	 * @param message
	 * @param resubmitURL
	 */
	public AllianceServicesException(String message , String resubmitURL) {
		super(message, resubmitURL);		
	}

	/**
	 * @param cause
	 * @param resubmitURL
	 */
	public AllianceServicesException(Throwable cause , String resubmitURL) {
		this(null , cause , resubmitURL);
	}

	/**
	 * @param message
	 * @param cause
	 * @param resubmitURL
	 */
	public AllianceServicesException(String message, Throwable cause, String resubmitURL) {
		super(message, cause, resubmitURL);		
	}
}
