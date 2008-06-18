package validation;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

public class Register extends ActionSupport{
	private static Map<String, String> users=new HashMap<String, String>();
	private String userName;
	private String password;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String execute(){
		users.put(userName.toLowerCase(), password);
		return SUCCESS;
	}
	
	public void validate(){
		if (this.userName == null || this.userName.length()==0){
			addFieldError("userName", getText("register.required.userName"));
		}
		else if(userName !=null && users.containsKey(userName.toLowerCase())){
			addFieldError("userName", getText("register.exists.userName"));
		}
		if (this.password == null || this.password.length() == 0){
			addFieldError("password", getText("register.required.password"));
		}
	}

}
