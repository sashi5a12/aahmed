package net.roseindia;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Spring3HelloWorldConfigTest {

	public static void main(String[] args) {

		// Initialize IoC Container
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Spring3HelloWorldConfig.class);

		System.out.println("Calling Bean method: sayHello()");

		// Retrieve the bean from Container
		Spring3HelloWorld myBean = (Spring3HelloWorld) context.getBean("spring3HelloWorld");
		myBean.sayHello();
	}

}