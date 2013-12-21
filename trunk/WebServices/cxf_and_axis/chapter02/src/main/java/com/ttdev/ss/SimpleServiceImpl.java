package com.ttdev.ss;

import javax.jws.WebService;

@WebService(endpointInterface = "com.ttdev.ss.SimpleService")
public class SimpleServiceImpl implements SimpleService{

	@Override
	public String concat(ConcatRequest parameters) {
		return parameters.getS1() + parameters.getS2();
	}

}
