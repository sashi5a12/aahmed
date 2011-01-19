package net.roseindia;

import java.util.Map;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;

public class ConstructorInjection {
	public static void main(String args[]){
		XmlBeanFactory factory=new XmlBeanFactory(new ClassPathResource("ConstructorInjection.xml"));
		Company company=(Company)factory.getBean("company");
		System.out.println("Name of the company is: " + company.name());
        System.out.println("Address of the company is: " + company.address());
	}
}

interface Company{
	String name();
	String address();
}

interface Detail{
	String find(String entry);
}

class CompanyInformation implements Company{
	private Detail detail;
	
	public String name(){
		return detail.find("CompanyName");
	}
	
	public String address(){
		return detail.find("Address");
	}
	
	public void setEncyclopedia(Detail d){
		this.detail=d;
	}
}

class Configuration implements Detail{
	private Map<String,String> map;
	
	public Configuration(Map<String,String> map){
		Assert.notNull(map, "Arguments cannot be null.");
		this.map=map;
	}
	
	public String find(String key){
		return map.get(key);
	}
	
}