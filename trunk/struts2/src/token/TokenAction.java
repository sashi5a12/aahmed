package token;

import com.opensymphony.xwork2.ActionSupport;

public class TokenAction extends ActionSupport{
	private String input;

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
	
	public String show(){
		return "show";
	}
	public String execute(){
		System.out.println("Success Called....");
		return SUCCESS;
	}
}
