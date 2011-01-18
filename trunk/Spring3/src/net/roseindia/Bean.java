package net.roseindia;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class Bean {
	private String name;
	private String address;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "Bean [address=" + address + ", name=" + name + "]";
	}
	public static void main(String args[]){
		XmlBeanFactory factory=new XmlBeanFactory(new ClassPathResource("Inheritance.xml"));
		System.out.println(factory.getBean("child"));
		System.out.println(factory.getBean("subchild"));
	}
}
