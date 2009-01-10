package model;

public class SubClassBillingDetails implements java.io.Serializable {

	private static final long serialVersionUID = 5429141908655034982L;
	// Fields

	private Long billingDetailId;
	private Users users;

	// Constructors

	/** default constructor */
	public SubClassBillingDetails() {
	}

	/** full constructor */
	public SubClassBillingDetails(Users users) {
		this.users = users;
	}

	// Property accessors

	public Long getBillingDetailId() {
		return this.billingDetailId;
	}

	public void setBillingDetailId(Long billingDetailId) {
		this.billingDetailId = billingDetailId;
	}

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

}