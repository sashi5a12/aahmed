package model;

public class UnionCreditCard extends UnionBilllingDetails implements java.io.Serializable {

	private static final long serialVersionUID = -6313159112523007502L;
	private String number;
	private String expMonth;
	private String expYear;

	// Constructors

	/** default constructor */
	public UnionCreditCard() {
	}

	/** full constructor */
	public UnionCreditCard(Users users, String number, String expMonth, String expYear) {
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