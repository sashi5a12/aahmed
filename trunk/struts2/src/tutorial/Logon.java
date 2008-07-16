package tutorial;

import com.opensymphony.xwork2.ActionSupport;

public class Logon extends ActionSupport{
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String execute(){
		if(isInvalid(username))return INPUT;
		if(isInvalid(password))return INPUT;
		return SUCCESS;
	}
	private boolean isInvalid(String value){
		return ((value == null) || value.length() == 0);
	}
}
