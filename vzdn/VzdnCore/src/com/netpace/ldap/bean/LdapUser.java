package com.netpace.ldap.bean;



public class LdapUser {

	//Timestamp, Email Address, First Name, Last Name, Title, DOB, Country, Terms & Condition agreement
	private String createdTimeStamp;
	private String email;
	private String firstName;
	private String lastName;
	private String title;
	private String dob;
	private String country;
	private String agreement;
	
	public LdapUser(String createdTimeStamp, String email, String firstName, String lastName, String title, String dob, String country, String agreement) {
		this.createdTimeStamp = createdTimeStamp;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.dob = dob;
		this.country = country;
		this.agreement = agreement;
	}
	
	public LdapUser() {
		createdTimeStamp = email = firstName = lastName = title = dob = country = agreement = "" ;
	}
	
	
	/**
	 * @return the timeStamp
	 */
	public String getCreatedTimeStamp() {
		return createdTimeStamp;
	}
	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setCreatedTimeStamp(String timeStamp) {
		this.createdTimeStamp = timeStamp;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}
	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the agreement
	 */
	public String getAgreement() {
		return agreement;
	}
	/**
	 * @param agreement the agreement to set
	 */
	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("\nCreated Timestamp: " ).append( createdTimeStamp ).append( ", Title: " ).append( title )
		.append( ", First Name: " ).append( firstName ).append( ", Last Name: " ).append( lastName ).append( ", Date of Birth: " )
		.append( dob ).append( ", Country: " ).append( country ).append( ", Terms & Conditions Agreement: " ).append( agreement );
		
		return sb.toString();
	}
}
