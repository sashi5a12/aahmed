package com.ttdev.sp;

import java.util.Queue;

import javax.jws.WebService;

@WebService(endpointInterface = "com.ttdev.sp.StatProducer")
public class StatProducerImpl implements StatProducer {
	private Queue<GetStatistics> statRequestQueue;

	public StatProducerImpl(Queue<GetStatistics> statRequestQueue) {
		this.statRequestQueue = statRequestQueue;
	}

	public StatProducerImpl() {
	}

	@Override
	public void getStatistics(String param, String replyTo) {
		GetStatistics req = new GetStatistics();
		req.setParam(param);
		req.setReplyTo(replyTo);
		statRequestQueue.add(req);
	}

}
