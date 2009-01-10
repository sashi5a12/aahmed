package model;

/**
 * SubClassCreditCard entity. @author MyEclipse Persistence Tools
 */

public class SubClassCreditCard extends model.SubClassBillingDetails implements java.io.Serializable {

	private static final long serialVersionUID = 7167876431835394300L;
	// Fields

	private String ccNumber;
	private String ccExpMonth;
	private String ccExpYear;

	// Constructors

	/** default constructor */
	public SubClassCreditCard() {
	}

	/** full constructor */
	public SubClassCreditCard(Users users, String ccNumber, String ccExpMonth,
			String ccExpYear) {
		super(users);
		this.ccNumber = ccNumber;
		this.ccExpMonth = ccExpMonth;
		this.ccExpYear = ccExpYear;
	}

	// Property accessors

	public String getCcNumber() {
		return this.ccNumber;
	}

	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}

	public String getCcExpMonth() {
		return this.ccExpMonth;
	}

	public void setCcExpMonth(String ccExpMonth) {
		this.ccExpMonth = ccExpMonth;
	}

	public String getCcExpYear() {
		return this.ccExpYear;
	}

	public void setCcExpYear(String ccExpYear) {
		this.ccExpYear = ccExpYear;
	}

}