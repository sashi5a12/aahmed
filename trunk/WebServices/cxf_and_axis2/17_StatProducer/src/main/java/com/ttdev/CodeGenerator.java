package com.ttdev;

import org.apache.cxf.tools.wsdlto.WSDLToJava;

public class CodeGenerator {
	public static void main(String[] args) {
		
		WSDLToJava.main(new String[] { 
				"-server", 
				"-d", "src/main/java",
				"src/main/resources/StatProducer.wsdl" });
		
		WSDLToJava.main(new String[] { 
				"-client", 
				"-d", "src/main/java",
				"src/main/resources/StatConsumer.wsdl" });
		
		System.out.println("Done!");
	}
}
