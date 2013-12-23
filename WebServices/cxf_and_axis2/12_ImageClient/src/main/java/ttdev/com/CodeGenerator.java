package ttdev.com;

import org.apache.cxf.tools.wsdlto.WSDLToJava;

public class CodeGenerator {
	public static void main(String args[]){
		WSDLToJava.main(new String[]{
				"-client",
				"-d", "src/main/java",
				"src/main/resources/ImageService.wsdl"
		});
	}
}
