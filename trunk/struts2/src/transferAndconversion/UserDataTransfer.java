package transferAndconversion;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

public class UserDataTransfer extends ActionSupport{
	List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public String execute(){
		return SUCCESS;
	}
}
