package javabean;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

public class TransferData extends ActionSupport{
	private static Map<String, String> users=new HashMap<String, String>();
	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String execute(){
		users.put(user.getUserName().toLowerCase(), user.getPassword());
		return SUCCESS;
	}
	
	public void validate(){
		if (user.getUserName() == null || user.getUserName().length()==0){
			addFieldError("user.userName", getText("register.required.userName"));
		}
		else if(user.getUserName() !=null && users.containsKey(user.getUserName().toLowerCase())){
			addFieldError("user.userName", getText("register.exists.userName"));
		}
		if (user.getPassword() == null || user.getPassword().length() == 0){
			addFieldError("user.password", getText("register.required.password"));
		}
	}	
	
}
