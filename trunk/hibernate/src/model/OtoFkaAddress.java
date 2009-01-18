package model;

/**
 * OtoFkaAddress entity. @author MyEclipse Persistence Tools
 */

public class OtoFkaAddress implements java.io.Serializable {

	// Fields

	private Long addressId;
	private OtoFkaUsers user;
	private String street;
	private String zipcode;
	private String city;

	// Constructors

	/** default constructor */
	public OtoFkaAddress() {
	}

	/** full constructor */
	public OtoFkaAddress(OtoFkaUsers user, String street, String zipcode,
			String city) {
		this.user = user;
		this.street = street;
		this.zipcode = zipcode;
		this.city = city;
	}

	// Property accessors

	public Long getAddressId() {
		return this.addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public OtoFkaUsers getUser() {
		return this.user;
	}

	public void setUser(OtoFkaUsers user) {
		this.user = user;
	}

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