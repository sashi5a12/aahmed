package com.ch04;

import javax.xml.ws.Endpoint;

public class RabbitCounterPublisher {

    public static void main(String[] args) {
        int port = 7009;
        String url = "http://localhost:" + port + "/rc";
        System.out.println("Restfully publishing: " + url);
        Endpoint.publish(url, new RabbitCounterProvider());
    }
}
