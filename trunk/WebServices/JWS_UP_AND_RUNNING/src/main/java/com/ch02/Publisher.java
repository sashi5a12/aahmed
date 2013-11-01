package com.ch02;

import javax.xml.ws.Endpoint;

public class Publisher {
    public static void main(String args[]){
        Endpoint.publish("http://localhost:7002/ts", new TimeServer());
    }
}
