package model;

/**
 * Address entity. @author MyEclipse Persistence Tools
 */

public class HomeAddress implements java.io.Serializable {

	// Fields

	private String street;
	private String zipcode;
	private String city;

	// Constructors

	/** default constructor */
	public HomeAddress() {
	}

	/** full constructor */
	public HomeAddress(String street, String zipcode, String city) {
		this.street = street;
		this.zipcode = zipcode;
		this.city = city;
	}

	// Property accessors

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}