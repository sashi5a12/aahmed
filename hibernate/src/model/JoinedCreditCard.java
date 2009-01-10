package model;

/**
 * JoinedCreditCard entity. @author MyEclipse Persistence Tools
 */

public class JoinedCreditCard extends model.JoinedBillingDetails implements java.io.Serializable {

	private static final long serialVersionUID = 5913227348384699281L;
	private String number;
	private String expMonth;
	private String expYear;

	// Constructors

	/** default constructor */
	public JoinedCreditCard() {
	}

	/** minimal constructor */
	public JoinedCreditCard(String number, String expMonth, String expYear) {
		this.number = number;
		this.expMonth = expMonth;
		this.expYear = expYear;
	}

	/** full constructor */
	public JoinedCreditCard(Users users, String number, String expMonth,
			String expYear) {
		super(users);
		this.number = number;
		this.expMonth = expMonth;
		this.expYear = expYear;
	}

	// Property accessors

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getExpMonth() {
		return this.expMonth;
	}

	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}

	public String getExpYear() {
		return this.expYear;
	}

	public void setExpYear(String expYear) {
		this.expYear = expYear;
	}

}