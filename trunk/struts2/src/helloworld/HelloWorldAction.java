package helloworld;

import com.opensymphony.xwork2.ActionSupport;

public class HelloWorldAction extends ActionSupport{
	private String name;
	private String customGreeting;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCustomGreeting() {
		return customGreeting;
	}
	public void setCustomGreeting(String customGreeting) {
		this.customGreeting = customGreeting;
	}
	
	public String execute(){
		if (this.name != null && this.name.length()>0){
			this.setCustomGreeting("Hello "+this.name);
			return SUCCESS;
		}
		else {
			return ERROR;
		}
	}
	
	public int add(int a, int b){
		return a+b;
	}
	public int addition(){
		return 5+5;
	}
}
