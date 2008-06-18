package modeldriven;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

public class ModelDriven extends ActionSupport implements com.opensymphony.xwork2.ModelDriven<User>{
	private static Map<String, String> users=new HashMap<String, String>();
	private User user=new User();
	private String defaultProfileName;

	public User getModel() {
		return user;
	}

	public String getDefaultProfileName() {
		return defaultProfileName;
	}

	public void setDefaultProfileName(String defaultProfileName) {
		this.defaultProfileName = defaultProfileName;
	}

	public String execute(){
		users.put(user.getUserName().toLowerCase(), user.getPassword());
		return SUCCESS;
	}
	
	public void validate(){
		if (user.getUserName() == null || user.getUserName().length()==0){
			addFieldError("userName", getText("register.required.userName"));
		}
		else if(user.getUserName() !=null && users.containsKey(user.getUserName().toLowerCase())){
			addFieldError("userName", getText("register.exists.userName"));
		}
		if (user.getPassword() == null || user.getPassword().length() == 0){
			addFieldError("password", getText("register.required.password"));
		}
	}	
	
}
