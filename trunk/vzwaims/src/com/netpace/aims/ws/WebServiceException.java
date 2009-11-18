/*
 * WebServiceException.java Created on Sep 29, 2009 by wakram Copyright (c) 2009
 * Netpace Inc. All rights reserved. You shall use it and distribute only in accordance with the
 * terms of the License Agreement.
 */
package com.netpace.aims.ws;

/**
 * @author <a href="mailto:wakram@netpace.com">Waseem Akram</a>.
 * $Id: WebServiceException.java,v 1.2 2009/10/23 00:12:06 aahmed Exp $
 */
public class WebServiceException extends Exception {

	private String resubmitURL ;
	
	/**
	 * @param message
	 * @param resubmitURL
	 */
	public WebServiceException(String message , String resubmitURL) {
		super(message);
		this.resubmitURL = resubmitURL ;
	}

	/**
	 * @param cause
	 * @param resubmitURL
	 */
	public WebServiceException(Throwable cause , String resubmitURL) {
		this(null , cause , resubmitURL);
	}

	/**
	 * @param message
	 * @param cause
	 * @param resubmitURL
	 */
	public WebServiceException(String message, Throwable cause, String resubmitURL) {
		super(message, cause);
		this.resubmitURL = resubmitURL ;
	}
	
	/**
	 * @return the resubmitURL
	 */
	public String getResubmitURL() {
		return resubmitURL;
	}
	/**
	 * Returns exception message in standard format with resubmit URL if resubmitURL != null
	 */
    public String toString() {
        String s = super.toString();
        return (resubmitURL != null) ? (s + ": [" + resubmitURL + "]") : s;
    }

}
