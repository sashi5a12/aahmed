
package com.ttdev.ss;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.7.8
 * 2013-12-24T10:22:38.060-08:00
 * Generated source version: 2.7.8
 * 
 */
 
public class StatService_P1_Server{

    protected StatService_P1_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new StatServiceImpl();
        String address = "http://localhost:8080/ss/p1";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new StatService_P1_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}