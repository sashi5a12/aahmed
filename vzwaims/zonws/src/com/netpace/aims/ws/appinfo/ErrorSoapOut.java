
package com.netpace.aims.ws.appinfo;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.2.1
 * Thu May 07 13:34:55 PDT 2009
 * Generated source version: 2.2.1
 * 
 */

@WebFault(name = "ErrorResponse", targetNamespace = "http://www.vzwdeveloper.com/vds/application/")
public class ErrorSoapOut extends Exception {
    public static final long serialVersionUID = 20090507133455L;
    
    private com.netpace.aims.ws.appinfo.ErrorResponse errorResponse;

    public ErrorSoapOut() {
        super();
    }
    
    public ErrorSoapOut(String message) {
        super(message);
    }
    
    public ErrorSoapOut(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorSoapOut(String message, com.netpace.aims.ws.appinfo.ErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public ErrorSoapOut(String message, com.netpace.aims.ws.appinfo.ErrorResponse errorResponse, Throwable cause) {
        super(message, cause);
        this.errorResponse = errorResponse;
    }

    public com.netpace.aims.ws.appinfo.ErrorResponse getFaultInfo() {
        return this.errorResponse;
    }
}
