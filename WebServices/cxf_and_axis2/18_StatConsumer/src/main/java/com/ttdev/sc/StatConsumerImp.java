package com.ttdev.sc;

public class StatConsumerImp implements StatConsumer {

	@Override
	public void putStatistics(String parameters) {
		System.out.println("Got response:"+parameters);
	}

}
