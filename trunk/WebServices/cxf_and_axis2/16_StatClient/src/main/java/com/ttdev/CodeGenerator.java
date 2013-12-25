package com.ttdev;

import org.apache.cxf.tools.wsdlto.WSDLToJava;

public class CodeGenerator {
	public static void main(String[] args) {
		WSDLToJava.main(new String[] { 
				"-client", 
				"-d", "src/main/java",
				"-b", "src/main/resources/binding.xml",
				"src/main/resources/StatService.wsdl" });
		System.out.println("Done!");
	}
}
