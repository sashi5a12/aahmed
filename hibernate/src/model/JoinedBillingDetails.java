package model;

/**
 * JoinedBillingDetails entity. @author MyEclipse Persistence Tools
 */

public class JoinedBillingDetails implements java.io.Serializable {

	private static final long serialVersionUID = 8645981320589246922L;
	private Long billingDetailsId;
	private Users users;

	// Constructors

	/** default constructor */
	public JoinedBillingDetails() {
	}

	/** full constructor */
	public JoinedBillingDetails(Users users) {
		this.users = users;
	}

	// Property accessors

	public Long getBillingDetailsId() {
		return this.billingDetailsId;
	}

	public void setBillingDetailsId(Long billingDetailsId) {
		this.billingDetailsId = billingDetailsId;
	}

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

}