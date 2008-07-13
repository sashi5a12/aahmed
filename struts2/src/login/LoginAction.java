package login;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements SessionAware{
	private Map session;
	private String userId;
	private String pwd;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setSession(Map map) {
		this.session=map;
	}

	public String execute(){
		if(userId.equals(pwd)){
			User user=new User();
			user.setName("Adnan Ahmed");
			session.put("USER",user);
			session.put("TEST","To test OGNL when selecting session as root object.");
			return SUCCESS;
		}
		else {
			return "login";
		}
	}
	
	public void validate(){
		if(this.userId==null || this.userId.length() == 0){
			super.addFieldError("userId", "Required: UserID");
		}
		if(this.pwd==null || this.pwd.length()==0){
			super.addFieldError("pwd","Required: Password");
		}
	}
}
