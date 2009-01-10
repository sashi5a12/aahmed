package model;

/**
 * SubClassBankAccount entity. @author MyEclipse Persistence Tools
 */

public class SubClassBankAccount extends SubClassBillingDetails implements java.io.Serializable {

	private static final long serialVersionUID = 7108166764197905344L;
	private String baAccount;
	private String baBankName;
	private String baSwift;

	// Constructors

	/** default constructor */
	public SubClassBankAccount() {
	}

	/** full constructor */
	public SubClassBankAccount(Users users, String baAccount, String baBankName, String baSwift) {
		super(users);
		this.baAccount = baAccount;
		this.baBankName = baBankName;
		this.baSwift = baSwift;
	}

	// Property accessors

	public String getBaAccount() {
		return this.baAccount;
	}

	public void setBaAccount(String baAccount) {
		this.baAccount = baAccount;
	}

	public String getBaBankName() {
		return this.baBankName;
	}

	public void setBaBankName(String baBankName) {
		this.baBankName = baBankName;
	}

	public String getBaSwift() {
		return this.baSwift;
	}

	public void setBaSwift(String baSwift) {
		this.baSwift = baSwift;
	}

}