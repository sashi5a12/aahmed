package com.ttdev;

import org.apache.axis2.wsdl.WSDL2Java;

public class CodeGenerator {

	public static void main(String[] args) throws Exception {
		WSDL2Java.main(new String[]{
				"-ss",
				"-sd",
				"-uw",
				"-S", "src/main/java",
				"-R", "src/main/resources/META-INF",
				"-ns2p", "urn:ttdev.com:service/img=com.ttdev.is",
				"-uri", "src/main/resources/ImageService.wsdl"
		});
		System.out.println("Done!");
	}

}
