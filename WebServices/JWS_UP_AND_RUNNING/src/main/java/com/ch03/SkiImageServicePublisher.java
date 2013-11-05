package com.ch03;

import javax.xml.ws.Endpoint;

public class SkiImageServicePublisher {
    public static void main(String args[]){
        Endpoint.publish("http://localhost:7005/image", new SkiImageService());
    }
}
