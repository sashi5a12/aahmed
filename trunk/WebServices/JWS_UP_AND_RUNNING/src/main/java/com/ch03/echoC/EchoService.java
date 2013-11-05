
package com.ch03.echoC;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "EchoService", targetNamespace = "http://ch03.com/", wsdlLocation = "http://localhost:7004/echo?wsdl")
public class EchoService
    extends Service
{

    private final static URL ECHOSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(com.ch03.echoC.EchoService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = com.ch03.echoC.EchoService.class.getResource(".");
            url = new URL(baseUrl, "http://localhost:7004/echo?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://localhost:7004/echo?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        ECHOSERVICE_WSDL_LOCATION = url;
    }

    public EchoService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public EchoService() {
        super(ECHOSERVICE_WSDL_LOCATION, new QName("http://ch03.com/", "EchoService"));
    }

    /**
     * 
     * @return
     *     returns Echo
     */
    @WebEndpoint(name = "EchoPort")
    public Echo getEchoPort() {
        return super.getPort(new QName("http://ch03.com/", "EchoPort"), Echo.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Echo
     */
    @WebEndpoint(name = "EchoPort")
    public Echo getEchoPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ch03.com/", "EchoPort"), Echo.class, features);
    }

}
