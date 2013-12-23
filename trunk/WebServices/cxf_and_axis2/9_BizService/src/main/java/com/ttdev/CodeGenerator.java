package com.ttdev;

import org.apache.cxf.tools.wsdlto.WSDLToJava;

public class CodeGenerator {
	public static void main (String args[]){
		WSDLToJava.main(new String[]{
				"-server",
				"-p","http://foo.com=com.ttdev.biz",
				"-d","src/main/java",
				"src/main/resources/BizService.wsdl"
		});
	}
}
