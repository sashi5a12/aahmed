package net.roseindia;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class InitBean extends BeanSupport{
	private String name;
	private String address;
	private String profession;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		System.out.println("Setting name: "+name);
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public void init(){
		System.out.println("INITIALIZATION BEAN.....");
	}
	@Override
	public String toString() {
		return "InitBean [address=" + address + ", name=" + name + ", profession=" + profession + "]";
	}
	
	public static void main(String args[]){
		BeanFactory factory = new XmlBeanFactory(new ClassPathResource("InitMethod.xml"));
		InitBean bean = (InitBean) factory.getBean("bean");
		System.out.println(bean);
	}
}

class BeanSupport implements InitializingBean{
	private String value;
	
	public final String getValue() {
		return value;
	}

	protected final void setValue(String value) {
		this.value = value;
	}

	public final void afterPropertiesSet() throws Exception {
		System.out.println("After Properties Set");
	}
	
}