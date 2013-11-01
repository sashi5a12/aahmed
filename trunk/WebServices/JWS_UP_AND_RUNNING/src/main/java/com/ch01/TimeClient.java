package com.ch01;

import com.ch01.timeclient.TimeServerImplService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

class TimeClient {

    public static void main(String args[]) throws Exception {
        URL url = new URL("http://localhost:7001/ts?wsdl");
        
        // Qualified name of the service:
        // 1st arg is the service URI
        // 2nd is the service name published in the WSDL
        QName qname = new QName("http://ch01.com/", "TimeServerImplService");
        
        // Create, in effect, a factory for the service.
        Service service = Service.create(url, qname);
        
        // Extract the endpoint interface, the service "port".
        TimeServer eif = service.getPort(TimeServer.class);
        System.out.println(eif.getTimeAsString());
        System.out.println(eif.getTimeAsElapsed());
        
        //To generate client stub
        //wsimport -verbose -p com.ch01.timeclient -s C:/Users/aahmed.NETPACE/Documents/NetBeansProjects/JWS_UP_AND_RUNNING/src/main/java -d C:/Users/aahmed.NETPACE/Documents/NetBeansProjects/JWS_UP_AND_RUNNING/target/classes -keep http://localhost:7001/ts?wsdl
        
        TimeServerImplService tsc=new TimeServerImplService();
        com.ch01.timeclient.TimeServer tsPort =tsc.getTimeServerImplPort();
        System.out.println(tsPort.getTimeAsString());
        System.out.println(tsPort.getTimeAsElapsed());

    }
}
