package com.ttdev.sp;

import java.util.Queue;

public class StatProducerImpl implements StatProducer {
	private Queue<String> statRequestQueue;
	
	public StatProducerImpl(Queue<String> statRequestQueue) {
		this.statRequestQueue = statRequestQueue;
	}
	public void getStatistics(String parameters) {
		statRequestQueue.add(parameters);
	}

}
