package ttdev.com;

import org.apache.axis2.wsdl.WSDL2Java;

public class CodeGenerator {
	public static void main(String args[]) throws Exception{
		WSDL2Java.main(new String[]{
				"-S", 		"src/main/java",
				"-R",		"src/main/resources/META-INF",
				"-ns2p",	"http://ttdev.com/ss=com.ttdev.ss",
				"-uri",		"src/main/resources/SimpleService.wsdl"
		});
		System.out.println("Done!");
	}
}
