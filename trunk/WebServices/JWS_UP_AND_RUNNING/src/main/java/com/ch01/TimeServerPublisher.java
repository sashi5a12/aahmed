// Example 1-3. Endpoint publisher for the TimeServer
package com.ch01;

import javax.xml.ws.Endpoint;

public class TimeServerPublisher {

    public static void main(String[] args) {
        // 1st argument is the publication URL
        // 2nd argument is an SIB instance
        Endpoint.publish("http://localhost:7001/ts", new TimeServerImpl());
    }
}