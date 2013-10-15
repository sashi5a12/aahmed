package model;

/**
 * Address entity. @author MyEclipse Persistence Tools
 */

public class Address implements java.io.Serializable {

	// Fields

	private Long addressId;
	private Users user;
	private Integer objVersion;
	private String street;
	private String zipcode;
	private String city;

	// Constructors

	/** default constructor */
	public Address() {
	}

	/** minimal constructor */
	public Address(Users user, Integer objVersion) {
		this.user = user;
		this.objVersion = objVersion;
	}

	/** full constructor */
	public Address(Users user, Integer objVersion, String street,
			String zipcode, String city) {
		this.user = user;
		this.objVersion = objVersion;
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

	public Users getUser() {
		return this.user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Integer getObjVersion() {
		return this.objVersion;
	}

	public void setObjVersion(Integer objVersion) {
		this.objVersion = objVersion;
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