package model;

public class UnionBankAccount extends UnionBilllingDetails implements java.io.Serializable {

	private static final long serialVersionUID = 7571781727222129425L;
	private String account;
	private String bankName;
	private String swift;


	/** default constructor */
	public UnionBankAccount() {
	}

	/** full constructor */
	public UnionBankAccount(Users users, String account, String bankName,
			String swift) {
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