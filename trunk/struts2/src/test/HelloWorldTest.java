package test;

import com.opensymphony.xwork2.ActionSupport;

import helloworld.HelloWorldAction;
import junit.framework.TestCase;

public class HelloWorldTest extends TestCase{

	public void testHelloWorld() throws Exception{
		
		HelloWorldAction action=new  HelloWorldAction();
		action.setName("Adnan Ahmed");
		String result=action.execute();
		
		assertTrue("Expected a success result! ",ActionSupport.SUCCESS.equals(result));
		
	}
}
