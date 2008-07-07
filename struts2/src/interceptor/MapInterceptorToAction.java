package interceptor;

import com.opensymphony.xwork2.ActionSupport;

public class MapInterceptorToAction extends ActionSupport{
	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String execute(){
		System.out.println("Name: "+name);
		return SUCCESS;
	}
}
