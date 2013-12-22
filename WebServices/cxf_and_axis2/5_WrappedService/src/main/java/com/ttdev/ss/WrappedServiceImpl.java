package com.ttdev.ss;

import javax.jws.WebService;

@WebService(endpointInterface="com.ttdev.ss.WrappedService")
public class WrappedServiceImpl implements WrappedService{

	@Override
	public String concat(String s1, String s2) {
		return s1+" "+s2;
	}

}
