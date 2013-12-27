package com.ttdev;

import com.sun.tools.xjc.XJCFacade;

public class CodeGenerator {
	public static void main(String args[]) throws Throwable{
		XJCFacade.main(new String[]{
				"-d",	"src/main/java",
				"-b",   "src/main/resources/bindings.xml",
				"src/main/resources/BookService.xsd"
		});
	}
}
