package com.ttdev.simple;

import javax.jws.WebService;

@WebService(endpointInterface = "com.ttdev.simple.SimpleService")
public class SimpleServiceImpl implements SimpleService {

	@Override
	public String concat(ConcatRequest parameters) {
		return parameters.getS1() + parameters.getS2();
	}

}
