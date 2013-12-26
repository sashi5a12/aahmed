package com.ttdev;



import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;

import com.ttdev.sc.StatConsumerImp;
import com.ttdev.sp.StatProducer;
import com.ttdev.sp.StatProducer_Service;

public class Main {
	
	public static void main(String[] args) {
		new Main().run();
	}
	
	public Main(){
	}

	public void run(){
		StatConsumerImp implementor = new StatConsumerImp();
		String address = "http://localhost:8081/sc/p1";
		Endpoint.publish(address, implementor);
		sendRequests();
	}
	
	public void sendRequests(){
		System.out.println("Sending a requests");
		StatProducer_Service ss = new StatProducer_Service(StatProducer_Service.WSDL_LOCATION, new QName("http://ttdev.com/sp","StatProducer"));
		StatProducer port = ss.getP1();
		port.getStatistics("ABC", "adnan_ahmed");
	}
}
