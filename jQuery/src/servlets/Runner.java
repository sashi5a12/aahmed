package servlets;

public class Runner {
	private String firstName;
	private String lastName;
	private String time;
	private String gender;
	
	public Runner() {
	}
	public Runner(String firstName, String lastName, String gender, String time) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender=gender;
		this.time = time;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	public String toString()
	{
	    final String TAB = "    ";
	    
	    String retValue = "";
	    
	    retValue = "Runner ( "
	        + "firstName = " + this.firstName + TAB
	        + "lastName = " + this.lastName + TAB
	        + "time = " + this.time + TAB
	        + "gender = " + this.gender + TAB
	        + " )";
	
	    return retValue;
	}
	
}
