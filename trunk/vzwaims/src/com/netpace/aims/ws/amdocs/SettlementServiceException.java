/*
 * SettlementServiceException.java Created on Sep 29, 2009 by wakram Copyright (c) 2009
 * Netpace Inc. All rights reserved. You shall use it and distribute only in accordance with the
 * terms of the License Agreement.
 */
package com.netpace.aims.ws.amdocs;


import com.netpace.aims.ws.WebServiceException;

/**
 * @author <a href="mailto:wakram@netpace.com">Waseem Akram</a>.
 * $Id: SettlementServiceException.java,v 1.2 2009/10/23 00:12:06 aahmed Exp $ 
 */
public class SettlementServiceException extends WebServiceException {
	/**
	 * @param message
	 * @param resubmitURL
	 */
	public SettlementServiceException(String message , String resubmitURL) {
		super(message, resubmitURL);		
	}

	/**
	 * @param cause
	 * @param resubmitURL
	 */
	public SettlementServiceException(Throwable cause , String resubmitURL) {
		this(null , cause , resubmitURL);
	}

	/**
	 * @param message
	 * @param cause
	 * @param resubmitURL
	 */
	public SettlementServiceException(String message, Throwable cause, String resubmitURL) {
		super(message, cause, resubmitURL);		
	}
}
