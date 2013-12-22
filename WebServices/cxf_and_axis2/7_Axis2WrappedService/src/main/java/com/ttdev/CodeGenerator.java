package com.ttdev;

import org.apache.axis2.wsdl.WSDL2Java;

public class CodeGenerator {
	public static void main(String args[]) throws Exception{
		WSDL2Java.main(new String[]{
				"-ss",	//Generate code for the server side.
				"-sd",	//Generate the service descriptor file. This file controls how to deploy your service.
				"-uw",
				"-S",	"src/main/java",
				"-R",	"src/main/resources/META-INF",
				"-ns2p",	"http://ttdev.com/ss=com.ttdev.ss",
				"-uri",	"src/main/resources/SimpleService.wsdl"
		});
	}
}
