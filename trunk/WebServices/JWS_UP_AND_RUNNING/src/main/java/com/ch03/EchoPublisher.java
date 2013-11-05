package com.ch03;

import javax.xml.ws.Endpoint;

public class EchoPublisher {
    public static void main(String args[]){
        String cwd = System.getProperty ("user.dir");
        System.out.println(cwd);
        Endpoint.publish("http://localhost:7004/echo", new Echo());
                
    }
}
