package model;


public abstract class UnionBilllingDetails implements java.io.Serializable {

	private static final long serialVersionUID = -4138230052881735578L;
	private Long billingDetailId;
	private Users users;

	/** default constructor */
	public UnionBilllingDetails() {
	}

	/** full constructor */
	public UnionBilllingDetails(Users users) {
		this.users = users;
	}

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