/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.ch02;

import javax.xml.ws.Endpoint;

/**
 *
 * @author aahmed
 */
public class TimeServerPublisher {
    public static void main(String args[]){
        Endpoint.publish("http://localhost:9877/ts", new TimeServerImpl());
    }
}
