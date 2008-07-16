package tutorial;

import com.opensymphony.xwork2.ActionSupport;

public class HelloWorld extends ActionSupport{
	private static final String MESSAGE="Struts is up and running ...";
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String execute(){
		setMessage(MESSAGE);
		return SUCCESS;
	}
}
