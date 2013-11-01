package com.ch03;

import javax.xml.ws.Endpoint;

public class RabbitCounterPublisher {
    public static void main(String args[]){
        Endpoint.publish("http://localhost:7003/fib", new RabbitCounter());
    }
}
