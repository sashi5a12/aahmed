package model;

/**
 * JoinedBankAccount entity. @author MyEclipse Persistence Tools
 */

public class JoinedBankAccount extends JoinedBillingDetails implements java.io.Serializable {

	private static final long serialVersionUID = 7840431512552304186L;
	private String account;
	private String bankName;
	private String swift;

	// Constructors

	/** default constructor */
	public JoinedBankAccount() {
	}

	/** minimal constructor */
	public JoinedBankAccount(String account, String bankName, String swift) {
		this.account = account;
		this.bankName = bankName;
		this.swift = swift;
	}

	/** full constructor */
	public JoinedBankAccount(Users users, String account, String bankName, String swift) {
		super(users);
		this.account = account;
		this.bankName = bankName;
		this.swift = swift;
	}

	// Property accessors

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getSwift() {
		return this.swift;
	}

	public void setSwift(String swift) {
		this.swift = swift;
	}

}