package com.ttdev;

import org.apache.cxf.tools.wsdlto.WSDLToJava;

public class CodeGenerator {
	public static void main(String[] args) {
		WSDLToJava.main(new String[] { "-client", 
				"-d", "src/main/java",
				"-p", "http://ttdev.com/ss=com.ttdev.simple",
				"src/main/resources/SimpleService.wsdl" });
		System.out.println("Done!");
	}
}
