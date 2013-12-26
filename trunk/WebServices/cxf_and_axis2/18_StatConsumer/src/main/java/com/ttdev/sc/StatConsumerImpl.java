package com.ttdev.sc;

import javax.jws.WebService;

@WebService(endpointInterface = "com.ttdev.sc.StatConsumer")
public class StatConsumerImpl implements StatConsumer {

	@Override
	public void putStatistics(String parameters) {
		System.out.println("Got response: " + parameters);
	}

}
