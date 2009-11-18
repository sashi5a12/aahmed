
package com.netpace.aims.ws.appinfo;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.netpace.aims.ws.appinfo package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.netpace.aims.ws.appinfo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ErrorResponse }
     * 
     */
    public ErrorResponse createErrorResponse() {
        return new ErrorResponse();
    }

    /**
     * Create an instance of {@link ApplicationInfoResponse }
     * 
     */
    public ApplicationInfoResponse createApplicationInfoResponse() {
        return new ApplicationInfoResponse();
    }

    /**
     * Create an instance of {@link ErrorItemType }
     * 
     */
    public ErrorItemType createErrorItemType() {
        return new ErrorItemType();
    }

    /**
     * Create an instance of {@link ApplicationInfoRequest }
     * 
     */
    public ApplicationInfoRequest createApplicationInfoRequest() {
        return new ApplicationInfoRequest();
    }

}
