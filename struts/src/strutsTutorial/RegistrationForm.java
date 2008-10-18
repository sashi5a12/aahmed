package strutsTutorial;

import org.apache.struts.validator.ValidatorForm;

public class RegistrationForm extends ValidatorForm {
	
	private String userId;
	private String pwd1;
	private String pwd2;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPwd1() {
		return pwd1;
	}
	public void setPwd1(String pwd1) {
		this.pwd1 = pwd1;
	}
	public String getPwd2() {
		return pwd2;
	}
	public void setPwd2(String pwd2) {
		this.pwd2 = pwd2;
	}

}
