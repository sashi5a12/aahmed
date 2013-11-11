package com.ch04;

import javax.xml.ws.Endpoint;

public class TeamsPublisher {
    public static void main(String args[]){
        Endpoint.publish("http://localhost:7007/teams", new RestfulTeams());
    }
}
