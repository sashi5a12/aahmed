package com.ch03;

import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.SOAPBinding;

public class SkiImageServicePublisher {
    public static void main(String args[]){
        Endpoint endpoint = Endpoint.create(new SkiImageService());
        ((SOAPBinding)endpoint.getBinding()).setMTOMEnabled(true);
        endpoint.publish("http://localhost:7005/image");
    }
}
