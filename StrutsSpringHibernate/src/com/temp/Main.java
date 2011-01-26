package com.temp;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

public class Main {
	public static void main(String args[]){
		XmlBeanFactory context = new XmlBeanFactory(new FileSystemResource("WebContent/WEB-INF/SpringBeans.xml"));
		FooService service=(FooService) context.getBean("fooService");
		service.insertFoo(new Foo());
	}
}
