package model;

/**
 * OtoFkaUsers entity. @author MyEclipse Persistence Tools
 */

public class OtoFkaUsers implements java.io.Serializable {

	// Fields

	private Long userId;
	private OtoFkaAddress shippingAddress;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String email;

	// Constructors

	/** default constructor */
	public OtoFkaUsers() {
	}

	/** minimal constructor */
	public OtoFkaUsers(OtoFkaAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	/** full constructor */
	public OtoFkaUsers(OtoFkaAddress shippingAddress, String firstname,
			String lastname, String username, String password, String email) {
		this.shippingAddress = shippingAddress;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	// Property accessors

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public OtoFkaAddress getShippingAddress() {
		return this.shippingAddress;
	}

	public void setShippingAddress(OtoFkaAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}