package model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Users entity. @author MyEclipse Persistence Tools
 */

public class Users implements java.io.Serializable {

	// Fields

	private Long userId;
	private BillingDetails billingDetails;
	private Integer objVersion;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String email;
	private Integer rank;
	private Boolean isAdmin;
	private Timestamp created;
	private HomeAddress homeAddress;
	private Address shippingAddress;
	private Set boughtItems=new HashSet(0);

	// Constructors

	/** default constructor */
	public Users() {
	}

	// Property accessors

	public Long getUserId() {
		return this.userId;
	}

	public Users(Integer objVersion, String firstname, String lastname,
			String username, String password, String email, Integer rank,
			Boolean isAdmin, Timestamp created) {
		this.objVersion = objVersion;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.rank = rank;
		this.isAdmin = isAdmin;
		this.created = created;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BillingDetails getBillingDetails() {
		return this.billingDetails;
	}

	public void setBillingDetails(BillingDetails billingDetails) {
		this.billingDetails = billingDetails;
	}

	public Integer getObjVersion() {
		return this.objVersion;
	}

	public void setObjVersion(Integer objVersion) {
		this.objVersion = objVersion;
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

	public Integer getRank() {
		return this.rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Boolean getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public HomeAddress getHomeAddress() {
		return this.homeAddress;
	}

	public void setHomeAddress(HomeAddress homeAddress) {
		this.homeAddress = homeAddress;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Set getBoughtItems() {
		return boughtItems;
	}

	public void setBoughtItems(Set boughtItems) {
		this.boughtItems = boughtItems;
	}
	
	public void addItem(Item item){
		item.setBuyer(this);
		this.boughtItems.add(item);
	}
}