package tagdemo;

import com.opensymphony.xwork2.ActionSupport;

public class TagDemo extends ActionSupport {

	private String name="Netpace System!";
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String execute(){
		return SUCCESS;
	}
}
